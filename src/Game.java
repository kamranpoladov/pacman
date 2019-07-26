import acm.graphics.*;
import acm.program.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Main pacman game class
 * @author Kamran Poladov
 *
 */

public class Game extends GraphicsProgram {
    private int direction = 2;
    private final static int REFRESH = 100;
    private int prevDir = -1;
    private boolean isLoser = false;
    private static int blockSize = 20;
    private Ghost[] ghostsFig = new Ghost[4];

    public void run() {
        setSize(750, 700);
        addKeyListeners();
        setBackground(Color.BLACK);
        int[][] mazeArray = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,3,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,3,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,4,4,4,4,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,0,0,0,0,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,2,2,2,2,2,2,2,0,0,0,1,1,0,0,0,0,1,1,0,0,0,2,2,2,2,2,2,2,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,3,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,3,1,1,1,1,1},
                {1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1},
                {1,1,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1},
                {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        };
        Maze maze = new Maze(mazeArray);
        Pacman pacman = new Pacman("./img/pacmanRight.gif", 360, 17 * 20, mazeArray);
        Ghost blinky = new Ghost("./img/ghost_0_up.gif", 380, 14 * 20, 0, mazeArray);
        Ghost pinky = new Ghost("./img/ghost_1_up.gif", 360, 14 * 20, 1, mazeArray);
        Ghost inky = new Ghost("./img/ghost_2_up.gif", 340, 14 * 20, 2, mazeArray);
        Ghost clyde = new Ghost("./img/ghost_3_up.gif", 320, 14 * 20, 3, mazeArray);
        GLabel score = new GLabel("Score: " + pacman.getScore(), 650, 650);
        GLabel highScore = new GLabel("High score: " + getHigh(), 400, 650);
        GLabel lives = new GLabel("Lives: " + pacman.getLives(), 50, 650);
        score.setColor(Color.WHITE);
        highScore.setColor(Color.WHITE);
        lives.setColor(Color.WHITE);
        add(maze);
        add(pacman);
        add(score);
        add(highScore);
        add(lives);
        add(blinky);
        add(pinky);
        add(inky);
        add(clyde);
        int[] deleteRegion;
        ghostsFig[0] = blinky;
        ghostsFig[1] = pinky;
        ghostsFig[2] = inky;
        ghostsFig[3] = clyde;
        pause(1000);
        while (!isLoser) {
            for (Ghost ghost :
                    ghostsFig)
            {
                ghost.run();
            }

            if (direction == 0)
            {
                if (pacman.meetTheWall(pacman.getX() - blockSize, pacman.getY())) {
                    direction = prevDir;
                    pacman.setSpeed(0);
                } else
                {
                    pacman.setSpeed(10);
                    pacman.goLeft();
                    prevDir = 0;
                }
            }
            else if (direction == 1)
            {
                if (pacman.meetTheWall(pacman.getX(), pacman.getY() - blockSize)) {
                    direction = prevDir;
                    pacman.setSpeed(0);
                } else
                {
                    pacman.setSpeed(10);
                    pacman.goUp();
                    prevDir = 1;
                }
            }
            else if (direction == 2)
            {
                if (pacman.meetTheWall(pacman.getX() + blockSize, pacman.getY())) {
                    direction = prevDir;
                    pacman.setSpeed(0);
                } else
                {
                    pacman.setSpeed(10);
                    pacman.goRight();
                    prevDir = 2;
                }
            }
            else if (direction == 3)
            {
                if (pacman.meetTheWall(pacman.getX(), pacman.getY() + blockSize)) {
                    direction = prevDir;
                    pacman.setSpeed(0);
                } else
                {
                    pacman.setSpeed(10);
                    pacman.goDown();
                    prevDir = 3;
                }
            }
            //pacman.setTrack((int)(pacman.getX() / blockSize), (int)(pacman.getY() / blockSize));
            if ((deleteRegion = pacman.meetTheFood()) != null) {
                maze.conceal(deleteRegion[0], deleteRegion[1]);
                pacman.setScore(pacman.getScore() + 1);
                score.setLabel("Score: " + pacman.getScore());
            }

            if (pacman.getBounds().intersects(pinky.getBounds()) ||
                    pacman.getBounds().intersects(inky.getBounds()) ||
                    pacman.getBounds().intersects(blinky.getBounds()) ||
                    pacman.getBounds().intersects(clyde.getBounds()))
            {
                pacman.setLives(pacman.getLives() - 1);
                if (pacman.getLives() == 0) {
                    pause(1000);
                    blinky.setLocation(-100, -1);
                    pinky.setLocation(-100, -1);
                    inky.setLocation(-100, -1);
                    clyde.setLocation(-100, -1);
                    pacman.dead();
                    isLoser = true;
                    GLabel loserMessage = new GLabel("YOU LOST! PRESS ENTER TO EXIT!", 260, 310);
                    loserMessage.setColor(Color.white);
                    add(loserMessage);
                    pacman.setSpeed(0);
                    blinky.setSpeed(0);
                    inky.setSpeed(0);
                    pinky.setSpeed(0);
                    clyde.setSpeed(0);
                    if (pacman.getScore() > getHigh()) {
                        setHigh(pacman.getScore());
                        highScore.setLabel("High score: " + getHigh());
                    }
                }
                else
                {
                    pause(1000);
                    blinky.setLocation(-100, -1);
                    pinky.setLocation(-100, -1);
                    inky.setLocation(-100, -1);
                    clyde.setLocation(-100, -1);
                    pacman.dead();
                    pause(2500);
                    pacman.setImage("./img/pacmanRight.gif");
                    pacman.setLocation(360, 17 * 20);
                    blinky.setLocation(380, 14 * 20);
                    pinky.setLocation(360, 14 * 20);
                    inky.setLocation(340, 14 * 20);
                    clyde.setLocation(320, 14 * 20);
                    direction = 2;
                    lives.setLabel("Lives: " + pacman.getLives());
                    pause(1000);
                }
            }
            pause(REFRESH);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isLoser && e.getKeyCode() == KeyEvent.VK_ENTER) {
            exit();
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                direction = 0;
                break;
            case KeyEvent.VK_W:
                direction = 1;
                break;
            case KeyEvent.VK_S:
                direction = 3;
                break;
            case KeyEvent.VK_D:
                direction = 2;
                break;
        }
    }

    public static int getHigh() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("highscore.txt"));
            return scanner.nextInt();
        } catch (FileNotFoundException e) {

        }
        return 0;
    }

    public static void setHigh(int newScore) {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(new File("highscore.txt"));
            printWriter.println(newScore);
            printWriter.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Game().start();
    }
}