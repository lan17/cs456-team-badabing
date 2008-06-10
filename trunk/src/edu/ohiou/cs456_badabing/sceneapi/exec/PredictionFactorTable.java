package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.JTable;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.TableModelListener;
import edu.ohiou.cs456_badabing.sceneapi.exec.data_model.*;
import java.awt.Component;
import java.awt.*;

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
public class PredictionFactorTable extends JTable
{

    private class Renderer extends JLabel implements TableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value,
                                            boolean isSelected, boolean hasFocus,
                                            int row, int column)
        {
            if( (String) value == no_value )
            {
                return new JPanel()
                        {
                        public void paintComponent( Graphics g )
                        {

                            g.setColor( new Color( 200, 200, 200 ) );
                            g.fillRect( 0, 0, 100, 100 );
                        }
                        };

            }
            JLabel ret = new JLabel();
            ret.setText( (String) value );
            return (Component)ret;
        }


    }

    private class Model implements TableModel
    {



        private PredictionData default_values = new PredictionData();

        int width = 4;
        int height = PredictionData.names.length;

        String [][] data = new String[height][width];


        public Model()
        {
            for( int i = 0; i < height; ++i )
            {
                for( int j = 0; j < width; ++j )
                {
                    data[i][j] = no_value;
                }
            }

            for( int i = 0; i < height; ++i )
            {
                data[i][0] = PredictionData.names[i];
            }
            data[5][1] = "Fawn";
            data[5][2] = "Yearling";
            data[5][3] = "Adult";

            data[0][1] = Double.toString( default_values.expansion_factor );
            data[1][1] = Double.toString( default_values.winter_rate );
            data[2][1] = Double.toString( default_values.winter_rate_bucks );
            data[3][1] = Double.toString( default_values.antlered );
            data[4][1] = Double.toString( default_values.antlerless );

            data[6][1] = Double.toString( default_values.sex_fawn );
            data[6][2] = Double.toString( default_values.sex_year );
            data[6][3] = Double.toString( default_values.sex_adult );

            data[7][1] = Double.toString( default_values.rep_fawn );
            data[7][2] = Double.toString( default_values.rep_year );
            data[7][3] = Double.toString( default_values.rep_adult );

            data[8][1] = Double.toString( default_values.summer_fawn );
            data[8][2] = Double.toString( default_values.summer_year );
            data[8][3] = Double.toString( default_values.summer_adult );
        }

            /**
         * Returns the number of rows in the model. A
         * <code>JTable</code> uses this method to determine how many rows it
         * should display.  This method should be quick, as it
         * is called frequently during rendering.
         *
         * @return the number of rows in the model
         * @see #getColumnCount
         */
        public int getRowCount()
        {
            return height;
        }

        /**
         * Returns the number of columns in the model. A
         * <code>JTable</code> uses this method to determine how many columns it
         * should create and display by default.
         *
         * @return the number of columns in the model
         * @see #getRowCount
         */
        public int getColumnCount()
        {
            return 4;
        }

        /**
         * Returns the name of the column at <code>columnIndex</code>.  This is used
         * to initialize the table's column header name.  Note: this name does
         * not need to be unique; two columns in a table can have the same name.
         *
         * @param	columnIndex	the index of the column
         * @return  the name of the column
         */
        public String getColumnName(int columnIndex)
        {
            return " ";
        }

        /**
         * Returns the most specific superclass for all the cell values
         * in the column.  This is used by the <code>JTable</code> to set up a
         * default renderer and editor for the column.
         *
         * @param columnIndex  the index of the column
         * @return the common ancestor class of the object values in the model.
         */
        public Class<?> getColumnClass(int columnIndex)
        {
            return String.class;
        }

        /**
         * Returns true if the cell at <code>rowIndex</code> and
         * <code>columnIndex</code>
         * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
         * change the value of that cell.
         *
         * @param	rowIndex	the row whose value to be queried
         * @param	columnIndex	the column whose value to be queried
         * @return	true if the cell is editable
         * @see #setValueAt
         */
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            if( columnIndex == 0 ) return false;
            if( rowIndex == 5 ) return false;
            if( rowIndex < 5 && columnIndex > 1 ) return false;
            return true;
        }

        /**
         * Returns the value for the cell at <code>columnIndex</code> and
         * <code>rowIndex</code>.
         *
         * @param	rowIndex	the row whose value is to be queried
         * @param	columnIndex 	the column whose value is to be queried
         * @return	the value Object at the specified cell
         */
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return data[rowIndex][columnIndex];
        }

        /**
         * Sets the value in the cell at <code>columnIndex</code> and
         * <code>rowIndex</code> to <code>aValue</code>.
         *
         * @param	aValue		 the new value
         * @param	rowIndex	 the row whose value is to be changed
         * @param	columnIndex 	 the column whose value is to be changed
         * @see #getValueAt
         * @see #isCellEditable
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            try
            {
                double num = Double.parseDouble( (String)aValue );
            }
            catch( NumberFormatException num_ex )
            {
                return;
            }
            data[rowIndex][columnIndex] = (String)aValue;
        }

        /**
         * Adds a listener to the list that is notified each time a change
         * to the data model occurs.
         *
         * @param	l		the TableModelListener
         */
        public void addTableModelListener(TableModelListener l)
        {

        }

        /**
         * Removes a listener from the list that is notified each time a
         * change to the data model occurs.
         *
         * @param	l		the TableModelListener
         */
        public void removeTableModelListener(TableModelListener l)
        {

        }



    }

    private Model t_model = new Model();
    private Renderer t_render = new Renderer();
    private PredictionData default_values = new PredictionData();
    private String no_value = new String( " " );

    public PredictionFactorTable()
    {
        super.setModel( t_model );
        TableColumn titles = super.getColumnModel().getColumn( 0 );
       titles.setPreferredWidth( 200 );
    }

    /*
    public TableCellRenderer getCellRenderer(int row, int column) {
        return t_render;
    }
    */



}
