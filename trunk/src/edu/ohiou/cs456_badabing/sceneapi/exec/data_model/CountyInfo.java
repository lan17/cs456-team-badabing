package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

import java.util.*;
import edu.ohiou.cs456_badabing.sceneapi.exec.*;

/**
 * <p>Container for DeerTuples for some county</p>
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

    public CountyInfo()
    {
    }
}
