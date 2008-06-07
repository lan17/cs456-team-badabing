package edu.ohiou.cs456_badabing.sceneapi.visualize;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;

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
    private float transperency = 1f;
    private float radius = 1f;
    private int detail = 100;
    private static Material mtl = new Material();

    public static boolean need_to_generate_display_list = true;

    private static int g_disp_list = -1;


    public Sphere()
    {
        //mtl.color[3] = .5f;
    }

    /**
     * set new transpereny for all Spheres.  this causes display list to be re-generated.
     * @param new_transperency float
     */
    public static void setTransperency( float new_transperency )
    {
        mtl.color[3] = new_transperency;
        need_to_generate_display_list = true;
    }

    public void setRadius( float new_radius )
    {
        radius = new_radius;
        super.scale.set( (double) radius, (double) radius, (double) radius );
        //need_to_generate_display_list = true;
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
        if( g_disp_list != display_list )
        {
            display_list = g_disp_list;
        }
        if( need_to_generate_display_list )
        {
            int tmp = gl.glGenLists( 1 );
            gl.glNewList( tmp, gl.GL_COMPILE );
            {
                mtl.apply( panel );
                GLU glu = new GLU();
                GLUquadric quadric = glu.gluNewQuadric();
                glu.gluSphere( quadric, 1.0f, detail, detail );
            }
            gl.glEndList();
            DisplayListManager.addList( panel, "sphere", tmp );
            need_to_generate_display_list = false;
            g_disp_list = tmp;
        }
        if( display_list == no_disp_list )
        {
            Integer q = DisplayListManager.getList( "sphere" );
            if( q == null )
            {
                need_to_generate_display_list = true;
            }
            else
            {
                display_list = q.intValue();
            }
        }
        if( show )
        {
            super.render(panel);
        }
    }
}
