# Project Documentation

This document provides an overview of the project.

## Diagrams

![Diagram 1](ClassDiagram.png)

## Overview

### Questions

1. **How is the frame of the snake updating every button click?**
   - The method responsible for updating the frame on button clicks is `MyFrame.keyPressed()`. The actual movement is handled by the `MyFrame.move()` method.

2. **How does it detect if it touched itself or the end of the screen?**
   - For detecting self-collision, the method `MyFrame.eatbody()` is used. To handle collisions with the screen edges, `MyFrame.outofBounds()` is employed.

3. **How does the snake grow, and what images/code is used?**
   - The snake grows by consuming food, and this growth logic is implemented in the `Food.eaten()` method.

4. **Where is the main logic of movement?**
   - The main logic for movement is centralized in the `MyFrame` class.

5. **Where is the main logic for score updating?**
   - Score updating is handled in `Food.eaten()`. The updated score is then passed to `MyFrame.MySnake`, and `Play.drawScore` displays it.

### Potential Adds to the Code

- Play again button
- Different “The End” UI
- Pause button
- Faster mode?
- More items that the snake can eat (to avoid it being too easy with only 16 items)
- Different music?
- Different background?
- Ending music as the snake dies.
- Levels or stages
- Power-ups
- Customization options
- Achievements or badges
- Obstacles avoidance
- High Scores and Leaderboard
- Sound Effects
- Tutorial (?)
- Random Events to add to the gameplay.
