package edu.ohiou.cs456_badabing.sceneapi.visualize;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.math.*;
import javax.media.opengl.*;
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
public class Sphere
        extends ANode
{
    public float radius = .5f;
    public int detail = 100;
    public Color color = Color.RED;
    public static final double PI = 3.1415;

    public Sphere()
    {

    }

    public void render( GLAutoDrawable panel )
    {
        GL gl = panel.getGL();

        gl.glPointParameterf( gl.GL_POINT_FADE_THRESHOLD_SIZE, 5 );
        gl.glColor3d( color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        for( double i = 0f; i < 2f*PI; i += PI/(double)detail )
        {
            for( double j = 0f; j < PI; j += PI/(double)detail )
            {
                Coordinate point = new Coordinate( Math.cos( i ) * Math.sin( j ) * radius, Math.sin( i ) * Math.sin( j ) * radius, Math.cos( j ) * radius );
                gl.glBegin( GL.GL_POINT);
                gl.glVertex3d( point.x(), point.y(), point.z() );
                gl.glEnd();
            }
        }

    }
}
