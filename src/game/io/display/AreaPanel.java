package game.io.display;

import game.Game;
import game.io.input.GameInput;
import game.physical.Physical;
import world.AreaCoordinate;
import world.Coordinate;
import world.World;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 */
class AreaPanel extends JPanel {

    private static final int SQUARE_SIZE = GameDisplay.SQUARE_SIZE;


    public AreaPanel() {
        setBackground(Color.BLACK);
        setFont(new Font("SansSerif", Font.BOLD, SQUARE_SIZE));
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        final Game runningGame = GameDisplay.getRunningGame();
        final World world = runningGame.getWorld();

        Coordinate playerAt = runningGame.getActivePlayerActor().getCoordinate();
        AreaCoordinate playerAtAC = world.convertToAreaCoordinate(playerAt);
        Coordinate playerAreaOrigin = playerAt.offset(-playerAtAC.areaX, -playerAtAC.areaY);

        for (int y = 0; y < world.getAreaSizeInSquares().getHeight(); y++) {
            for (int x = 0; x < world.getAreaSizeInSquares().getWidth(); x++) {

                Coordinate thisCoordinate = playerAreaOrigin.offset(x, y);

                final Physical visible = world.getSquare(thisCoordinate).peek();

                final char mapSymbol = visible.getMapSymbol();
                final Color color = visible.getColor();
                final Color bgColor = visible.getBGColor();

                int placeX = (x) * SQUARE_SIZE;
                int placeY = (y) * SQUARE_SIZE + getInsets().top;

                SquareDrawer.drawSquare(g, mapSymbol, color, bgColor, SQUARE_SIZE, placeX, placeY);

                // Draw a cursor on the square targeted by the player.
                Coordinate target = GameInput.getPlayerTarget();
                if (target != null && target.equalTo(thisCoordinate)) {
                    SquareDrawer.drawOval(g, GameDisplay.CURSOR, SQUARE_SIZE, placeX, placeY);
                }
            }
        }

        ActionOverlay.drawOverlay((Graphics2D) g);
        EventLog.drawOverlay((Graphics2D) g);
    }
}
