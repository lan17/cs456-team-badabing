package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;

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

    private void jbInit()
            throws Exception
    {
        JMenu file_menu = new JMenu( "File" );
        add( file_menu);
    }
}
