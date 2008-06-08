package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.ohiou.cs456_badabing.sceneapi.visualize.PieChart;

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
public class LegendPanel extends JPanel implements ChangeListener, MainUIPanelChild
{
    private JSlider t_slider = new JSlider( JSlider.HORIZONTAL, 0, 100, 100 );

    private MainUIPanel main_panel = null;

    public MainUIPanel getMainPanel()
    {
        return main_panel;
    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
    }

    private class ColorBox extends JPanel
    {

        ColorBox( Color c )
        {
            this.color = c;
            super.setSize( new Dimension( 20, 20 ) );
            super.setPreferredSize( new Dimension( 20, 20 ) );
        }

        Color color = new Color( 0, 0, 0 );
        public void paintComponent( Graphics g )
        {
            g.setColor( color );
            g.fillRect( 0, 0, super.getWidth()-1, super.getHeight()-1 );
            g.setColor( new Color( 0, 0, 0 ) );
            g.drawRect( 0, 0, super.getWidth()-1, super.getHeight()-1 );
        }


    }

    public void stateChanged( ChangeEvent e )
    {
        main_panel.rendera.changeTransperency( t_slider.getValue() );
    }


    public LegendPanel( MainUIPanel main_panel, int width, int height )
    {
        addMainPanel( main_panel );
       // super.setSize( new Dimension( 300, 400 ) );

        JPanel left = new JPanel();
       // left.setLayout( new GridLayout( 3, 2 ) );
        left.setPreferredSize( new Dimension( 105, 100 ) );

        left.add( new ColorBox( PieChart.colors[0] ) );
        left.add( new JLabel( "Fawn Doe" ) );

        left.add( new ColorBox( PieChart.colors[1] ) );
        left.add( new JLabel( "Yearling Doe" ) );

        left.add( new ColorBox( PieChart.colors[2] ) );
        left.add( new JLabel( "Adult Doe" ) );

        JPanel right = new JPanel();
        //right.setLayout( new GridLayout( 3, 2 ) );
        right.setPreferredSize( new Dimension( 110, 100 ) );

        right.add( new ColorBox( PieChart.colors[3] ) );
        right.add( new JLabel( "Fawn Buck" ) );

        right.add( new ColorBox( PieChart.colors[4] ) );
        right.add( new JLabel( "Yearling Buck" ) );

        right.add( new ColorBox( PieChart.colors[5] ) );
        right.add( new JLabel( "Adult Buck" ) );


        t_slider.setPaintLabels( true );
        t_slider.setPaintTicks( true );
        t_slider.setMinorTickSpacing( 10 );
        t_slider.setMajorTickSpacing( 20 );
        t_slider.setSnapToTicks( true );

        t_slider.addChangeListener( this );

        super.setPreferredSize( new Dimension( width, height ) );
        super.add( left );
        super.add( right );
        super.add( new JLabel( "Transperency:" ) );
        super.add( t_slider );
    }

}


