package edu.ohiou.cs456_badabing.sceneapi.basic;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.*;

import edu.ohiou.cs456_badabing.sceneapi.*;


/**
 * <p>Container of a hasmap of texture names associated with JOGL's Texture objects.</p>
 *
 * <p>Description: Container of a hasmap of texture names associated with JOGL's Texture objects.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class TextureManager
{
    public static Hashtable< String, com.sun.opengl.util.texture.Texture > textures = new Hashtable<String,com.sun.opengl.util.
                   texture.Texture>();

   public static void clear ()
   {
       for( Object t : (textures.values().toArray() ) )
       {
           t = null;
       }
        textures = null;
        textures = new Hashtable<String,com.sun.opengl.util.texture.Texture>();

   }

   public static com.sun.opengl.util.texture.Texture getTexture( String name )
   {
       com.sun.opengl.util.texture.Texture ret = null;
       ret = textures.get( name );
       if( ret != null ) { return ret; } // if the texture already exists, return its instance, otherwise create a new one.\
       String file_name = name;
       File file = new File( file_name );
       //System.out.println( "Does this file exist? " + file.toString() +  " - " + file.exists() );
       BufferedImage image;
       try
       {
           ret = TextureIO.newTexture( file, true );
       }
       catch( Exception e )
       {
           System.out.println( "Error reading " + file.getAbsolutePath() );
           e.printStackTrace();
           return null;
       }


       ret.setTexParameteri( GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );
       ret.setTexParameteri( GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
       ret.setTexParameteri( GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR );
       ret.setTexParameteri( GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR );
       textures.put( name, ret );
       //ret.enable();
       return ret;
   }
}
