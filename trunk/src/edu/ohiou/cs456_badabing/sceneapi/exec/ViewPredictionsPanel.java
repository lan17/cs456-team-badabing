package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.SystemColor;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.*;
import javax.swing.JTable;
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

    JButton update = new JButton("Update Stage");
    BorderLayout borderLayout1 = new BorderLayout();
    GroupLayout layout = new GroupLayout(this);

        //PredictHarvestTable harvestTable;

    String rows[][] ={{"ExpFctr","","",""},{"SexRto","","",""},
                      {"RepRt","","",""},{"SumLss","","",""},{"WinHrvst","","",""},
                      {"WinBrn","","",""},{"AntLss","","",""},{"Antlrd","","",""}};
    String cols[] = {" ","Fawn","Yearling","Adult"};
//    JTable popTable=new JTable(rows,cols);
    JTable popTable = new PredictionFactorTable();
    String hvrows[][]={{"Doe Fawn","",""},{"Doe Yearling","",""},
                                        {"Doe Adult","",""},{"Buck Fawn","",""},
                                        {"Buck Yearling","",""},{"Buck Adult","",""}};
    String hvcols[]={" ","Harvest","Default"};
    JTable harvestTable=new JTable(hvrows,hvcols);


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
    }

    public MainUIPanel getMainPanel()
    {
        return main_panel;
    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;

    }
    private void jbInit()throws Exception
    {
        this.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JScrollPane scrollPane = new JScrollPane(popTable);
                JScrollPane scrollPane2 = new JScrollPane(harvestTable);
                //scrollPane.setFillsViewportHeight(false);

        // Create a sequential group for the horizontal axis.

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().addComponent(scrollPane).addComponent(scrollPane2));

        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(scrollPane));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(scrollPane2));

        layout.setVerticalGroup(vGroup);

    }
    /*public void actionPerformed(ActionEvent e)
    {

                if(e.getSource()==jComboBox1)
        {
                        Integer county = (Integer)jComboBox1.getSelectedIndex();
                              String temp = (String)jComboBox1.getItemAt(county);
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
    }*/
}
