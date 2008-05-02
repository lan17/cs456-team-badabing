package edu.ohiou.lev_neiman.sceneapi.edit;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.awt.*;
import java.awt.event.*;
import edu.ohiou.lev_neiman.sceneapi.*;
import edu.ohiou.lev_neiman.sceneapi.basic.*;

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
public class ModelViewGLPanel extends JPanel implements GLEventListener
{
    private GLU glu = new GLU( );
    private GLJPanel gl_panel = new GLJPanel( );

    private double left = -2.0f;
    private double right = 2.0f;
    private double top = 2.0f;
    private double bottom = -2.0f;
    private double near = 1.0f;
    private double far = 100f;




    private ModelNode model = null;

    public ModelViewGLPanel()
    {
        gl_panel.addGLEventListener( this );

    }

    public void loadModel( ModelNode model )
    {
        this.model = model;
    }

    public void displayChanged( GLAutoDrawable panel, boolean _boolean, boolean _boolean2 )
    {

     }

    public void init( GLAutoDrawable panel )
    {


    }

    public void display( GLAutoDrawable panel )
    {
        GL gl = panel.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        if( model != null ) ANode.renderTree( panel, model );

    }
/*
        void glFrustum( GLdouble left,
                              GLdouble right,
                              GLdouble bottom,
                              GLdouble top,
                              GLdouble zNear,
                              GLdouble zFar	)
*/

    public void reshape(GLAutoDrawable panel, int x, int y, int width, int height)
    {
        final GL gl = panel.getGL();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        //glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glFrustum( left, right, bottom, top, near, far );
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();


    }
}
