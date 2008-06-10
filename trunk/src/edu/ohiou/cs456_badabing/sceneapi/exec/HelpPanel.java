package edu.ohiou.cs456_badabing.sceneapi.exec;

/**
 * @(#)HelpPanel.java
 *
 *
 * @author
 * @version 1.00 2008/6/9
 */

import java.awt.Frame;
import java.awt.Frame.*;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.JComponent.*;
import java.io.*;
import java.awt.Dimension;
import java.awt.Toolkit;

public class HelpPanel extends JEditorPane
{
    public static void main(String [] args)
    {

        JFrame helpFrame=new JFrame("Help");
        helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helpFrame.setSize(100,100);

        //center frame
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        helpFrame.setSize(screenWidth / 2, screenHeight / 2);
        helpFrame.setLocation(screenWidth / 4, screenHeight / 4);

        HelpPanel helpPanel=new HelpPanel();

                JScrollPane pane = new JScrollPane(helpPanel);
                pane.setSize(100,100);
                helpFrame.add(pane);
                helpFrame.setVisible(true);

    }
    public HelpPanel()
    {

                setBackground(new Color(200,200,255));
                setEditable(false);
                setContentType("text/html");
                setText("<html><head><font size=6 face=Georgia>Accounting Style Deer Population Model Visualizer</head>"+
                                        "<body><br><br>CS 456 Spring 2008 <br><u>Team BadaBing</u><font size=5 /><i/><br>Lev Neiman<br>Ryan Whiteside<br>Gene Farber<br>"+
                                        "Jared Hollins<br></i></font><br><font size=6 ><u>Shortcut Keys</u></font><br>"+
                                        "<font size=5>Ctrl-O   Open Excel File<br>Ctrl-1   View District Map Texture"+
                                        "<br>Ctrl-2   View Deer Management Map Texture<br>Ctrl-3   View Plain Black/White Map Texture</font>"+
                                        "<br></font><br><font size=6 ><u>Quick Facts</u></font><br><br><font size=5>-To load new harvest data and visualize it go to File->Open and select an "+
                                        "appropriate Excel file.<br><br>-The green spheres represent that county's deer population relative to "+
                                        "all of the other counties. That is the county with the most deer will have biggest sized sphere and the "+
                                        "one with smallest population will have the smallest sphere.<br><br> -Pie charts represent the ratio between 6 "+
                                        "different deer groups (fawn doe, fawn buck, yearling doe, yearling buck, adult doe, adult buck) inside "+
                                        "that county.<br><br> -On the right, in the “Harvest view” user can change the current year of the visualization, "+
                                        "and also zoom in on the county by using the drop down menus.<br><br>-A user can also select a county by left clicking "+
                                        "once on it in the 3D panel.<br><br> -To rotate the map hold the right mouse button down over the map and drag the mouse.</font></body></html>");

    }
}
