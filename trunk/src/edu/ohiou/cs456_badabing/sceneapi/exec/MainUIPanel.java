package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.awt.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import edu.ohiou.cs456_badabing.sceneapi.visualize.*;

import edu.ohiou.cs456_badabing.sceneapi.exec.data_model.*;

/**
 * <p>Main User Interface class for Accounting Style Deer Population Visualizer.  Also main class.</p>
 *
 * <p>Description: Main GUI class for our project.  Also main class.</p>
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

    public static final String use_vertex_arrays_text = "Disable vertex arrays";

    public static final String use_district_map = "District Map";
    public static final String use_deer_managment_map = "Deer Managment Map";
    public static final String use_plain_map = "Plain Black/White map";

    public static final String help_about_text = "About Deer Visualizer";

    private JFileChooser file_chooser_data = new JFileChooser( System.getProperty( "user.dir" ) );
    private JFileChooser file_chooser_screenshot = new JFileChooser( System.getProperty( "user.dir" ) );

    /**
     * handle to the DataBaseModel object which stores harveset data and organizes it for easy access.
     */
    private edu.ohiou.cs456_badabing.sceneapi.exec.data_model.DataBaseModel db = null;

    /**
     * current county name.
     */
    private static String current_county = null;

    /**
     * returns the DataBaseModel.
     * @return DataBaseModel
     */
    public DataBaseModel getDB()
    {
        return db;
    }

    /**
     * current year.
     */
    public int current_year;

    /**
     * set current year to year.
     * @param year int
     */
    public void setCurrentYear( int year )
    {
        this.current_year = year;
        try
        {
            DeerTuple t = db.counties.get(current_county).getYear(current_year);
            control_panel.tuple_info.setDeerTuple(t);
            control_panel.view_pred_panel.onYearSelected();
        }
        catch( Exception e )
        {
            // do nothing.
        }
    }

    /**
     * return currently selected county.
     * @return String
     */
    public String getCurrentCounty()
    {
        return current_county;
    }

    /**
     * Sets current county to county.  Performs no error checking.
     * lets necessary components know about county being selected.
     * @param county String
     */
    public void setCurrentCounty( String county )
    {
        current_county = county;
        ViewPastPanel.county_combo_box.setSelectedItem(county);
        rendera.zoomIn( county );
        DeerTuple t = db.counties.get( county ).getYear( current_year );
        control_panel.tuple_info.setDeerTuple( t );
        control_panel.view_pred_panel.onCountySelected( county );
    }

    /**
     * default constructor.
     */
    public MainUIPanel()
    {
        try
        {
            jbInit();
            new Thread()
            {
              public void run()
              {
                  try
                  {
                      super.sleep(1000);
                      loadHarvestData( System.getProperty( "user.dir" ) + "\\data\\db.xls" );
                  }
                  catch( Exception e )
                  {
                      e.printStackTrace();
                  }


              }
            }.start();
            //loadHarvestData( System.getProperty( "user.dir" ) + "\\data\\db.xls" );

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * loads the harvest data into memory and updates neccessary component.
     * @param path String
     * @throws Exception
     */
    public void loadHarvestData( String path ) throws Exception
    {
        rendera.should_display = false;
        db = DataBaseModel.readHarvestDataFile(path);
        this.control_panel.view_past_panel.onDataBaseLoad();
        current_year = db.getMinYear();
        rendera.triggerUpdate();
        rendera.should_display = true;

    }

    /**
     * gets called when some action is performed.  What that action was is decided by getActionCommand();
     * @param e ActionEvent
     */
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
                    loadHarvestData(file_chooser_data.getSelectedFile().getAbsolutePath());

                   //Looper loopa = new Looper( this );
                }
                catch( Exception exc )
                {
                    JOptionPane.showMessageDialog(null, "Could not open this file.  Here is exception message: " + exc.getMessage());
                    exc.printStackTrace();
                    return;
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
        if( command.equals( use_vertex_arrays_text ) )
        {
            PieChart.flipUseVertexArrays();
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
        if( command.equals( help_about_text ) )
        {
            JDialog diag_box = new JDialog( frame, "About" );
            JScrollPane spane = new JScrollPane( new HelpPanel() );
            spane.setPreferredSize( new Dimension( 800, 600 ) );
            spane.getVerticalScrollBar().setValue( 0 );
            diag_box.add( spane );
            diag_box.setSize( new Dimension( 800, 800 ) );
            diag_box.setVisible( true );
            spane.repaint();
        }
    }

    /**
     * convert the first char in name to uppercase.
     * @param name String
     * @return String
     */
    public static String properName( String name )
    {
        char[] tmp = name.toCharArray();
        if( tmp[0] >= 97 )
        {
            tmp[0] -= 32;
        }
        String ret = new String( tmp );

        return ret;
    }

    /**
     * gets called when mouseOver event occurs over some county in ViewPanel.
     * @param name String
     */
    public void countyMouseOver( String name )
    {
        bottom_label.setText( properName( name ) );
    }

    public static JFrame frame;

    /**
     * main method for our project.
     * @param args String[]
     */
    public static void main( String[] args )
    {
        System.load( System.getProperty( "user.dir" ) + "/jogl.dll" );
        frame = new JFrame( "Accounting Style Deer Population Model Visualizer.  Team BadaBing, CS456 Spring 2008." );
        frame.setSize( 910, 700 );
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

    /**
     * handle to the control panel.
     */
    ViewDataControlPanel control_panel = null;

    /**
     * handle to the ViewPanel.
     */
    public ViewPanel rendera = null;

    /**
     * handle to the BottomLabel
     */
    BottomLabel bottom_label = null;

    /**
     * handle to the MenuBar.
     */
    MenuBar menu_bar = null;

    /**
     * method to initialize all GUI components.
     * @throws Exception
     */
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
        //loadStuffz( System.getProperty( "user.dir" ) + "\\data\\db.xls" );
    }
}


