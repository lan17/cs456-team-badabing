package edu.ohiou.cs456_badabing.sceneapi.exec;



/**
 * <p>File Filter for xls extension.</p>
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
public class ExcelFileFilter extends MyFileFilter
{
    public boolean accept(java.io.File f)
    {
        if( f.isDirectory() ) return true;


        String extension = super.getExtension( f.getAbsolutePath() );
        //System.out.println( extension );
        if( extension.equals(  "xls" ) ) return true;
        return false;
    }

    public String getDescription( )
    {
        return "Micro$oft Excel Files";
    }

}
