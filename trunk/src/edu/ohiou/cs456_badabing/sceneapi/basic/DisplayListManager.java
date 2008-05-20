package edu.ohiou.cs456_badabing.sceneapi.basic;

import java.util.*;

/**
 * <p>Simple class to contain a TreeMap between string names of a display list and its OpenGL integer value.</p>
 *
 * <p>Description: Simple class to contain a TreeMap between string names of a display list and its OpenGL integer value.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class DisplayListManager
{
    /**
    *  map display list string names to their corresponding OpenGL display list integers.
    */
    public static TreeMap< String, Integer > name_map = new TreeMap<String,Integer>( );

    public DisplayListManager()
    {
    }

    /**
     * Add new list to the manager.
     * @param name String - name of the new list.
     * @param list Integer - corresponding OpenGL display list integer.
     */
    public static void addList( String name, Integer list )
    {
        if( ! name_map.containsKey( name ) )
        {
            name_map.put( name, list );
        }
    }

    /**
     * get OpenGL display list integer value that is mapped to 'name'
     * @param name String - display list's name
     * @return Integer - display list OpenGL value.
     */
    public static Integer getList( String name )
    {
        return name_map.get( name );
    }
}
