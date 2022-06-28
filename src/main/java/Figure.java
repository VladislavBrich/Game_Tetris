import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Figure {
    Random random = new Random();
    private ArrayList<Block> figure = new ArrayList<Block>();
    private int[][] shape = new int[4][4];
    private int type, size, color;
    private int x = Game_Tetris.FIELD_WIDTH / 2 - 2, y = 0; //starting left up corner

    Figure() {
        type = random.nextInt(Game_Tetris.SHAPES.length);
        size = Game_Tetris.SHAPES[type][4][0];
        color = Game_Tetris.SHAPES[type][4][1];
        if (size == 4) y = -1;
        {
            for (int i = 0; i < size; i++) {
                System.arraycopy(Game_Tetris.SHAPES[type][i], 0, shape[i], 0,
                        Game_Tetris.SHAPES[type][i].length);
                createFromShape();
            }
        }
    }

    void createFromShape() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (shape[j][i] == 1) figure.add(new Block(i + this.x
                        , j + this.y));
            }
        }
    }

    boolean isTouchGround() {
        for (Block block : figure) {
            if (Game_Tetris.gameField[block.getY() + 1][block.getX()] > 0) {
                return true;
            }
        }
        return false;
    }


    boolean isCrossGround() {
        for (Block block : figure) {
            if (Game_Tetris.gameField[block.getY()][block.getX()] > 0) {
                return true;
            }
        }
        return false;
    }

    void leaveOnTheGround() {
        for (Block block : figure) {
            Game_Tetris.gameField[block.getY()][block.getX()] = color;
        }

    }

    void stepDown() {
        for (Block block : figure) block.setY(block.getY() + 1);
        y++;
    }

    void drop() {
        while (!isTouchGround()) {
            stepDown();
        }
    }

    boolean isTouchWall(int keyCode) {
        for (Block block : figure) {
            if (keyCode == Game_Tetris.LEFT && (block.getX() == 0 || Game_Tetris.gameField[block.getY()][block.getX() - 1] > 0))
                return true;
            if (keyCode == Game_Tetris.RIGHT && (block.getX() == Game_Tetris.FIELD_WIDTH - 1 || Game_Tetris.gameField[block.getY()][block.getX() + 1] > 0))
                return true;
        }
        return false;
    }

    void move(int keyCode) {
        if (!isTouchWall(keyCode)) {
            int dx = keyCode - 38;
            for (Block block : figure) block.setX(block.getX() + dx);
            x += dx;
        }

    }

    boolean isWrongPosition() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (shape[j][i] == 1) {
                    if (j + this.y < 0) return true;
                    if (i + this.x < 0 || i + this.x > Game_Tetris.FIELD_WIDTH - 1) return true;
                    if (Game_Tetris.gameField[j + this.y][i + this.x] > 0) return true;
                }
        return false;
    }

    void rotate() {
        for (int i = 0; i < size / 2; i++) {
            for (int j = i; j < size - 1 - i; j++) {
                int tmp = shape[size - 1 - j][i];
                shape[size - 1 - j][i] = shape[size - 1 - i][size - 1 - j];
                shape[size - 1 - i][size - 1 - j] = shape[j][size - 1 - i];
                shape[j][size - 1 - i] = shape[i][j];
                shape[i][j] = tmp;
            }
        }
        if (!isWrongPosition()) {
            figure.clear();
            createFromShape();
        }
    }

    void paint(Graphics g) {
        for (Block block : figure) block.paint(g, color);
    }
}
 class Block {
    private int x, y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.drawRoundRect(x * Game_Tetris.BLOCK_SIZE + 1, y * Game_Tetris.BLOCK_SIZE + 1,
                Game_Tetris.BLOCK_SIZE - 2, Game_Tetris.BLOCK_SIZE - 2, Game_Tetris.ARC_RADIUS, Game_Tetris.ARC_RADIUS);
        g.fillRect(x * Game_Tetris.BLOCK_SIZE + 1, y * Game_Tetris.BLOCK_SIZE + 1,
                Game_Tetris.BLOCK_SIZE - 2, Game_Tetris.BLOCK_SIZE - 2);
    }
}

