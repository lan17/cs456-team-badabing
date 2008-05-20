package edu.ohiou.cs456_badabing.sceneapi.exec;

import edu.ohiou.cs456_badabing.sceneapi.exec.data_model.*;
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
import java.awt.event.*;
import javax.swing.filechooser.*;

/**
 * <p>Main User Interface class for Accounting Style Deer Population Visualizer</p>
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
public class MainUIPanel extends JPanel implements ActionListener
{

    public static final String open_file_text = "Open Database";
    public static final String save_screenshot_text = "Save screenshot";
    public static final String exit_program_text = "Exit";

    public static final String show_hide_charts_text = "Hide pie charts";
    public static final String show_hide_spheres_text = "Hide spheres";

    public static final String use_district_map = "District Map";
    public static final String use_deer_managment_map = "Deer Managment Map";
    public static final String use_plain_map = "Plain Black/White map";

    private JFileChooser file_chooser_data = new JFileChooser( System.getProperty( "user.dir" ) );
    private JFileChooser file_chooser_screenshot = new JFileChooser( System.getProperty( "user.dir" ) );

    private HashMap< String, java.awt.Color > deer_type_2_color = null;

    public edu.ohiou.cs456_badabing.sceneapi.exec.data_model.DataBaseModel db = null;

    public int current_year;

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


    public void actionPerformed( ActionEvent e )
    {
        System.out.println( e.getActionCommand());

        String command = e.getActionCommand();
        if( command.equals( open_file_text ) )
        {
            if( file_chooser_data.showOpenDialog( this ) == file_chooser_data.APPROVE_OPTION )
            {
                System.out.println( file_chooser_data.getSelectedFile().getAbsolutePath() );
                try
                {
                    db = DataBaseModel.readFile(file_chooser_data.getSelectedFile().getAbsolutePath());
                    current_year = db.max_year;
                    rendera.update();
                }
                catch( Exception exc )
                {
                    JOptionPane.showMessageDialog(null, "Could not open this file.  Here is exception message: " + exc.getMessage());
                }
            }
        }
        if( command.equals( this.save_screenshot_text ) )
        {
            if( file_chooser_screenshot.showSaveDialog( this ) == file_chooser_screenshot.APPROVE_OPTION )
            {
                String file_name = file_chooser_screenshot.getSelectedFile().getAbsolutePath();
                String format = null;
                FileFilter file_filta = file_chooser_screenshot.getFileFilter();
                if( file_filta instanceof JPEGFileFilter )
                {
                    if( ! MyFileFilter.getExtension(file_name).equals( "jpg" ) ) file_name += ".jpg";
                    format = "jpg";
                }
                if( file_filta instanceof PNGFileFilter )
                {
                    if( ! MyFileFilter.getExtension(file_name).equals( "png" ) ) file_name += ".png";
                    format = "png";
                }
                if( file_filta instanceof BMPFileFilter )
                {
                    if( ! MyFileFilter.getExtension(file_name).equals( "bmp" ) ) file_name += ".bmp";
                    format = "bmp";
                }

                rendera.takeScreenshot( new File( file_name ), format );
            }
        }
        if( command.equals( exit_program_text ) )
        {
            System.exit( 0 );
        }
        if( command.equals( show_hide_charts_text ) )
        {
            rendera.getMap().setShowPie( ! rendera.getMap().isShowPies() );
        }
        if( command.equals( show_hide_spheres_text ) )
        {
            rendera.getMap().setShowSpheres( ! rendera.getMap().isShowSpheres() );
        }
        if( command.equals( use_deer_managment_map ) )
        {
            rendera.map.useDeerManagmentMap( rendera );
        }
        if( command.equals( use_district_map ) )
        {
            rendera.map.useDistrictMap( rendera );
        }
        if( command.equals( use_plain_map ) )
        {
            rendera.map.usePlainMap( rendera );
        }
    }

    public String properName( String name )
    {
        char[] tmp = name.toCharArray();
        if( tmp[0] >= 97 )
        {
            tmp[0] -= 32;
        }
        String ret = new String( tmp );

        return ret;
    }

    public void countyMouseOver( String name )
    {
        bottom_label.setText( properName( name ) );
    }

    public static void main( String[] args )
    {
        System.load( System.getProperty( "user.dir" ) + "/jogl.dll" );
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
        file_chooser_data.addChoosableFileFilter( new ExcelFileFilter() );
        file_chooser_screenshot.addChoosableFileFilter( new JPEGFileFilter() );
        file_chooser_screenshot.addChoosableFileFilter( new BMPFileFilter() );
        file_chooser_screenshot.addChoosableFileFilter( new PNGFileFilter() );

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
