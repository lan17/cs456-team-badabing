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
 * <p>JPanel to let user select County and year.</p>
 *
 * <p>Description: this panel contains 2 combo boxes to select year and county, and also a slider for optional year selection.</p>
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



    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();

    JSlider slider = new JSlider(JSlider.VERTICAL);

    BorderLayout borderLayout1 = new BorderLayout();
    GroupLayout layout = new GroupLayout(this);
    JPopupMenu jPopupMenu3 = new JPopupMenu();
    TitledBorder titledBorder1 = new TitledBorder("");

    public static JComboBox county_combo_box = new JComboBox();
    JComboBox jComboBox2 = new JComboBox();


    public ViewPastPanel( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );
        try
        {
            jbInit();
            //main_panel.loadHarvestData( System.getProperty( "user.dir" ) + "\\data\\db.xls" );
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

    public void onDataBaseLoad()
    {
        this.county_combo_box.removeAllItems();
        county_combo_box.addItem("Select County");

        for( Object i : main_panel.getDB().counties.keySet().toArray() )
        {
            this.county_combo_box.addItem( i );
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

        jLabel1.setText("County:");
        jLabel2.setText("Year:");

                county_combo_box.addActionListener(this);
        jComboBox2.addActionListener( this );
        slider.addChangeListener( this );

        // Create a sequential group for the horizontal axis.

        JPanel dummy = new JPanel();

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel1).addComponent(jLabel2)
                        .addComponent(slider));
        hGroup.addGroup(layout.createParallelGroup().addComponent(county_combo_box).addComponent(jComboBox2));

        //hGroup.addGroup(layout.createParallelGroup().addComponent( legend ).addComponent(legend));

        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel1).addComponent(county_combo_box));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel2).addComponent(jComboBox2));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(slider));



        //vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent( legend ).addComponent( legend )  );

        layout.setVerticalGroup(vGroup);

        county_combo_box.addActionListener( this );
        jComboBox2.addActionListener( this );
        slider.addChangeListener( this );
        }


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
        try
        {
            if (e.getSource() == county_combo_box)
            {
                Integer county = (Integer) county_combo_box.getSelectedIndex();
                String temp = (String) county_combo_box.getItemAt(county);
                main_panel.setCurrentCounty( temp );
            }

            if (e.getSource() == jComboBox2)
            {
                Integer year = (Integer) jComboBox2.getSelectedIndex();
                Integer temp2 = (Integer) jComboBox2.getItemAt(year);
                int num = temp2.intValue();
                slider.setValue(num);
                main_panel.setCurrentYear(num);
                main_panel.rendera.triggerUpdate();
            }
        }
        catch( Exception ex )
        {
            // do nothing.
        }
    }
}
