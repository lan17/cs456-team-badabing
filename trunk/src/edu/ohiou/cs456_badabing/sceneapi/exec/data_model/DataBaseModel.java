package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

import java.util.*;
import edu.ohiou.cs456_badabing.sceneapi.exec.*;
import java.io.File;
import java.util.Date;
import jxl.*;


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
public class DataBaseModel
{
    public ArrayList< DeerTuple > tuples = new ArrayList<DeerTuple>();
    public TreeMap< String, CountyInfo > counties = new TreeMap<String,CountyInfo>();


    public int max_year = -1;

    /**
     * add a new Deer tuple to the database.
     * @param new_tuple DeerTuple
     */
    public void addTuple( DeerTuple new_tuple )
    {
        if( max_year < new_tuple.year ) max_year = new_tuple.year;

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

    /**
     * This function reads in Excel file into memory and returns a new instance of DataBaseModel
     * @param file_name String - absolute path to the file.
     * @return DataBaseModel
     * @throws Exception
     */
    public static DataBaseModel readFile( String file_name ) throws Exception
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
     * use parameters to predict for ALL deer tuples in this data base.
     * @param parameters PredictionData
     */
    public void predict( PredictionData parameters )
    {

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
