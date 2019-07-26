/**
 * Ghost behaviour class
 * @author Kamran Poladov
 *
 */



public class Ghost extends Actor implements Runnable
{
    private int color;
    private int direction;
    private int speed = 10;
    private int blockSize = 20;
    private int[][] maze;

    public Ghost(String img, int x, int y, int color, int[][] maze)
    {
        super(img);
        this.color = color;
        this.maze = maze;
        setLocation(x, y);
    }

    public void setSpeed(int val) {
        speed = val;
    }

    @Override
    public void goLeft() {
        super.move(-speed, 0);
        super.setImage("./img/ghost_" + color + "_left.gif");
        super.move(-speed, 0);
    }

    @Override
    public void goRight() {
        super.move(speed, 0);
        super.setImage("./img/ghost_" + color + "_right.gif");
        super.move(speed, 0);
    }

    @Override
    public void goUp() {
        super.move(0, -speed);
        super.setImage("./img/ghost_" + color + "_up.gif");
        super.move(0, -speed);
    }

    @Override
    public void goDown() {
        super.move(0, speed);
        super.setImage("./img/ghost_" + color + "_down.gif");
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
                if (x == j * blockSize && y == i * blockSize && maze[i][j] == 1)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void run()
    {
//        double rand = Math.random();
//        int left, right, up, down;
//        if (!meetTheWall(super.getX(), super.getY() - blockSize)) {
//            up = Pacman.getTrack()[(int)(super.getX() / 20)][(int)((super.getY() - blockSize) / 20)];
//        } else
//            up = -1;
//        if (!meetTheWall(super.getX(), super.getY() + blockSize)) {
//            down = Pacman.getTrack()[(int)(super.getX() / 20)][(int)((super.getY() + blockSize) / 20)];
//        } else
//            down = -1;
//        if (!meetTheWall(super.getX() + blockSize, super.getY())) {
//            right = Pacman.getTrack()[(int)((super.getX() + blockSize) / 20)][(int)(super.getY() / 20)];
//        } else
//            right = -1;
//        if (!meetTheWall(super.getX() - blockSize, super.getY())) {
//            left = Pacman.getTrack()[(int)((super.getX() - blockSize) / 20)][(int)((super.getY() - blockSize) / 20)];
//        } else
//            left = -1;
//        if (right == up && right == down && right == left && up == down && up == left && down == left) {
//            direction = (int)(rand * 4);
//            if (direction == 0) {
//                goUp();
//            } else if (direction == 1) {
//                goDown();
//            } else if (direction == 2) {
//                goRight();
//            } else if (direction == 3) {
//                goLeft();
//            }
//        } else
//        {
//            int max = Math.max(Math.max(up, down), Math.max(right, left));
//            System.out.println(max);
//            if (max == up)
//            {
//                goUp();
//            } else if (max == down)
//            {
//                goDown();
//            } else if (max == right)
//            {
//                goRight();
//            } else if (max == left)
//            {
//                goLeft();
//            }
//        }
            double rand = Math.random();
            if (direction == 0) {
                if (meetTheWall(super.getX(), super.getY() - blockSize))
                    direction = (int)(rand * 4);
                else
                    goUp();
            } else if (direction == 1) {
                if (meetTheWall(super.getX(), super.getY() + blockSize))
                    direction = (int)(rand * 4);
                else
                    goDown();
            } else if (direction == 2) {
                if (meetTheWall(super.getX() + blockSize, super.getY()))
                    direction = (int)(rand * 4);
                else
                    goRight();
            } else if (direction == 3) {
                if (meetTheWall(super.getX() - blockSize, super.getY()))
                    direction = (int)(rand * 4);
                else
                    goLeft();
            }
    }
}
