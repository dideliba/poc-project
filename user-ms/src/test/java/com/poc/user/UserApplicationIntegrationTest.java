package com.poc.user;


import com.poc.user.dao.impl.mongodb.entity.UserEntity;
import com.poc.user.dao.impl.mongodb.repository.UserRepository;
import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.response.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserApplicationIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    public static final String usersUri="/v1/users";
    @Container
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"))
            .withReuse(true);

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @BeforeAll
    public static void startContainer() {
        container.start();
    }

    @BeforeEach
    public void emptyUsersCollection(){
        //ensure user collection is empty before tests
        userRepository.deleteAll().block();
    }

    @Test
    void givenNewUser_whenSaved_userExistsOnDB() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("123");
        userEntity = userRepository.save(userEntity).block();
        assertThat(userEntity.getId()).isNotNull();
        assertEquals(1, userRepository.findAll().count().block());
    }


    @Test
    void givenAllEligibleFieldsPopulated_whenCreateNewUser_thenUserCreatedSuccessfully() {
        UserInfo user = new UserInfo();
        user.setEmail("test@test.com");
        user.setFirstname("testName");
        user.setLastname("testLastName");
        user.setUsername("testUser");

        UserResponse userResponse=webTestClient.post()
                .uri(usersUri).body(Mono.just(user), UserResponse.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponse.class)
                .returnResult().getResponseBody();

        UserEntity userEntity= userRepository.findById(userResponse.getId()).block();

        assertEquals(userEntity.getEmail(),user.getEmail());
        assertEquals(userEntity.getFirstname(),user.getFirstname());
        assertEquals(userEntity.getLastname(), user.getLastname());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertTrue(userEntity.isActive());
        assertNotNull(userEntity.getCreatedDateTime());
       // assertNull(userEntity.getUpdatedDateTime());
        //assertNull(userEntity.getUpdatedDateTime());
        assertNull(userEntity.getDeactivatedTimestamp());
    }

}