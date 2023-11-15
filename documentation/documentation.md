# Project Documentation

This document provides an overview of the project.

## Diagrams

![Diagram 1](ClassDiagram.png)

## Overview

* **Questions?**

- How is the frame of the snake updating every button click?
    - MyFrame.keyPressed() - That’s the method that updates it’s frame depending on the key pressed. And what makes it move it’s the method called MyFrame.move()
- How does it detect it touched itself or the end of the screen?
    - For eating itself it uses the method MyFrame.eatbody() | and for all the ends of the screen it uses the method MyFrame.outofBounds().
- How does the snake grow, what images/code is used?
    - The snake grows depending on the food it eats and this method is found in Food.eaten()
- Where is the main logic of movement?
    - All the main logic of movement can be found in MyFrame.
- Where is the main logic for score updating
    - Score Updating is found in Food.eaten(), however is passed into MyFrame.MySnake and Play.drawScore actually displays it, so quite a confusing path until it’s displayed.

**Potential Adds to the code :**

- Play again button
- Different “The End” UI
- Pause button
- Faster mode?
- More items that the snake can eat / otherwise is too easy to only eat 16 items
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
