package edu.ohiou.lev_neiman.sceneapi.edit;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;

/**
 * <p>Title: MenuBar</p>
 *
 * <p>Description: Main Menu for Material Editor</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class MenuBar extends JMenuBar
{
    public static final String open_file_text = "Open OBJ";
    public static final String save_file_text = "Save";
    public static final String save_file_as_text = "Save as";
    public static final String exit_text = "Exit";

    private JMenuItem open_file = new JMenuItem( open_file_text );
    private JMenuItem save_file = new JMenuItem( save_file_text );
    private JMenuItem save_file_as = new JMenuItem( save_file_as_text );
    private JMenuItem exit = new JMenuItem( exit_text );

    MaterialEditorPanel parent;

    public MenuBar( MaterialEditorPanel parent )
    {
        this.parent = parent;
        try
        {
            jbInit( );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        open_file.setMnemonic(KeyEvent.VK_O);
        save_file.setMnemonic( KeyEvent.VK_S );
        exit.setMnemonic( KeyEvent.VK_X );

        JMenu main_menu = new JMenu( "File" );
        main_menu.setMnemonic( KeyEvent.VK_F );
        main_menu.add( open_file );
        main_menu.add( save_file );
        main_menu.add( save_file_as );
        main_menu.addSeparator( );
        main_menu.add( exit );



        open_file.addActionListener( parent );
        save_file.addActionListener( parent );
        save_file_as.addActionListener( parent );
        exit.addActionListener( parent );
        super.add( main_menu );
    }
}
