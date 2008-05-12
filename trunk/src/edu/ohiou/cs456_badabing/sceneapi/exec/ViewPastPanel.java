package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.SystemColor;
import java.awt.*;
import javax.swing.border.TitledBorder;
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
public class ViewPastPanel extends JPanel implements MainUIPanelChild
{
    private MainUIPanel main_panel = null;


    private String background_image_file_name = "\\data\\deer2.jpg";
    private ImageIcon background_image = null;
    JLabel jLabel1 = new JLabel();
    JPopupMenu jPopupMenu1 = new JPopupMenu();

    BorderLayout borderLayout1 = new BorderLayout();
    JPopupMenu jPopupMenu3 = new JPopupMenu();
    TitledBorder titledBorder1 = new TitledBorder("");
    String[] years = { "Select a year", "2007", "2006" };
    JComboBox jComboBox1 = new JComboBox(years);


    public ViewPastPanel( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        repaint();

    }

    public MainUIPanel getMainPanel()
    {
        return main_panel;
    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
    }

    public void paintComponent( Graphics g )
    {
        g.drawImage( background_image.getImage(), 0, 0, null );
    }



    private void jbInit()
            throws Exception
    {

        background_image = new ImageIcon( System.getProperty( "user.dir" ) + background_image_file_name );
        jLabel1.setText("Year:");
        this.setLayout(borderLayout1);

        jComboBox1.setToolTipText("pick a year.");


        this.add(jLabel1, java.awt.BorderLayout.NORTH);
        this.add(jComboBox1, java.awt.BorderLayout.SOUTH);

    }

    public void jComboBox1_actionPerformed(ActionEvent e)
    {

    }
}


class ViewPastPanel_jComboBox1_actionAdapter
        implements ActionListener
{
    private ViewPastPanel adaptee;
    ViewPastPanel_jComboBox1_actionAdapter(ViewPastPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jComboBox1_actionPerformed(e);
    }
}
