package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

import java.util.ArrayList;

/**
 * <p>Container for DeerTuples for some county</p>
 *
 * <p>Description: Contains DeerTuple(s) that belong to some county. </p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class CountyInfo
{
    /**
    * name of the county.
    */
    public String name;

    /**
     * array list of deer tuples for this county.
     */
    public ArrayList< DeerTuple > info = new ArrayList<DeerTuple>();

    /**
     * return DeerTuple for specific year.  if it can't be found return null.
     * @param year int
     * @return DeerTuple
     */
    public DeerTuple getYear( int year )
    {
        for( DeerTuple t : info )
        {
            if( t.year == year )
            {
                return t;
            }
        }
        return null;
    }
}
