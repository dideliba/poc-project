package com.poc.user;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.poc.user.dao.impl.mongodb.entity.UserEntity;
import com.poc.user.dao.impl.mongodb.repository.UserRepository;
import com.poc.user.domain.request.ParticularUserInfo;
import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.request.UserInfoList;
import com.poc.user.domain.response.UserResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApplicationIntegrationTest  {

    static final String DB_NAME = "userDB";
    static final String USERS_ENDPOINT_PATH="/v1/users";
    static final String MONGO_DB_CONTAINER_VERSION="mongo:4.4.2";


    @Autowired
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @ComponentScan
    static class TransConfig extends AbstractReactiveMongoConfiguration {

        @Bean("reactiveTransactionManager")
        ReactiveTransactionManager transactionManager(ReactiveMongoDatabaseFactory dbFactory) {
            return new ReactiveMongoTransactionManager(dbFactory);
        }

        @Bean
        @Override
        public MongoClient reactiveMongoClient() {
            return MongoClients.create(mongoDBContainer.getReplicaSetUrl());
        }

        @Override
        protected String getDatabaseName() {
            return DB_NAME;
        }
    }


    @Autowired
    UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.
            parse(MONGO_DB_CONTAINER_VERSION)).withReuse(true).withCommand("--replSet", "rs0");


    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    public static void initializeTestEnvironment() {
        //start mongo container
        mongoDBContainer.start();

    }

    @BeforeEach
    public void emptyUsersCollection(){
        //ensure user collection is empty before each test
        mongoTemplate.dropCollection("user").block();
        mongoTemplate.createCollection("user").block();
    }

    @Test
    public void givenNewUser_whenSaved_userExistsOnDB() {
        UserEntity testUserEntity=new UserEntity();
        testUserEntity.setId("test"); testUserEntity.setEmail("test@test.com"); testUserEntity.setFirstname("testName");
        testUserEntity.setLastname("testLastname"); testUserEntity.setUsername("testuser");
        testUserEntity.setActive(true);
        
        UserEntity userEntity=mongoTemplate.save(testUserEntity).block();

        assertThat(userEntity.getId()).isNotNull();
        assertEquals(1, userRepository.findAll().count().block());
    }

    @Test
    void givenAllEligibleFieldsPopulated_whenCreateNewUser_thenUserCreatedSuccessfully() {
        UserInfo testUserInfo=new UserInfo();
        testUserInfo.setEmail("test@test.com"); testUserInfo.setFirstname("testName");
        testUserInfo.setLastname("testLastname"); testUserInfo.setUsername("testuser");

        UserResponse userResponse=webTestClient.post()
                .uri(USERS_ENDPOINT_PATH).body(Mono.just(testUserInfo), UserResponse.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponse.class)
                .returnResult().getResponseBody();
        UserEntity userEntity= userRepository.findById(userResponse.getId()).block();

        assertEquals(testUserInfo.getEmail(),userResponse.getEmail());
        assertEquals(testUserInfo.getFirstname(),userResponse.getFirstname());
        assertEquals(testUserInfo.getLastname(),userResponse.getLastname());
        assertEquals(testUserInfo.getUsername(),userResponse.getUsername());
        assertTrue(userEntity.isActive()); assertNull(userEntity.getDeactivatedTimestamp());
        assertNotNull(userResponse.getCreatedDateTime());
        assertNull(userResponse.getLastModifiedDateTime());
    }

    @Test
    void givenSpecificUserExists_whenReadUser_thenUserInfoReadSuccessfully() {
        UserEntity testUserEntity=new UserEntity();
        testUserEntity.setId("test"); testUserEntity.setEmail("test@test.com"); testUserEntity.setFirstname("testName");
        testUserEntity.setLastname("testLastname"); testUserEntity.setUsername("testuser"); testUserEntity.setActive(true);
        mongoTemplate.insert(testUserEntity).block();

        UserResponse userResponse= webTestClient.get()
                .uri(USERS_ENDPOINT_PATH+"/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .returnResult().getResponseBody();

        assertEquals(testUserEntity.getEmail(),userResponse.getEmail());
        assertEquals(testUserEntity.getFirstname(),userResponse.getFirstname());
        assertEquals(testUserEntity.getLastname(), userResponse.getLastname());
        assertEquals(testUserEntity.getUsername(), userResponse.getUsername());
    }

    @Test
    void givenSpecificUserNotExists_whenReadUser_thenUserNotFound() {
        webTestClient.get()
                .uri(USERS_ENDPOINT_PATH+"/1234")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void givenSpecificUsersExists_whenUsersEdited_thenAllFieldsUpdatedSuccessfully() {
        UserEntity userEntity1=new UserEntity();
        userEntity1.setId("test"); userEntity1.setEmail("test@test.com"); userEntity1.setFirstname("testName");
        userEntity1.setLastname("testLastname"); userEntity1.setUsername("testuser"); userEntity1.setActive(true);
        UserEntity userEntity2=mongoTemplate.save(userEntity1).block();
        userEntity2.setId("test2");
        userEntity2.setEmail("test2@test.com");
        mongoTemplate.save(userEntity2).block();

        ParticularUserInfo userInfo1=new ParticularUserInfo();
        userInfo1.setId("test");userInfo1.setEmail("newTest@test.com");userInfo1.setUsername("testuser");
        userInfo1.setLastname("newTestlastname");
        ParticularUserInfo userInfo2=new ParticularUserInfo();
        userInfo2.setId("test2");userInfo2.setEmail("newTest2@test.com"); userInfo2.setUsername("testuser2");
        userInfo2.setLastname("newTestlastname2");
        UserInfoList<ParticularUserInfo> userInfoList = new UserInfoList() {
            {
                add(userInfo1);
                add(userInfo2);
            }
        };

       webTestClient.put()
               .uri(USERS_ENDPOINT_PATH).body(Mono.just(userInfoList), UserInfoList.class)
               .exchange()
               .expectStatus().isOk()
               .expectBody()
               .jsonPath("$[0].id").value(Matchers.in(userInfoList.stream().map(u-> u.getId() ).collect(Collectors.toList())))
               .jsonPath("$[0].email").value(Matchers.in(userInfoList.stream().map(u-> u.getEmail()).collect(Collectors.toList())))
               .jsonPath("$[0].lastname").value(Matchers.in(userInfoList.stream().map(u-> u.getLastname()).collect(Collectors.toList())))
               .jsonPath("$[0].firstname").value(Matchers.in(userInfoList.stream().map(u-> u.getFirstname()).collect(Collectors.toList())))
               .jsonPath("$[0].lastModifiedDateTime").isNotEmpty().jsonPath("$[1].lastModifiedDateTime")
               .isNotEmpty();
    }

    @Test
    void givenNonExistentUserProvided_whenReadUser_thenUserMotFound() {
        webTestClient.get()
                .uri(USERS_ENDPOINT_PATH+"/test")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void givenSpecificUsersExists_whenUsersDeleted_thenUsersNotFetchedAfterDelete() {
        UserEntity userEntity1=new UserEntity();
        userEntity1.setId("test"); userEntity1.setEmail("test@test.com"); userEntity1.setFirstname("testName");
        userEntity1.setLastname("testLastname"); userEntity1.setUsername("testuser");userEntity1.setActive(true);
        UserEntity userEntity2=new UserEntity();
        userEntity2.setId("test2"); userEntity2.setEmail("test2@test.com"); userEntity2.setFirstname("testName2");
        userEntity2.setLastname("testLastname2"); userEntity2.setUsername("testuser2");userEntity2.setActive(true);
        mongoTemplate.insert(userEntity1).block();
        mongoTemplate.insert(userEntity2).block();

        List<UserResponse> userReadResponse= webTestClient.get()
                .uri(USERS_ENDPOINT_PATH+"?id=test&id=test2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult().getResponseBody();
        webTestClient.delete()
                .uri(USERS_ENDPOINT_PATH+"?id=test&id=test2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class);
        List<UserResponse> userReadResponseAfterDeletion= webTestClient.get()
                .uri(USERS_ENDPOINT_PATH+"?id=test&id=test2")
                .exchange()
                .expectBody(List.class)
                .returnResult().getResponseBody();

        assertEquals(2,userReadResponse.size());
        assertEquals(0,userReadResponseAfterDeletion.size());
    }
}
