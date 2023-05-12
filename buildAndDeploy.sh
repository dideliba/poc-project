echo 'Starting building project, running tests and creating docker images, please be patient it will take a couple of minutes depending on your network connection....'
./mvnw clean install
echo 'Project build finished successfully!'
echo 'Starting services'
docker-compose up --detach
echo 'Services deployed successfully, enjoy!'
echo 'To stop services run docker-compose down '







