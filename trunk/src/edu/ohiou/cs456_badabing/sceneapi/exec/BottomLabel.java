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
public class BottomLabel extends javax.swing.JLabel implements MainUIPanelChild
{
    MainUIPanel main_panel = null;

    public BottomLabel( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );
        setText( "Welcome to Accounting Style Deer Population Model Visualizer!" );
    }

    public MainUIPanel getMainPanel()
    {
        return main_panel;
    }

    public void setText( String text )
    {
        super.setText( text );
    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
    }
}
