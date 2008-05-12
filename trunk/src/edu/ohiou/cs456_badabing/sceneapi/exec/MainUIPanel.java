package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import javax.media.opengl.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * <p>Title: JOGL stuff</p>
 *
 * <p>Description: Experimentation with JOGL</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class MainUIPanel extends JPanel
{

    private JFileChooser file_chooser = new JFileChooser( System.getProperty( "user.dir" ) );

    private HashMap< String, java.awt.Color > deer_type_2_color = null;

    public MainUIPanel()
    {
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static void main( String[] args )
    {
        JFrame frame = new JFrame( "Accounting Style Deer Population Model Visualizer.  Team BadaBing, CS456 Spring 2008." );
        frame.setSize( 850, 630 );
        frame.addWindowListener(new WindowAdapter()
             {
                 public void windowClosing(WindowEvent e)
                 {
                     System.exit( 0 );
                 }
             });

        MainUIPanel main_panel = new MainUIPanel();
        frame.add( main_panel );
        frame.setVisible( true );

    }

    ViewDataControlPanel control_panel = null;
    ViewPanel rendera = null;
    BottomLabel bottom_label = null;
    MenuBar menu_bar = null;

    private void jbInit()
            throws Exception
    {
        file_chooser.addChoosableFileFilter( new ExcelFileFilter() );

        super.setLayout( new BorderLayout( ) );
        rendera = new ViewPanel( this );
        rendera.setPreferredSize( new Dimension( 600, 600 ) );
        control_panel = new ViewDataControlPanel( this );
        control_panel.setPreferredSize( new Dimension( 250, 600 ) );
        bottom_label = new BottomLabel( this );
        menu_bar = new MenuBar( this );
        super.add( menu_bar, BorderLayout.NORTH );
        super.add(rendera, BorderLayout.CENTER );
        super.add( control_panel, BorderLayout.EAST );
        super.add( bottom_label, BorderLayout.SOUTH );
    }
}
