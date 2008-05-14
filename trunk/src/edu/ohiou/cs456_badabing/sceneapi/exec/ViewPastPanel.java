package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.SystemColor;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.*;
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
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();

    JSlider slider = new JSlider(JSlider.VERTICAL,1984,2008,2000);


    JCheckBox all = new JCheckBox("All");
    JCheckBox buck = new JCheckBox("Buck");
    JCheckBox doe = new JCheckBox("Doe");
    JCheckBox adult = new JCheckBox("Buck Adult");
    JCheckBox yearling = new JCheckBox("Buck Yearling");
    JCheckBox fawn = new JCheckBox("Buck Fawn");
    JCheckBox adult2 = new JCheckBox("Doe Adult");
    JCheckBox yearling2 = new JCheckBox("Doe Yearling");
    JCheckBox fawn2 = new JCheckBox("Doe Fawn");

    JPopupMenu jPopupMenu1 = new JPopupMenu();

    BorderLayout borderLayout1 = new BorderLayout();
    GroupLayout layout = new GroupLayout(this);
    JPopupMenu jPopupMenu3 = new JPopupMenu();
    TitledBorder titledBorder1 = new TitledBorder("");
    String[] years = { "Select Year", "2007", "2006","2005","2004","2003","2002","2001","2000" };
    String[] counties = {"Select County","Adams","Allen","Ashland","Ashtabula","Athens","Auglaaize","Belmont","Brown",
        "Butler","Carroll","Champaign","Clark","Clermont","Clinton","Columbiana","Coshocton","Crawford","Cuyahoga" };
    JComboBox jComboBox1 = new JComboBox(counties);
    JComboBox jComboBox2 = new JComboBox(years);


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

    /*public void paintComponent( Graphics g )
    {
        //g.drawImage( background_image.getImage(), 0, 0, null );

    }*/



    private void jbInit()
            throws Exception
    {

                this.setLayout(layout);
                layout.setAutoCreateGaps(true);
                layout.setAutoCreateContainerGaps(true);
        //background_image = new ImageIcon( System.getProperty( "user.dir" ) + background_image_file_name );

        jLabel1.setText("County:");
        jLabel2.setText("Year:");
        jLabel3.setText("Deer Filter");

        //slider.addChangeListener();
        slider.setMajorTickSpacing(2);
                //slider.setMinorTickSpacing(1);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);

          // Create a sequential group for the horizontal axis.

                GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

                hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel1).addComponent(jLabel2)
                                .addComponent(jLabel3).addComponent(slider).addComponent(all).addComponent(buck).addComponent(adult)
                                .addComponent(yearling).addComponent(fawn));
                hGroup.addGroup(layout.createParallelGroup().addComponent(jComboBox1).addComponent(jComboBox2)
                            .addComponent(doe).addComponent(adult2)
                                .addComponent(yearling2).addComponent(fawn2));
                //hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel3).addComponent(jLabel4));
                layout.setHorizontalGroup(hGroup);

                // Create a sequential group for the vertical axis.
                GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel1).addComponent(jComboBox1));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel2).addComponent(jComboBox2));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(slider));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel3));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(all));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(buck).addComponent(doe));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(adult).addComponent(adult2));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(yearling).addComponent(yearling2));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(fawn).addComponent(fawn2));

                layout.setVerticalGroup(vGroup);

    }

    public void jComboBox1_actionPerformed(ActionEvent e)
    {

    }
}


class ViewPastPanel_jComboBox1_actionAdapter implements ActionListener
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
