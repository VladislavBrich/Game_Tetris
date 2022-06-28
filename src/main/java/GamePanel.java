import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < Game_Tetris.FIELD_WIDTH; i++) {
            for (int j = 0; j < Game_Tetris.FIELD_HEIGHT; j++) {
                if ((Game_Tetris.gameField[j][i] > 0)) {
                    g.setColor(new Color(Game_Tetris.gameField[j][i]));
                    g.fill3DRect(i * Game_Tetris.BLOCK_SIZE + 1, j * Game_Tetris.BLOCK_SIZE + 1,
                            Game_Tetris.BLOCK_SIZE + 1, Game_Tetris.BLOCK_SIZE - 1, true);
                }
            }
            if (Game_Tetris.gameOver) {
                g.setColor(Color.black);
                for (int y = 0; y < Game_Tetris.GAME_OVER_MSG.length; y++)
                    for (int x = 0; x < Game_Tetris.GAME_OVER_MSG[y].length; x++)
                        if (Game_Tetris.GAME_OVER_MSG[y][x] == 1) g.fill3DRect(x * 11 + 56, y * 11 + 160, 10, 10, true);

            } else
                Game_Tetris.figure.paint(g);
        }

    }

}

