package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import java.awt.SystemColor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.*;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.ohiou.cs456_badabing.sceneapi.exec.data_model.*;
import javax.swing.event.TableModelListener;

/**
 * <p>JPanel for predictions tab.</p>
 *
 * <p>Description: This panel contains Factors table, Harvest/Population table and button to predict next year's population.</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ViewPredictionsPanel extends JPanel implements MainUIPanelChild, ActionListener
{
        /**
         *
         * <p>Table Model for harvest and population table.</p>
         *
         * <p>Description: This model controls what can be entered in this table and where.</br>also contians data structure to hold table data.</p>
         *
         * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
         *
         * <p>Company: Ohio University EECS</p>
         *
         * @author Lev A Neiman
         * @version 1.0
         */
    private class harvestTableModel extends AbstractTableModel
    {
        /**
        * 2-D array containing
        */
        private String data[][] = new String[6][3];


        public harvestTableModel()
        {
            data[0][0] = "Doe Fawn";
            data[1][0] = "Doe Yearling";
            data[2][0] = "Doe Adult";
            data[3][0] = "Buck Fawn";
            data[4][0] = "Buck Yearling";
            data[5][0] = "Buck Adult";
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
            return 6;
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
            return 3;
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
            if( columnIndex == 0 ) return "";
            if( columnIndex == 1 ) return "Harvest Data";
            else return "Population";
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
            else return true;
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
                Double.parseDouble( (String) aValue );
            }
            catch( Exception e )
            {
                return;
            }
            double d = Double.parseDouble( (String)aValue );
            if( d < 0 ) d = 0;
            data[rowIndex][columnIndex] = Double.toString( d );
            super.fireTableDataChanged();

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

    /**
     * handle to the main panel.
     */
    private MainUIPanel main_panel = null;






        //PredictHarvestTable harvestTable;

    /**
    * Table to enter factors for prediction.
    */
    JTable popTable = new PredictionFactorTable();

    /**
     * table for harvest data and population
     */
    JTable harvestTable=new JTable( new harvestTableModel() );

    /**
     * default constructor
     * @param main_panel MainUIPanel
     */
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

    /**
     *
     * @return MainUIPanel
     */
    public MainUIPanel getMainPanel()
    {
        return main_panel;
    }

    /**
     *
     * @param main_panel MainUIPanel
     */
    public void addMainPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;

    }

    /**
     * gets called by MainUIPanel when user selects a county.
     * @param county String
     */
    public void onCountySelected( String county )
    {
        // get harvest tuple for current county, year.


        updateHarvestPop();

    }

    /**
     * updates harvest data as well as population data, and also the Label above the table.
     */
    private void updateHarvestPop()
    {
        String county = main_panel.getCurrentCounty();
        DeerTuple harvest_info = main_panel.getDB().counties.get( county ).getYear( main_panel.current_year );

        updateHarvestView( harvest_info );

        county_year_info.setText( main_panel.properName( county ) + ", " + Integer.toString( main_panel.current_year ));

        harvestTable.repaint();

        updateLabel( main_panel.getCurrentCounty(), main_panel.current_year );


       DeerTuple pop = new DeerTuple();
       double factor = harvest_info.sum() * .7;
       pop.adult_bucks = harvest_info.adult_bucks + factor;
       pop.adult_does = harvest_info.adult_does + factor;
       pop.fawn_bucks = harvest_info.fawn_bucks + factor;
       pop.fawn_does = harvest_info.fawn_does + factor;
       pop.yearling_bucks = harvest_info.yearling_bucks + factor;
       pop.yearling_does = harvest_info.yearling_does + factor;

       updatePopulationView( pop );

       cur_year = main_panel.current_year;
       this.new_simulation = true;

    }

    /**
     * update the label above the Harvest/Population table.
     * @param county String
     * @param year int
     */
    private void updateLabel( String county, int year )
    {
        county_year_info.setText( MainUIPanel.properName( county) + ", " + Integer.toString( year ) );
    }

    /**
     * update harvest data in Harvest/Population table.
     * @param harvest_info DeerTuple
     */
    private void updateHarvestView( DeerTuple harvest_info )
    {
        TableModel model = harvestTable.getModel();
        model.setValueAt( Double.toString( harvest_info.fawn_does ), 0, 1 );
        model.setValueAt( Double.toString( harvest_info.yearling_does ), 1, 1 );
        model.setValueAt( Double.toString( harvest_info.adult_does ), 2, 1 );
        model.setValueAt( Double.toString( harvest_info.fawn_bucks ), 3, 1 );
        model.setValueAt( Double.toString( harvest_info.yearling_bucks ), 4, 1 );
        model.setValueAt( Double.toString( harvest_info.adult_bucks ), 5, 1 );

        harvestTable.repaint();

    }

    /**
     * gets called by MainUIPanel when user selects year.
     */
    public void onYearSelected( )
    {
        updateHarvestPop();
    }

    /**
     * current year of the simulation.
     */
    private int cur_year = 1981;


    /**
     * Text for the button.
     */
    private String predict_button_text = "Next Year";

    /**
     * label above Harvest/Population table.
     */
    private JLabel county_year_info = new JLabel( "County, Year" );

    /**
     * updates population in the Harvest/Population table.
     * @param population DeerTuple
     */
    private void updatePopulationView( DeerTuple population )
    {

        TableModel model = harvestTable.getModel();
        model.setValueAt( Double.toString( population.fawn_does ), 0, 2 );
        model.setValueAt( Double.toString( population.yearling_does ), 1, 2 );
        model.setValueAt( Double.toString( population.adult_does ), 2, 2 );
        model.setValueAt( Double.toString( population.fawn_bucks ), 3, 2 );
        model.setValueAt( Double.toString( population.yearling_bucks ), 4, 2 );
        model.setValueAt( Double.toString( population.adult_bucks ), 5, 2 );

        harvestTable.repaint();

    }

    /**
     * function to initialize GUI components for this component.
     * @throws Exception
     */
    private void jbInit()throws Exception
    {
        super.setLayout( new BorderLayout() );

        JScrollPane scrollPane = new JScrollPane(popTable);
        JScrollPane scrollPane2 = new JScrollPane(harvestTable);
        //scrollPane.setFillsViewportHeight(false);

        scrollPane.setPreferredSize( new Dimension( 200, 200 ) );

        JPanel button_panel = new JPanel( );
        JButton next_year = new JButton( predict_button_text );
        next_year.addActionListener( this );

        button_panel.add( next_year );

        super.add( scrollPane, BorderLayout.NORTH );
        JPanel centa_panel = new JPanel( new BorderLayout() );
        centa_panel.add( county_year_info, BorderLayout.NORTH );
        centa_panel.add( scrollPane2, BorderLayout.CENTER );
        super.add( centa_panel, BorderLayout.CENTER  );
        super.add( button_panel, BorderLayout.SOUTH );
    }

    /**
     * Read the factors table and return PredictionData object that represents it.
     * @return PredictionData
     */
    private PredictionData getPredictions()
    {
        PredictionData ret = new PredictionData();
        TableModel model = popTable.getModel();

        ret.expansion_factor = Double.parseDouble( (String)model.getValueAt( 0, 1 ));
        ret.winter_rate = Double.parseDouble( (String)model.getValueAt( 1, 1 ));
        ret.winter_rate_bucks = Double.parseDouble( (String)model.getValueAt( 2, 1 ));
        ret.antlered = Double.parseDouble( (String)model.getValueAt( 3, 1 ));
        ret.antlerless = Double.parseDouble( (String)model.getValueAt( 4, 1 ));

        ret.sex_fawn = Double.parseDouble( (String)model.getValueAt( 6, 1 ));
        ret.sex_year = Double.parseDouble( (String)model.getValueAt( 6, 2 ));
        ret.sex_adult = Double.parseDouble( (String)model.getValueAt( 6, 3 ));

        ret.rep_fawn = Double.parseDouble( (String)model.getValueAt( 7, 1 ));
        ret.rep_year = Double.parseDouble( (String)model.getValueAt( 7, 2 ));
        ret.rep_adult = Double.parseDouble( (String)model.getValueAt( 7, 3 ));

        ret.summer_fawn = Double.parseDouble( (String)model.getValueAt( 8, 1 ));
        ret.summer_year = Double.parseDouble( (String)model.getValueAt( 8, 2 ));
        ret.summer_adult = Double.parseDouble( (String)model.getValueAt( 8, 3 ));

        return ret;
    }


    /**
     * reads the Harvest data from Harvest/Population table and returns it as a DeerTuple
     * @return DeerTuple
     */
    public DeerTuple getHarvest()
    {
        DeerTuple ret = new DeerTuple();

        TableModel model = harvestTable.getModel();

        ret.fawn_does = Double.parseDouble( (String)model.getValueAt( 0, 1) );
        ret.yearling_does = Double.parseDouble( (String)model.getValueAt( 1, 1) );
        ret.adult_does = Double.parseDouble( (String)model.getValueAt( 2, 1) );
        ret.fawn_bucks = Double.parseDouble( (String)model.getValueAt( 3, 1) );
        ret.yearling_bucks = Double.parseDouble( (String)model.getValueAt( 4, 1) );
        ret.adult_bucks = Double.parseDouble( (String)model.getValueAt( 5, 1) );

        return ret;
    }

    /**
     * read population data from Harvest/Population table and retrun it as a DeerTuple
     * @return DeerTuple
     */
    public DeerTuple getPopulation()
    {
        DeerTuple ret = new DeerTuple();

        TableModel model = harvestTable.getModel();

        ret.fawn_does = Double.parseDouble( (String)model.getValueAt( 0, 2) );
        ret.yearling_does = Double.parseDouble( (String)model.getValueAt( 1, 2) );
        ret.adult_does = Double.parseDouble( (String)model.getValueAt( 2, 2) );
        ret.fawn_bucks = Double.parseDouble( (String)model.getValueAt( 3, 2) );
        ret.yearling_bucks = Double.parseDouble( (String)model.getValueAt( 4, 2) );
        ret.adult_bucks = Double.parseDouble( (String)model.getValueAt( 5, 2) );

        return ret;

    }

    /**
     * true if this is a new simulation.  false if not.
     */
    private boolean new_simulation = true;

    /**
     * when the button gets pressed this function gets called.
     * @param e ActionEvent
     */
    public void actionPerformed( ActionEvent e )
    {
        try
        {
            System.out.println("Trying to predict stuff.......");

            PredictionData pred = getPredictions();
            DeerTuple harvest = getHarvest();
            DeerTuple population = getPopulation();



            DeerTuple next_year;
            if( !new_simulation ) next_year = DeerTuple.Next_Year(population, harvest, pred);
            else next_year = DeerTuple.Default( population, harvest, pred );
            System.out.println(next_year.toString());

            new_simulation = false;
            cur_year ++;

            if( main_panel.getDB().getMaxYear() >= cur_year )
            {
                updateHarvestView( main_panel.getDB().counties.get( main_panel.getCurrentCounty() ).getYear( cur_year ) );
            }
            this.updatePopulationView( next_year );
            updateLabel( main_panel.getCurrentCounty(), cur_year );
            next_year.county = main_panel.getCurrentCounty();
            next_year.multiply( .3 );
            main_panel.rendera.updateDeerTuple( next_year );

        }
        catch( Exception ex )
        {
            System.out.println( "Whoops something went wrooooong........ :((((((((((" );
            ex.printStackTrace();
        }
    }

}
