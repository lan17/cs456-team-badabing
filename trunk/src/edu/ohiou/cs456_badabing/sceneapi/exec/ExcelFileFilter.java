package edu.ohiou.cs456_badabing.sceneapi.exec;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

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
public class ExcelFileFilter extends FileFilter
{
    public boolean accept(java.io.File f)
    {
        if( f.isDirectory() ) return true;

        String p = f.getAbsolutePath();
        int i = p.length( ) - 1;
        while( i >= 0 && p.charAt( i ) != '.' ) i--;
        String extension = p.substring( i + 1 );
        //System.out.println( extension );
        if( extension.equals(  "xls" ) ) return true;
        return false;
    }

    public String getDescription( )
    {
        return "Micro$oft Excel Files";
    }

}
