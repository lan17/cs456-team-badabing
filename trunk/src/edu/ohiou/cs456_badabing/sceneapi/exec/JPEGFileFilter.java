package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.io.File;

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
public class JPEGFileFilter extends MyFileFilter
{
    public JPEGFileFilter()
    {
        super();
    }

    /**
     * Whether the given file is accepted by this filter.
     *
     * @param f File
     * @return boolean
     *
     */
    public boolean accept(File f)
    {
        if( f.isDirectory() ) return true;
        String p = f.getAbsolutePath();
        String extension = super.getExtension( p );
        if( extension.equals(  "jpg" ) || extension.equals( "jpeg" ) ) return true;
        return false;

    }

    /**
     * The description of this filter.
     *
     * @return String
     *
     */
    public String getDescription()
    {
        return "JPEG";
    }
}
