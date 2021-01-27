package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Window {

    public Window(int width, int height, String title, Game game){

        JFrame frame = new JFrame(title);

        frame.setResizable(false);
        //sets up how big window is going to be
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        frame.getContentPane().setCursor(blankCursor);


        //add game class to the frame because it uses canvas. Java works together with JFrame and canvas
        frame.add(game);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close application when 'x' is clicked
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
