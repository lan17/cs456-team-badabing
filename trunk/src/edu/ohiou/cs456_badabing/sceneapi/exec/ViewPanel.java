package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.nio.*;
import javax.media.opengl.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import edu.ohiou.cs456_badabing.sceneapi.basic.ANode;
import edu.ohiou.cs456_badabing.sceneapi.visualize.Map;

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
public class ViewPanel extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener
{
    private MainUIPanel main_panel = null;

    public ViewPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;

        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void display(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();


    }

    public void displayChanged(GLAutoDrawable gLDrawable,
      boolean modeChanged, boolean deviceChanged) {
    }

    /**
     * Set up our scene.
     * @param gLDrawable GLAutoDrawable
     */
    public void init(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
        //gl.glClearColor( 0, 0, 0, 0 );
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);

    }

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height)
    {
        GL gl = getGL();


    }

    private void jbInit()
            throws Exception
    {
        addGLEventListener( this );
        addMouseListener( this );
        addMouseMotionListener( this );
    }

    public void mouseExited( MouseEvent e )
    {

    }

    public void mouseEntered( MouseEvent e )
    {

    }

    public void mouseReleased( MouseEvent e )
    {

    }

    public void mousePressed( MouseEvent e )
    {

    }

    public void mouseClicked( MouseEvent e )
    {

    }

    public void mouseMoved( MouseEvent e )
    {
        //System.out.println( Integer.toString( e.getX() ) );

        GL gl = getGL();
        float lol[] = { 1, 1,1 };
        java.nio.FloatBuffer pixels = FloatBuffer.allocate( 3 );
        gl.glReadPixels( e.getX(), e.getY(), 1, 1, GL.GL_RGB, GL.GL_FLOAT, pixels );
        lol = pixels.array();

        for( int i  = 0; i < 3; i++ )
        {
            System.out.print(pixels.get( i ));

        }
        System.out.println( "LOL");
        //gl.glRasterPos4fv();
    }

    public void mouseDragged( MouseEvent e )
    {

    }

}
