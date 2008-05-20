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
    public int detail = 20;
    public Material mtl = new Material();

    boolean first_run = true;

    public Sphere()
    {

    }

    public void changeRadius( float new_radius )
    {
        radius = new_radius;
    }

    public float getRadius( )
    {
        return radius;
    }

    public void render( GLAutoDrawable panel )
    {
        GL gl = panel.getGL();
        if( first_run )
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
            first_run = false;
        }
        if( show )
        {
            super.render(panel);
        }
    }
}
