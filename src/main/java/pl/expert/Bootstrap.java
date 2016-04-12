/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert;

import java.awt.EventQueue;
import javax.swing.JFrame;
import pl.expert.ui.MainFrame;

/**
 *
 * @author Mariusz Batyra
 */
public class Bootstrap {

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                final JFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
        
    }
}
