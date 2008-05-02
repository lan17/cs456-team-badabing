package edu.ohiou.lev_neiman.sceneapi.basic;

import java.util.*;
import javax.media.opengl.*;
import com.sun.opengl.util.texture.*;

/**
 * <p>Title: class Material</p>
 *
 * <p>Description: container class for material properties.</p>
 *
 * <p>Copyright: Copyright (c) 2007, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class Material
{
        /**
         * JOGL Texture object.  if it is equal to null then there is no texture.
         */
        public Texture texture = null;

        /**
         * unique name for this material.
         */
        public String name;

        /**
         * RGBA color
         */
        float color    [] = new float[4];
        /**
         * ambience
         */
        float ambient  [] = new float[4];
        /**
         * diffuse
         */
        float diffuse  [] = new float[4];
        /**
         * specular
         */
        float specular [] = new float[4];
        /**
         * shininess
         */
        float shininess[] = new float[1];

        /**
         * default constructor
         * @param name String
         */
        public Material( String name)
        {
            this.name = new String( name );
        }

        /**
         * this method applies this material to the GL instance.
         * @param drawable GLAutoDrawable
         */
        public void apply( GLAutoDrawable drawable )
        {
            GL gl = drawable.getGL();
            gl.glColor4fv( color, 0 );
            gl.glMaterialfv( gl.GL_FRONT, gl.GL_AMBIENT,   ambient, 0 );
            gl.glMaterialfv( gl.GL_FRONT, gl.GL_DIFFUSE,   diffuse, 0 );
            gl.glMaterialfv( gl.GL_FRONT, gl.GL_SPECULAR,  specular, 0 );
            gl.glMaterialfv( gl.GL_FRONT, gl.GL_SHININESS, shininess, 0 );
            if( texture != null ) { texture.bind( ); }

        }
}
