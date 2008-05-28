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

    public static boolean need_to_generate_display_list = true;


    public Sphere()
    {
        //mtl.color[3] = .5f;
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
            DisplayListManager.addList( "sphere", tmp );
            need_to_generate_display_list = false;
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
