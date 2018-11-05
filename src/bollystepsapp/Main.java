package bollystepsapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import bollysteps.database.Database;
import bollysteps.ui.UI;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aayus
 */
public class Main {

    private static UI ui; 
    private static Database db;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File dbFile = new File(Database.dbFileName);
        if (dbFile.exists()) {
            db = Database.loadFrom(Database.dbFileName);
        } else {
            db = new Database();
            db.saveTo(Database.dbFileName);
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui = new UI(db);
                
                ui.getFrame().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            db.saveTo(Database.dbFileName);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });

    }

}
