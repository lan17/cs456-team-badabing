package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

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
public class MenuBar extends JMenuBar implements MainUIPanelChild
{
    MainUIPanel main_panel = null;

    public MenuBar( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
    }

    public MainUIPanel getMainPanel( )
    {
        return main_panel;
    }

    public JRadioButtonMenuItem use_district_map;
    public JRadioButtonMenuItem use_deer_managment_map;
    public JRadioButtonMenuItem use_plain_map;

    public ArrayList< JRadioButtonMenuItem > maps_items = new ArrayList<JRadioButtonMenuItem>();


    private void jbInit()
            throws Exception
    {
        JMenu file_menu = new JMenu( "File" );

        JMenuItem open_file = new JMenuItem( main_panel.open_file_text );
        JMenuItem save_screenshot = new JMenuItem( main_panel.save_screenshot_text );
        JMenuItem exit_prog = new JMenuItem( main_panel.exit_program_text );

        open_file.addActionListener( main_panel );
        save_screenshot.addActionListener( main_panel );
        exit_prog.addActionListener( main_panel );

        file_menu.add( open_file );
        file_menu.add( save_screenshot );
        file_menu.addSeparator();
        file_menu.add( exit_prog );
        add( file_menu);

        JMenu view_menu = new JMenu( "View" );
        JCheckBoxMenuItem show_hide_charts = new JCheckBoxMenuItem( main_panel.show_hide_charts_text );
        JCheckBoxMenuItem show_hide_spheres = new JCheckBoxMenuItem( main_panel.show_hide_spheres_text );

        JMenu choose_map = new JMenu( "Pick map" );
        use_district_map = new JRadioButtonMenuItem( main_panel.use_district_map );
        use_district_map.setSelected( true );
        use_deer_managment_map = new JRadioButtonMenuItem( main_panel.use_deer_managment_map );
        use_plain_map = new JRadioButtonMenuItem( main_panel.use_plain_map );

        maps_items.add( use_district_map );
        maps_items.add( use_deer_managment_map );
        maps_items.add( use_plain_map );

        use_district_map.addActionListener( new ChooseMapActionListener( this ) );
        use_deer_managment_map.addActionListener( new ChooseMapActionListener( this ) );
        use_plain_map.addActionListener( new ChooseMapActionListener( this ) );

        choose_map.add( use_district_map );
        choose_map.add( use_deer_managment_map );
        choose_map.add( use_plain_map );

        show_hide_charts.addActionListener( main_panel );
        show_hide_spheres.addActionListener( main_panel );

        view_menu.add( show_hide_charts );
        view_menu.add( show_hide_spheres );
        view_menu.addSeparator();
        view_menu.add( choose_map );
        add( view_menu );
    }
}

class ChooseMapActionListener implements ActionListener
{
    MenuBar adaptee;
    ChooseMapActionListener( MenuBar adaptee )
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed( ActionEvent e )
    {
        for( JRadioButtonMenuItem i : adaptee.maps_items )
        {
            i.setSelected( false );
        }
        ((JRadioButtonMenuItem)e.getSource()).setSelected( true );
        adaptee.main_panel.actionPerformed( e );
    }

}
