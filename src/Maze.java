import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GRect;
import java.awt.*;

/**
 * Maze filling class
 * @author Kamran Poladov
 *
 */

public class Maze extends GCompound
{
    private int[][] mazeArray;
    private int blockSize = 20;
    private int offset = 7;
    private GRect[][] ar = new GRect[31][36];

    public Maze(int[][] mazeArray) {
        this.mazeArray = mazeArray;
        for (int i = 0; i < mazeArray.length; i++) {
            for (int j = 0; j < mazeArray[0].length; j++) {
                if (mazeArray[i][j] == 1)
                {
                    GRect block = new GRect(blockSize, blockSize);
                    block.setColor(Color.BLUE);
                    super.add(block, j * block.getWidth(), i * block.getHeight());
                }
                if (mazeArray[i][j] == 2) {
                    GRect food = new GRect(5, 5);
                    food.setFillColor(Color.pink);
                    food.setFilled(true);
                    super.add(food, j * blockSize + offset, i * blockSize + offset);
                    ar[i][j] = food;
                } else {
                    ar[i][j] = null;
                }
                if (mazeArray[i][j] == 4) {
                    for (int w = 0; w < 10; w++)
                    {
                        GLine homeLine = new GLine(j * blockSize, i * blockSize + w, j * blockSize + blockSize, i * blockSize + w);
                        homeLine.setColor(Color.WHITE);
                        super.add(homeLine);
                    }
                }
            }
        }
    }

    public void conceal(int i, int j) {
        remove(ar[i][j]);
    }
}