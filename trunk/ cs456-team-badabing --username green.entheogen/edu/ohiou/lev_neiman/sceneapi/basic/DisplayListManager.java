package edu.ohiou.lev_neiman.sceneapi.basic;

/**
 * <p>Title: JOGL stuff</p>
 *
 * <p>Description: Experimentation with JOGL</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */

import java.util.*;


public class DisplayListManager
{
    public static TreeMap< String, Integer > name_map = new TreeMap<String,Integer>( );

    public DisplayListManager()
    {
    }

    public static void addList( String name, Integer list )
    {
        if( ! name_map.containsKey( name ) )
        {
            name_map.put( name, list );
        }
    }
    public static Integer getList( String name )
    {
        return name_map.get( name );
    }
}
