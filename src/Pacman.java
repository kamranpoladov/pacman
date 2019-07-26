import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Pacman behaviour class
 * @author Kamran Poladov
 *
 */

public class Pacman extends Actor
{
    private int speed = 20;
    private int blockSize = 20;
    private int refresh = 100;
    private int[][] maze;
    private int score = 0;
    private int lives = 3;
    //private static int[][] track = new int[31][36];

    public Pacman(String img, int xPos, int yPos, int[][] maze)
    {
        super(img);
        this.maze = maze;
        super.setLocation(xPos, yPos);
    }

    public int getScore() { return score; }

    public void setScore(int val) { score = val; }

    public void setSpeed(int val) {
        speed = val;
    }

    public int getLives() { return lives; }

    public void setLives(int val) { lives = val; }

//    public void setTrack(int x, int y) {
//        track[x][y] = findHighestValue(track) + 1;
//        //System.out.println(track[x][y]);
//    }

//    public static int[][] getTrack() {
//        return track;
//    }

//    public static int findHighestValue(int[][] doubles) {
//        int currentHighestValue = -1;
//        for (int row = 0; row < doubles.length; row++) {
//            for (int col = 0; col < doubles[row].length; col++) {
//                int value = doubles[row][col];
//                if (value > currentHighestValue) {
//                    currentHighestValue = value;
//                }
//            }
//        }
//        return currentHighestValue;
//    }

    @Override
    public void goLeft() {
        super.move(-speed , 0);
        super.setImage("./img/pacmanLeft.gif");
        super.pause(refresh);
        super.move(-speed, 0);
    }

    @Override
    public void goRight() {
        super.move(speed, 0);
        super.setImage("./img/pacmanRight.gif");
        super.pause(refresh);
        super.move(speed, 0);
    }

    @Override
    public void goUp() {
        super.move(0, -speed);
        super.setImage("./img/pacmanUp.gif");
        super.pause(refresh);
        super.move(0, -speed);
    }

    @Override
    public void goDown() {
        super.move(0, speed);
        super.setImage("./img/pacmanDown.gif");
        super.pause(refresh);
        super.move(0, speed);
    }

    @Override
    public boolean meetTheWall(double x, double y) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (y == 14 * blockSize && x < 0)
                    super.setLocation(maze[0].length * blockSize, 14 * blockSize);
                if (y == 14 * blockSize && x == maze[0].length * blockSize)
                    super.setLocation(0, 14 * blockSize);
                if (x == j * blockSize && y == i * blockSize && (maze[i][j] == 1 || maze[i][j] == 4))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] meetTheFood() {
        int[] ret = new int[2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 2 && super.getX() == j * blockSize && super.getY() == i * blockSize) {
                    maze[i][j] = 0;
                    ret[0] = i;
                    ret[1] = j;
                    return ret;
                }
            }
        }
        return null;
    }

    public void dead() {
//        String path = "./snds/death.wav";
//        try
//        {
//            Clip clip = AudioSystem.getClip();
//            clip.open(AudioSystem.getAudioInputStream(new File(path)));
//            clip.start();
//        } catch (Exception e) {
//
//        }
        super.setImage("./img/pacmanDead.gif");
    }
}
