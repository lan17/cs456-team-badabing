package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * <p>Helper class with some helper functions.</p>
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
public class MyFileFilter
        extends FileFilter
{
    /**
    * return extension from file name represented by str.
    * @param str String
    * @return String
    */
    public static String getExtension( final String str )
    {
        int i = str.length( ) - 1;
        while( i >= 0 && str.charAt( i ) != '.' ) i--;
        String extension = str.substring( i + 1 );
        return extension;
    }

    /**
     * return file name represented by str without extension.
     * @param str String
     * @return String
     */
    public static String stripExtension( final String str )
    {
        int i = str.length( ) - 1;
        while( i >= 0 && str.charAt( i ) != '.' ) i--;
        String stripped = str.substring( 0, i );
        return stripped;

    }

    public MyFileFilter()
    {
        super();
    }

    /**
     * Whether the given file is accepted by this filter.
     *
     * @param f File
     * @return boolean
     * @todo Implement this javax.swing.filechooser.FileFilter method
     */
    public boolean accept(File f)
    {
        return false;
    }

    /**
     * The description of this filter.
     *
     * @return String
     * @todo Implement this javax.swing.filechooser.FileFilter method
     */
    public String getDescription()
    {
        return "";
    }
}
