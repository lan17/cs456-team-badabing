package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.SystemColor;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;



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
public class ViewPastPanel extends JPanel implements ChangeListener,ActionListener,MainUIPanelChild
{
    private MainUIPanel main_panel = null;

        //private String background_image_file_name = "\\data\\deer2.jpg";
    private ImageIcon background_image = null;

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();

    JSlider slider = new JSlider(JSlider.VERTICAL);
    JPopupMenu jPopupMenu1 = new JPopupMenu();

    BorderLayout borderLayout1 = new BorderLayout();
    GroupLayout layout = new GroupLayout(this);
    JPopupMenu jPopupMenu3 = new JPopupMenu();
    TitledBorder titledBorder1 = new TitledBorder("");
    //String[] years = { "Select Year"};
    //String[] counties = {"Select County"};
    JComboBox jComboBox1 = new JComboBox();
    JComboBox jComboBox2 = new JComboBox();


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

    public void onDataBaseLoad()
    {
        this.jComboBox1.removeAllItems();
        jComboBox1.addItem("Select County");

        for( Object i : main_panel.getDB().counties.keySet().toArray() )
        {
            this.jComboBox1.addItem( i );
        }
        int min_year = main_panel.getDB().getMinYear();
        int max_year = main_panel.getDB().getMaxYear();
                slider.setMinimum(min_year);
                slider.setMaximum(max_year);
                slider.setMajorTickSpacing(1);
                slider.setSnapToTicks(true);
                slider.setFont(new Font("slide",2,10));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        this.jComboBox2.removeAllItems();
        //jComboBox2.addItem("Select Year");
        for( int i = min_year; i <= max_year; i++ )
        {
            Integer boxed_i = new Integer( i );
            this.jComboBox2.addItem( boxed_i );
        }
    }

    private void jbInit()throws Exception
    {
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        //background_image = new ImageIcon( System.getProperty( "user.dir" ) + background_image_file_name );

        jLabel1.setText("County:");
        jLabel2.setText("Year:");


        //slider.addChangeListener();


                jComboBox1.addActionListener(this);
                jComboBox2.addActionListener( this );
                slider.addChangeListener( this );

        // Create a sequential group for the horizontal axis.

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel1).addComponent(jLabel2)
                        .addComponent(slider));
        hGroup.addGroup(layout.createParallelGroup().addComponent(jComboBox1).addComponent(jComboBox2));

        //hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel3).addComponent(jLabel4));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel1).addComponent(jComboBox1));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel2).addComponent(jComboBox2));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(slider));

        layout.setVerticalGroup(vGroup);

        jComboBox1.addActionListener( this );
        jComboBox2.addActionListener( this );
        slider.addChangeListener( this );
        }

    /*public void jComboBox1_actionPerformed(ActionEvent e)
    {

    }*/

        public void stateChanged(ChangeEvent e)
        {
                int year2=slider.getValue();
                        main_panel.setCurrentYear(year2);
                        Integer num2 = new Integer(year2);
                        jComboBox2.setSelectedItem(num2);
                        main_panel.rendera.triggerUpdate();
        }
     public void actionPerformed(ActionEvent e)
    {

        //adaptee.jComboBox1_actionPerformed(e);
        //JComboBox bx=(JComboBox)e.getSource();
                if(e.getSource()==jComboBox1)
                {

                Integer county = (Integer)jComboBox1.getSelectedIndex();
                                String temp = (String)jComboBox1.getItemAt(county);
                //System.out.println(county+" "+temp+" "+"eatit!");
                main_panel.rendera.zoomIn(temp);
                }

                if(e.getSource()==jComboBox2)
                {

                Integer year = (Integer)jComboBox2.getSelectedIndex();
                Integer temp2 = (Integer)jComboBox2.getItemAt(year);
                int num = temp2.intValue();
                slider.setValue(num);
                main_panel.setCurrentYear(num);
                main_panel.rendera.triggerUpdate();
                }

    }
}


/*class ViewPastPanel_jComboBox1_actionAdapter implements ActionListener
{
    private ViewPastPanel adaptee;
    ViewPastPanel_jComboBox1_actionAdapter(ViewPastPanel adaptee)
    {
        this.adaptee = adaptee;
    }
    jComboBox1.addActionListener(this);

    public void actionPerformed(ActionEvent e)
    {
        //adaptee.jComboBox1_actionPerformed(e);
        JComboBox bx=(JComboBox)e.getSource();
                String county = (String)bx.getSelectedItem();
                main_panel.rendera.zoomIn(county);
    }
}*/
