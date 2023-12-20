## Commit 1: MVC Structure and Refactoring

In this commit, we made significant strides towards adopting the Model-View-Controller (MVC) architectural pattern in the Snake Game project. Key changes include:

- **Renaming View Class:** Renamed `MyFrame` to `View` to align with MVC conventions, separating the user interface from game logic.
  
- **Model Refactoring:** Modified the `Model` class for improved structure and functionality, promoting better organisation and adherence to MVC principles.
  
- **Introducing Game Initiator:** Introduced a new class, `SnakeGameApp`, to handle game initiation, eliminating unnecessary logic from the now-deprecated `Main` class.
  
- **Boundary Collision Fix:** Addressed a boundary collision detection issue in the `View` class, ensuring smoother gameplay.
  
- **Music Handling Improvement:** Enhanced music handling in the `MusicPlayer` class, enabling continuous looping during gameplay and stopping on game end.
  
- **Redundant Class Removal:** Removed redundant classes, including `MyFrame.MySnake`, `Movable`, `Snake`, and `Paddle`.
  
- **New Class Introduction:** Introduced new classes, such as `Controller`, `MySnake`, and `SnakeObject`, to support better single responsibility and separation of concerns.

These changes lay the groundwork for a more organised and maintainable codebase following MVC design principles.

## Commit 2: Cleanup and Additional MVC Changes

The second commit builds upon the initial MVC refactor, focusing on further cleanup and optimisation:

- **File Cleanup:** Deleted unnecessary files, such as `Play.java` and `Snake.java`, streamlining the project structure.
  
- **Interface Removal:** Eliminated the outdated `movable` interface, recognising its redundancy.
  
- **View Optimisation:** Fine-tuned the `View` class, allowing the snake to navigate seamlessly around boundaries.
  
- **Class Creation:** Created new classes, including `Controller`, `MySnake`, and `SnakeObject`, to reinforce the single responsibility principle and enhance code modularity.
  
- **Version Control Cleanup:** Ensured the project adheres to cleaner version control practices, addressing issues like accidentally created directories.

These combined changes mark a significant step towards a well-organised, maintainable, and extensible Snake Game project, aligning with best practices in software development.

## Final Documentation

### Final Class Diagram

![High-level Class Diagram](JulioGuilherme_Design.png)




![UML Diagram](JulioGuilherme_Design_UML.png)




## Refactoring Activities

* Files in my project are well-organized with meaningful names. Class types like Models and Controllers are grouped into separate packages for clarity, featuring names such as SnakeModel and PlayerModel. This enhances organisation and highlights the functionality of each class.

* Classes like Food were renamed to ItemsModel, employing encapsulation for code organization and access management. Encapsulation is applied to various classes, including Entity, ImageUtil, ItemsModel, PlayerModel, and SnakeModel. Unused resources, such as Play, Paddle, MyFrame, GameUtil, Snake, movable interface, and some assets, were deleted.

* Classes like SnakeModel and Entity follow the single responsibility principle, separating concerns previously handled by the MyFrame class. The GameController now exclusively manages snake logic.

* The MVC pattern is implemented, with Models and Controllers organised in specific folders like SnakeGame.Models and SnakeGame.Controllers. FXML is used for MVC implementation, with models storing and retrieving data, and controllers applying the data for interactions and game logic.

* The singleton pattern is utilised for StageManager, offering centralised control and global accessibility for managing the main stage and scene transitions in the JavaFX application.

* JUnit is employed for comprehensive unit, integration, and UI testing to ensure component correctness and interactions. 

* Maven is correctly implemented with dependencies specified in the pom.xml file.

* The project transitions from Swing to JavaFX, with fxml files in the resources folder and JavaFX features incorporated into model and controller classes. 

* The module-info.java file is located in src/main/java.

* The start screen, designed as the main menu, uses MenuBackground.jpg with a green colour theme. The settings page allows sound muting/unmuting, and accessibility features include a black and white mode for colour-blind users.

* MenuController.java handles start/pause/end functionality and includes a settings button.

* GameController.java manages storing the player's name and passing it to DatabaseConnection.java for leaderboard updates.

* LeaderboardController.java displays all-time top scores and associated player names. The getTopPlayers() method adds the top 10 players to a VBox returned by the displayTopPlayers() method.

* ItemsModel.java manages object pictures, such as food, obstacles, power-ups. GameController.java handles the snake's body and head.

* The map's background can be changed when paused, and obstacles and power-ups enhance the gameplay experience. 

* Three different power-ups are added, and obstacles deduct one point from the snake and its body upon collision. Different random backgrounds can be selected, it's logic can be found in ItemsController.java

