/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui;

import bollysteps.ui.student.StudentsPanel;
import bollysteps.ui.danceclass.DanceClassesMainPanel;
import bollysteps.database.Database;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author aayus
 */

//contains main JFrame that the entire app is created on 
//methods to set up JFrame, sizing, looks

public class UI {

    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 1000;

    private JFrame frame;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JLabel statusBar;

    private Font titleFont;
    private Font textFont;
    private Font statusFont;

    private Database db;

    public UI(Database db) {
        this.db = db;
        this.createFrame();
        this.createFonts();
        this.createFrameComponents();
        this.layoutFrameComponents();
        showFrame();
    }
    
    private void showFrame() {
        frame.pack();
        frame.setVisible(true);
    }
    
    public JFrame getFrame() {
        return this.frame;
    }

    private void createFrame() {
        //center frame
        this.frame = new JFrame("Bollysteps Dance School");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        
        Image i = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(i);
    }

    private void createFonts() {
        this.titleFont = new Font("Dialog", Font.BOLD, 44);
        this.textFont = new Font("Times", Font.PLAIN, 30);
        this.statusFont = new Font("Dialog", Font.PLAIN, 24);
    }

    private void layoutFrameComponents() {
        Container pane = frame.getContentPane();

        BorderLayout frameLayout = new BorderLayout(5, 5);
        this.frame.setLayout(frameLayout);

        pane.add(this.mainPanel, BorderLayout.CENTER);
        pane.add(this.statusBar, BorderLayout.SOUTH);
    }

    private void createFrameComponents() {
        this.mainPanel = new JPanel();
        this.statusBar = new JLabel("Test");
        this.statusBar.setFont(this.statusFont);

        JPanel homePanel = new HomePanel(this);
        
        JPanel classesPanel = new DanceClassesMainPanel(this);
        
        JPanel studentsPanel = new StudentsPanel(this);
        
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setTabPlacement(JTabbedPane.TOP);
        this.tabbedPane.setFont(this.titleFont);
        this.tabbedPane.addChangeListener(new TabbedPanelListener());
        
        this.tabbedPane.addTab("Home",homePanel);      
        this.tabbedPane.addTab("Classes",classesPanel);
        this.tabbedPane.addTab("Students", studentsPanel);
        
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(this.tabbedPane,BorderLayout.CENTER);
    }
    
    public void gotoTab(int tabNum) {
        this.tabbedPane.setSelectedIndex(tabNum);
    }

    public Font getTitleFont() {
        return this.titleFont;
    }
    
    public Font getTextFont() {
        return this.textFont;
    }

    class TabbedPanelListener implements ChangeListener {
        
        public void stateChanged(ChangeEvent e) {
            statusBar.setText(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
        }
    };
    
    //getters and setters for Database Class
    
    public Database getDB() {
        return this.db;
    }
    
    public void setDB(Database db) {
        this.db = db; 
    }
    

}
