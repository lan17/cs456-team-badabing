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

    public static final String open_file_text = "Open Harvest Data Database";
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

    private edu.ohiou.cs456_badabing.sceneapi.exec.data_model.DataBaseModel db = null;

    public DataBaseModel getDB()
    {
        return db;
    }

    public int current_year;

    public void setCurrentYear( int year )
    {
        this.current_year = year;
    }

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
                    this.control_panel.view_past_panel.onDataBaseLoad();
                    current_year = db.getMaxYear();
                    rendera.triggerUpdate();

                   //Looper loopa = new Looper( this );
                }
                catch( Exception exc )
                {
                    JOptionPane.showMessageDialog(null, "Could not open this file.  Here is exception message: " + exc.getMessage());
                    exc.printStackTrace();
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
        frame.setMaximumSize( new Dimension( 1024, 768 ) );
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
    public ViewPanel rendera = null;
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
        //rendera.setPreferredSize( new Dimension( 600, 600 ) );
        JPanel rendera_container = new JPanel( new BorderLayout());
        rendera_container.add( rendera, BorderLayout.CENTER );
        control_panel = new ViewDataControlPanel( this );
        control_panel.setPreferredSize( new Dimension( 275, 600 ) );
        bottom_label = new BottomLabel( this );
        menu_bar = new MenuBar( this );
        super.add( menu_bar, BorderLayout.NORTH );
        super.add(rendera_container, BorderLayout.CENTER);
        super.add( control_panel, BorderLayout.EAST );
        super.add( bottom_label, BorderLayout.SOUTH );

        //rendera_container.addKeyListener( rendera );

    }
}


/**
 *
 * <p>LOL USELESS LOOPING THREAD HAHAHAH</p>
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
class Looper extends Thread
{
    MainUIPanel main_panel;

    int min_year;
    int max_year;

    Looper( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
        min_year =  main_panel.getDB().getMinYear();
        max_year = main_panel.getDB().getMaxYear();
        start();

    }

    public void run()
    {
        while( true )
        {
            for (int i = min_year; i <= max_year; ++i)
            {
                //main_panel.rendera.context.makeCurrent();
                main_panel.current_year = i;
                main_panel.rendera.triggerUpdate( );
                main_panel.bottom_label.setText( Integer.toString( i ));
                try
                {
                    sleep(300);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
