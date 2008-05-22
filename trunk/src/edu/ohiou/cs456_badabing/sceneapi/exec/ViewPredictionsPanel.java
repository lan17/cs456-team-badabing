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
public class ViewPredictionsPanel extends JPanel implements MainUIPanelChild
{
    private MainUIPanel main_panel = null;

        //private String background_image_file_name = "\\data\\deer2.jpg";
    private ImageIcon background_image = null;
    JButton update = new JButton("Update Stage");

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();

        JTextField intPop = new JTextField(5);
        JTextField finPop = new JTextField(5);
    JTextField buckFawn = new JTextField(5);
    JTextField doeFawn = new JTextField(5);
    JTextField buckYear = new JTextField(5);
    JTextField doeYear = new JTextField(5);
    JTextField buckAdult = new JTextField(5);
    JTextField doeAdult = new JTextField(5);


    JPopupMenu jPopupMenu1 = new JPopupMenu();

    BorderLayout borderLayout1 = new BorderLayout();
    GroupLayout layout = new GroupLayout(this);

    TitledBorder titledBorder1 = new TitledBorder("");
    String[] stages = { "Hunt", "Winter Loss", "Birth","Summer Loss"};

    JComboBox jComboBox2 = new JComboBox(stages);


    public ViewPredictionsPanel( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //repaint();

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



    private void jbInit()throws Exception
    {
                this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        //background_image = new ImageIcon( System.getProperty( "user.dir" ) + background_image_file_name );

        jLabel1.setText("Start Pop");
        jLabel2.setText("Final Pop");
        jLabel3.setText("Stage");
        jLabel4.setText("Buck");
        jLabel5.setText("Doe");
        jLabel6.setText("Fawn");
        jLabel7.setText("Yearling");
        jLabel8.setText("Adult");
        jLabel9.setText("");

                layout.linkSize(buckFawn,buckYear,buckAdult,doeFawn,doeYear,doeAdult);
        // Create a sequential group for the horizontal axis.

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(intPop).addComponent(finPop).addComponent(jComboBox2)
                                        .addComponent(jLabel9).addComponent(jLabel6).addComponent(jLabel7).addComponent(jLabel8)
                                        .addComponent(update));
        hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel3)
                                        .addComponent(jLabel4).addComponent(buckFawn).addComponent(buckYear).addComponent(buckAdult));
        hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel5).addComponent(doeFawn).addComponent(doeYear)
                                        .addComponent(doeAdult));

        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(intPop).addComponent(jLabel1));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(finPop).addComponent(jLabel2));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jComboBox2).addComponent(jLabel3));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel9).addComponent(jLabel4)
                                        .addComponent(jLabel5));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel6).addComponent(buckFawn)
                                        .addComponent(doeFawn));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel7).addComponent(buckYear)
                                        .addComponent(doeYear));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel8).addComponent(buckAdult)
                                        .addComponent(doeAdult));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(update));

        layout.setVerticalGroup(vGroup);
        }

    public void jComboBox1_actionPerformed(ActionEvent e)
    {

    }
}


class ViewPredictionsPanel_jComboBox1_actionAdapter implements ActionListener
{
    private ViewPredictionsPanel adaptee;
    ViewPredictionsPanel_jComboBox1_actionAdapter(ViewPredictionsPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jComboBox1_actionPerformed(e);
    }
}
