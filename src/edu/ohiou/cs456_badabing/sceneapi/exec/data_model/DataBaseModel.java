package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import jxl.*;

/**
 * <p>Contains Harvest data that is read from Excel file.</p>
 *
 * <p>Description: Contains harvest information on all counties and years that are loaded from Excel file.</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class DataBaseModel
{
    /**
    * All the tuples contained in an array.
    */
    public ArrayList< DeerTuple > tuples = new ArrayList<DeerTuple>();

    /**
     * map of county name as string to CountyInfo object for that county.
     */
    public TreeMap< String, CountyInfo > counties = new TreeMap<String,CountyInfo>();


    /**
     * what is the biggest year ( most recent ).
     */
    private int max_year = -1;

    /**
     * what is the smallest year ( most old ).
     */
    private int min_year = Integer.MAX_VALUE;

    /**
     * what is the biggest single harvest from all years, and counties?
     */
    private double max_harvest = Double.MAX_VALUE * -1.0;

    /**
     * what is the smallest single harvest from all years and counties?
     */
    private double min_harvest = Double.MAX_VALUE;

    /**
     * Get max harvest out of all years and counties.
     * @return double
     */
    public double getMaxHarvest()
    {
        return max_harvest;
    }

    /**
     * get min harvest out of all years and counties.
     * @return double
     */
    public double getMinHarvest()
    {
        return min_harvest;

    }

    /**
     * what is the most recent year.
     * @return int
     */
    public int getMaxYear()
    {
        return max_year;
    }

    /**
     * what is the oldest year.
     * @return int
     */
    public int getMinYear()
    {
        return min_year;
    }

    /**
     * add a new Deer tuple to the database.
     * @param new_tuple DeerTuple
     */
    public void addTuple( DeerTuple new_tuple )
    {
        if( max_year < new_tuple.year ) max_year = new_tuple.year;
        if( min_year > new_tuple.year ) min_year = new_tuple.year;
        double sum = new_tuple.sum();
        if( max_harvest < sum ) max_harvest = sum;
        if( min_harvest > sum ) min_harvest = sum;

        String county_name = new_tuple.county;
        tuples.add( new_tuple );
        if( counties.get( county_name ) == null )
        {
            counties.put( county_name, new CountyInfo() );
            counties.get( county_name).name = county_name;
        }
        counties.get( county_name ).info.add( new_tuple );
    }

    /**
     * out put entire contents of this database into a string.
     * @return String
     */
    public String toString()
    {
        String ret = "";
        for( DeerTuple t : tuples )
        {
            ret += t.toString() + "\n";
        }

        return ret;
    }

    //public void readPopulationDefaults

    /**
     * This function reads in Excel file into memory and returns a new instance of DataBaseModel
     * @param file_name String - absolute path to the file.
     * @return DataBaseModel
     * @throws Exception
     */
    public static DataBaseModel readHarvestDataFile( String file_name ) throws Exception
    {
        DataBaseModel ret = new DataBaseModel();
        try
        {
             Workbook workbook = Workbook.getWorkbook(new File( file_name ));
             Sheet sheet = workbook.getSheet(0);
             for( int i = 1; i < sheet.getRows(); ++i )
             {
                 DeerTuple tuple = new DeerTuple();
                 Cell row[] = sheet.getRow( i );
                 tuple.year = Integer.parseInt( row[0].getContents() );
                 tuple.county = row[1].getContents().toLowerCase();
                 tuple.fawn_does = Double.parseDouble( row[2].getContents() );
                 tuple.yearling_does = Double.parseDouble( row[3].getContents() );
                 tuple.adult_does = Double.parseDouble( row[4].getContents() );
                 tuple.fawn_bucks = Double.parseDouble( row[5].getContents() );
                 tuple.yearling_bucks = Double.parseDouble( row[6].getContents() );
                 tuple.adult_bucks = Double.parseDouble( row[7].getContents() );

                 ret.addTuple( tuple );
             }
        }
        catch( Exception e )
        {
            throw e;
        }

        return ret;
    }

    /**
     * applies population expansion factors.
     * refer to slide 11 in Overview presentation file.
     * @param input DeerTuple
     * @param parameters PredictionData
     * @return DeerTuple
     */
    public static DeerTuple preHuntPopulation( DeerTuple input, PredictionData parameters )
    {
        return null;
    }

    /**
     * applies harvest data to compute post hunt population
     * @param input DeerTuple
     * @param parameters PredictionData
     * @return DeerTuple
     */
    public static DeerTuple postHuntPopulation( DeerTuple pre_hunt_info, PredictionData parameters )
    {
        return null;
    }

    /**
     * applies winter mortality rates from post hunt population to arrive at pre birth population.
     * @param post_hunt_population DeerTuple
     * @param parameters PredictionData
     * @return DeerTuple
     */
    public static DeerTuple preBirthPopulation( DeerTuple post_hunt_population, PredictionData parameters )
    {
        return null;
    }

    /**
     * apply reproduction information from pre birth population to compute post birth population.
     * @param pre_birth_population DeerTuple
     * @param parameters PredictionData
     * @return DeerTuple
     */
    public static DeerTuple postBirthPopulation( DeerTuple pre_birth_population, PredictionData parameters )
    {
        return null;
    }

    /**
     * apply summer mortality rate from post birth population to arrive at new year's pre hunt population.
     * @param post_birth_population DeerTuple
     * @param parameters PredictionData
     * @return DeerTuple
     */
    public static DeerTuple newYearPreHuntPopulation( DeerTuple post_birth_population, PredictionData parameters )
    {
        return null;
    }


    /**
     * return a new deer tuple that is the prediction based on input, given parameters scenario.
     * @param input DeerTuple
     * @param parameters PredictionData
     * @return DeerTuple
     */
    public static DeerTuple predict( DeerTuple input, PredictionData parameters )
    {
        return null;
    }


}
