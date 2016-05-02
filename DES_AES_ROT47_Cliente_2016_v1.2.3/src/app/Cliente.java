package app;

import app.frames.ClientInterface;

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918

 * @version 5.0
 * 
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new InterfaceAntiga().setVisible(true);
                new ClientInterface().setVisible(true);
            }
        });
    }
}
