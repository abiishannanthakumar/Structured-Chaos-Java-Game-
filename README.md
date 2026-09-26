# Final Programming Project

A 2D platformer game built in Java with Swing, created as a final project for Grade 11 Introduction to Computer Science.

Co-authored with Munsif.

## About

The player navigates through multiple levels, jumping across platforms, avoiding obstacles, and collecting coins to reach the door at the end of each level. Core mechanics include:

- Player movement with gravity and double-jump support
- Custom collision detection (feet, head, and side hitboxes)
- Platforms and moving obstacles
- Collectible coins
- Multiple levels with a level selector and background music per level

## Tech

- Java (Swing / AWT for UI and rendering)
- Built with Apache Ant / NetBeans project structure
- GUI forms designed with NetBeans' GUI Builder (`.form` files)

## Running it

This is a NetBeans project (Ant-based), so the easiest way to run it is:

1. Open the project folder in NetBeans (File → Open Project).
2. Let it index, then click Run (or press F6).

Alternatively, with a JDK and Ant installed, from the project root:

```
ant run
```

## Structure

```
src/finalprogrammingproject/   Java source + NetBeans .form files
pixelify-sans/                 Font used in the UI
*.wav                          Level and menu music
*.jpg / *.png                  Backgrounds and UI assets
build.xml                      Ant build script
```

## Notes

This was a school project built a few years ago — it's not actively maintained, but the code is preserved here as a portfolio piece.
