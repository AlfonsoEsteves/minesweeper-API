# minesweeper-API

Made by Alfonso Esteves

You can see it running here:
http://ec2-18-237-220-109.us-west-2.compute.amazonaws.com/

The features present are:
 - Basic user sign up, sign in, authentication using Spring Security
 - Games and Users persistance in DynamoDB using Spring Data
 - Ability to start a new game and preserve/resume the old ones
 - Ability to select the game parameters: number of rows, columns, and mines
 - Super basic frontend in Html+Javascript, just to test the api
 - Unit tests with JUnit and Mockito
 - Methods with Javadoc
 - And ofcourse, the game logic itself

All the frontend files are included in the resources/static folder. So you can run the entire app by just running the Spring Boot app.