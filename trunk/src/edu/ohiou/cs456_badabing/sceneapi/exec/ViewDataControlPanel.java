package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.*;
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

    public ViewDataControlPanel( MainUIPanel main_panel )
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
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setText( "Control Box" );
        jCheckBox1.setText( "jCheckBox1" );
        jCheckBox1.addActionListener(new
                ViewDataControlPanel_jCheckBox1_actionAdapter(this));
        this.setLayout(borderLayout1);
        this.setInputVerifier(null);
        jPanel2.setBackground(Color.green);
        jPanel2.setLayout(borderLayout2);
        //this.add(jPanel1, java.awt.BorderLayout.SOUTH);
        //this.add(jLabel1, java.awt.BorderLayout.NORTH);
        //this.add(jPanel2, java.awt.BorderLayout.CENTER);
        view_past_panel = new ViewPastPanel( main_panel );
        jTabbedPane1.add( "Data Base View", view_past_panel );
        JPanel pred_view = new JPanel( new BorderLayout( ) );
        pred_view.add( new JLabel("OMG" ) );
        jTabbedPane1.add( "Predictions view",pred_view );
        jTabbedPane1.setPreferredSize( new Dimension( 250, 600 )  );
        //jPanel2.add(jTable1, java.awt.BorderLayout.NORTH);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }

    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JCheckBox jCheckBox1 = new JCheckBox();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout2 = new BorderLayout();

    ViewPastPanel view_past_panel = null;

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
