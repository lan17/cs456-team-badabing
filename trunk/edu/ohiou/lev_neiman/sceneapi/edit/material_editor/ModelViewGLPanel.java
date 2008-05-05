package edu.ohiou.lev_neiman.sceneapi.edit.material_editor;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.awt.*;
import java.awt.event.*;
import edu.ohiou.lev_neiman.sceneapi.*;
import edu.ohiou.lev_neiman.sceneapi.basic.*;
import java.lang.Math.*;

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

    public double left = -2.0f;
    public double right = 2.0f;
    public double top = 2.0f;
    public double bottom = -2.0f;
    public double near = 1.0f;
    public double far = 100f;

    private ModelNode model = null;
    private LightNode light = new LightNode( GL.GL_LIGHT0 );

    public ModelViewGLPanel()
    {
        gl_panel.setPreferredSize( new Dimension( 640, 480 ) );
        gl_panel.addGLEventListener( this );
        super.add( gl_panel );
    }

    /*
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        gl_panel.display();
    }
*/

    public void loadModel( ModelNode model )
    {
        this.model = model;
        //setUpProjection( gl_panel );
        display();
    }

    public void displayChanged( GLAutoDrawable panel, boolean _boolean, boolean _boolean2 )
    {

     }

     public void display()
     {
         gl_panel.display();
     }

    public void init( GLAutoDrawable panel )
    {
        GL gl = panel.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint( GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST );
    }

    public void display( GLAutoDrawable panel )
    {
        setUpProjection( panel );
        GL gl = panel.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslated( 0, 0, Math.abs( far-near ) / 2f * -1.5f  );

        light.render( panel );
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

    private void setUpProjection( GLAutoDrawable panel )
    {
        final GL gl = panel.getGL();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        //glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        System.out.println( Double.toString( left ) + ", " + Double.toString( right ) + ", " + Double.toString( bottom ) + ", " + Double.toString( top ) + ", " + Double.toString( near) + ", " + Double.toString( far ) );
        gl.glFrustum( left, right, bottom, top, -1f*far, far * 100);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    public void reshape(GLAutoDrawable panel, int x, int y, int width, int height)
    {
        setUpProjection( panel );
        display( panel );
    }
}
