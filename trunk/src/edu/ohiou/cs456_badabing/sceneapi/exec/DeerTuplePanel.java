package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.event.TableModelListener;
import edu.ohiou.cs456_badabing.sceneapi.visualize.*;
import edu.ohiou.cs456_badabing.sceneapi.exec.data_model.*;

/**
 * <p>Panel that displays harvest data numbers in a table along with color coded squares.  Also contains transperency slider.</p>
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
public class DeerTuplePanel extends JPanel implements ChangeListener, MainUIPanelChild, ItemListener
{
    private class Table extends JTable implements TableModelListener
    {


        private class tableModel extends AbstractTableModel
        {
            private String [][] data = new String[4][3];

            public tableModel()
            {
                for( int i = 0; i < 4; ++i )
                {
                    for( int j = 0; j < 3; ++j )
                    {
                        data[i][j] = new String( "" );
                    }
                }
                data[1][0] = "Fawn";
                data[2][0] = "Yearling";
                data[3][0] = "Adult";

                data[0][1] = "Does";
                data[0][2] = "Bucks";
            }

            public void setDeerTupleInfo( DeerTuple info )
            {
                data[1][1] = Double.toString( info.fawn_does );
                data[2][1] = Double.toString( info.yearling_does );
                data[3][1] = Double.toString( info.adult_does );

                data[1][2] = Double.toString( info.fawn_bucks );
                data[2][2] = Double.toString( info.yearling_bucks );
                data[3][2] = Double.toString( info.adult_bucks );

                //data[0][0] = info.county;


                super.fireTableDataChanged();


            }

            public int getRowCount()
            {
                return 4;
            }

            public int getColumnCount()
            {
                return 3;
            }

            public String getColumnName(int columnIndex)
            {
                if( columnIndex == 1 ) return "Does";
                if( columnIndex == 2 ) return "Bucks";
                return "";
            }

            public Class<?> getColumnClass(int columnIndex)
            {
                return String.class;
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                return data[rowIndex][columnIndex];
            }

            public void setValueAt(Object aValue, int rowIndex, int columnIndex)
            {
                data[rowIndex][columnIndex] = (String)aValue;
            }

            public void addTableModelListener(TableModelListener l)
            {

            }

            public void removeTableModelListener(TableModelListener l)
            {

            }


        }

        private class tableRenderer extends JLabel implements TableCellRenderer
        {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                            boolean isSelected, boolean hasFocus,
                                            int row, int column)
            {
                final String text = (String)value;
                if( row >=1 && column >= 1 )
                {
                    final int frow = row;
                    final int fcol = column;
                    JPanel ret = new JPanel()
                    {
                        public void paintComponent(Graphics g)
                        {
                            Color label_color = new Color(0, 0, 0);
                            if (fcol == 1)
                            {
                                if (frow >= 1)
                                {
                                    label_color = PieChart.colors[frow - 1];
                                }
                            }
                            if (fcol == 2)
                            {
                                if (frow >= 1)
                                {
                                    label_color = PieChart.colors[frow + 2];
                                }
                            }
                            g.setColor(label_color);
                            g.fillRect( 2, 2, 10, 10);
                            g.setColor(new Color(0, 0, 0));
                            g.drawString(text, 20, 11);
                        }


                    };
                    return ret;
                 }
                 return new JLabel( text );



            }
        }

        private TableCellRenderer t_render = new tableRenderer();

        public Table()
        {
            super.setModel( new tableModel()  );
            super.getModel().addTableModelListener( this );
        }

        public TableCellRenderer getCellRenderer(int row, int column)
        {
            return t_render;
        }


    }

    Table table = new Table();
    JLabel top = new JLabel( "County, Year" );
    JLabel bottom = new JLabel( "Total" );

    private MainUIPanel main_panel;

    private JSlider t_slider = new JSlider( JSlider.HORIZONTAL, 0, 100, 100 );

    public DeerTuplePanel( MainUIPanel main_panel )
    {
        addMainPanel( main_panel );

        super.setPreferredSize( new Dimension( 250, 200 ) );
        BorderLayout layout = new BorderLayout();
        //super.setLayout( layout );

        JPanel info = new JPanel( layout );

        JPanel t1 = new JPanel();
        t1.add( top );

        JPanel t2 = new JPanel();
        t2.add( bottom );

        info.add( t1, BorderLayout.NORTH );
        info.add( table, BorderLayout.CENTER );
        info.add( t2, BorderLayout.SOUTH );

        t_slider.setPaintLabels( true );
        t_slider.setPaintTicks( true );
        t_slider.setMinorTickSpacing( 10 );
        t_slider.setMajorTickSpacing( 20 );
        t_slider.setSnapToTicks( true );

        t_slider.addChangeListener( this );


        add(info);
        t_slider.setEnabled( false );
        add(t_slider);
        JCheckBox enable_transperency = new JCheckBox( "Enable Transperency" );
        enable_transperency.addItemListener( this );
        add( enable_transperency );
    }

    public void stateChanged( ChangeEvent e )
    {
        main_panel.rendera.changeTransperency( t_slider.getValue() );
    }

    private boolean enable_t = false;

    public void itemStateChanged( ItemEvent e )
    {
        if( enable_t )
        {
            enable_t = false;
            t_slider.setEnabled( false );
        }
        else
        {
            enable_t = true;
            t_slider.setEnabled( true );
        }

        main_panel.rendera.setTransperency( enable_t );

    }

    public void setDeerTuple( DeerTuple info )
    {
        ( (Table.tableModel)table.getModel() ).setDeerTupleInfo( info );
        table.repaint();

        top.setText( MainUIPanel.properName( info.county ) + ", " + Integer.toString( info.year ) );
        bottom.setText( "Total = " + Double.toString( info.sum() ) );

    }

    public MainUIPanel getMainPanel( )
    {
        return main_panel;
    }

    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;
    }



}
