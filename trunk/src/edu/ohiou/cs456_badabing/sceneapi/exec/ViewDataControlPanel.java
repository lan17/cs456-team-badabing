package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class ViewDataControlPanel extends JPanel implements MainUIPanelChild
{
    private MainUIPanel main_panel = null;

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout2 = new BorderLayout();

    ViewPastPanel view_past_panel = null;
    ViewPredictionsPanel view_pred_panel = null;


    public DeerTuplePanel tuple_info;

    public ViewDataControlPanel( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );
        tuple_info = new DeerTuplePanel( main_panel );

        try
        {
            jbInit();

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public MainUIPanel getMainPanel()
    {
        return main_panel;
    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
    }

    private void jbInit()
            throws Exception
    {
        view_past_panel = new ViewPastPanel( main_panel );
        view_pred_panel = new ViewPredictionsPanel( main_panel );


        jTabbedPane1.add( "Harvest View", view_past_panel );
        jTabbedPane1.add( "Predictions view",view_pred_panel );
        jTabbedPane1.setPreferredSize( new Dimension( 280, 400 )  );

        jTabbedPane1.setMnemonicAt( 0, KeyEvent.VK_H );
        jTabbedPane1.setMnemonicAt( 1, KeyEvent.VK_P );

        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        //this.add(legend, java.awt.BorderLayout.SOUTH );
        this.add( tuple_info, java.awt.BorderLayout.SOUTH );


        //main_panel.loadStuffz( System.getProperty( "user.dir" ) + "\\data\\db.xls" );
    }

    public void jCheckBox1_actionPerformed(ActionEvent e)
    {

    }
}


class ViewDataControlPanel_jCheckBox1_actionAdapter
        implements ActionListener
{
    private ViewDataControlPanel adaptee;
    ViewDataControlPanel_jCheckBox1_actionAdapter(ViewDataControlPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}
