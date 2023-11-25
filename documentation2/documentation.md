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
