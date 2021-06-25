package covidiot;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class COVIDIOT {
    
    public static void main(String[] args) {
      int height = 700;
        int width = 700;
        
         PrintWriter writer;
         PrintWriter writer2;
        try {
            writer = new PrintWriter("./log.txt");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(COVIDIOT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            writer2 = new PrintWriter("./forgetfulLog.txt");
            writer2.print("");
            writer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(COVIDIOT.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFrame c = new JFrame("elcorona");
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        animationpanel ap = new animationpanel(height, width);
        c.setBackground(Color.BLACK);
        c.add(ap);
        c.pack();
        c.setVisible(true);
    }
}