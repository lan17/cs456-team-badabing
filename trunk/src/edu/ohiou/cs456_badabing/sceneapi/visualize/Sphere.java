package edu.ohiou.cs456_badabing.sceneapi.visualize;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.math.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
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
public class Sphere extends ANode
{
    private float radius = 1f;
    private int detail = 20;
    private Material mtl = new Material();

    private boolean need_to_generate_display_list = true;

    public Sphere()
    {

    }

    public void changeRadius( float new_radius )
    {
        radius = new_radius;
    }

    public void setRadius( float new_radius )
    {
        radius = new_radius;
        need_to_generate_display_list = true;
    }

    public synchronized float getRadius( )
    {
        return radius;
    }

    /**
     * generates display list if needed.
     * Remember that ANode can render display lists, which is what it does after the display list has been generated.
     * @param panel GLAutoDrawable
     */
    public void render( GLAutoDrawable panel )
    {
        GL gl = panel.getGL();
        if( need_to_generate_display_list )
        {
            int tmp = gl.glGenLists( 1 );
            gl.glNewList( tmp, gl.GL_COMPILE );
            {
                mtl.apply( panel );
                GLU glu = new GLU();
                GLUquadric quadric = glu.gluNewQuadric();
                glu.gluSphere( quadric, radius, detail, detail );
            }
            gl.glEndList();
            super.display_list = tmp;
            need_to_generate_display_list = false;
        }
        if( show )
        {
            super.render(panel);
        }
    }
}
