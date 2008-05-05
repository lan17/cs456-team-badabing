package edu.ohiou.lev_neiman.sceneapi;

import java.awt.Frame;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import edu.ohiou.lev_neiman.sceneapi.basic.*;
import edu.ohiou.lev_neiman.sceneapi.visualize.*;

  class CS456ExampleRenderer
 implements GLEventListener, KeyListener
{
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU();

    private LightNode light = new LightNode( GL.GL_LIGHT0 );
    private PieChart chart = new PieChart( );


    public void display(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -2.0f); // camera looks into -z direction, so lets move the cube in front of the camera, otherwise it would be too close to it.

        /*
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        */

        ANode.renderTree( gLDrawable, light);
        //drawCube( gLDrawable );

        rotateT += 0.2f;
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
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);
        gLDrawable.addKeyListener(this);

        light.addChild( chart );
    }

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height)
    {
        final GL gl = gLDrawable.getGL();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            CS456Example.bQuit = true;
            CS456Example.displayT = null;
            System.exit(0);
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
 }


 public class CS456Example implements Runnable {
     static Thread displayT = new Thread(new CS456Example());
     static boolean bQuit = false;

     public static void main(String[] args)
     {
         System.load( System.getProperty( "user.dir" ) + "/jogl.dll" );
         displayT.start();
     }

     public void run()
     {
         try
         {
             System.out.println( "Starting...." );
             Frame frame = new Frame("CS456 JOGL Example.  Press ESC to exit.");
             GLCanvas canvas = new GLCanvas();
             canvas.addGLEventListener(new CS456ExampleRenderer());
             frame.add(canvas);
             frame.setSize(640, 480);

             frame.setVisible(true);
             canvas.requestFocus();
             while (!bQuit)
             {
                 canvas.display();
             }
         }
         catch( Exception e )
         {
             e.printStackTrace();
         }
     }
 }
