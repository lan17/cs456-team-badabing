package edu.ohiou.cs456_badabing.sceneapi;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;

import java.awt.Frame;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import edu.ohiou.cs456_badabing.sceneapi.lol.*;

class JavaRenderer
 implements GLEventListener, KeyListener
{
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU( );
    HyperCube hyper_cube;

    ANode scene;

    public void display(GLAutoDrawable gLDrawable)
    {
        // set evreything up for drawing.
        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); // clear background to default background color ( black ).
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT); // clear Z-Buffer (depth buffer).
        gl.glLoadIdentity(); // load identity matrix into ModelView matrix.

        // move everything -5 in z directoin, so it will become visible in the viewing frustrum.
        gl.glTranslatef(0.0f, 0, -5.0f);


        // next 4 lines rotate the whole scene.
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

        rotateT += .2f;

        gl.glColor3f( 1, 0, 0 );
        ANode.renderTree( gLDrawable, hyper_cube );
    }

    public void displayChanged(GLAutoDrawable gLDrawable,
      boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable gLDrawable) {
        System.out.println( "INITIALIZING!" );
        final GL gl = gLDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);  // set background color to black.
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);
        gLDrawable.addKeyListener(this);

        // next 4 lines create a display list which draws a cube.
        int tmp = gl.glGenLists( 1 );
        gl.glNewList( tmp, gl.GL_COMPILE );
        ANode.drawCube( gLDrawable );
        gl.glEndList();
        DisplayListManager.addList( gLDrawable, "cube", tmp );


        /*
        //lightning
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glEnable(gl.GL_NORMALIZE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);


        float light_ambient[]  = { 1.0f, 1.0f, 1.0f, 0.0f };
        float light_diffuse[]  = { 1.0f, 1.0f, 1.0f, 1.0f };
        float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        float light_position[] = { 2.0f, 5.0f, 5.0f, 0.0f };


        gl.glLightfv(gl.GL_LIGHT0, gl.GL_AMBIENT,  light_ambient, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_DIFFUSE,  light_diffuse, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_SPECULAR, light_specular, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, light_position, 0);
        */

        scene = new LightNode( GL.GL_LIGHT0 );





        hyper_cube = new HyperCube(gLDrawable);
        hyper_cube.scale().set( 2, 2, 2 );
        System.out.println( ANode.countNodes( hyper_cube ) );

        //ModelFile tmpfile = ModelFileManager.getFile( System.getProperty( "user.dir" ) + "\\data\\hornball.obj" );
        //object = new ModelNode( tmpfile );
        //scene.addChild( object );
        //ModelNode object2 = new ModelNode( tmpfile );
        //object.scale.set( new Coordinate( .1, .1, .1 ) );
        //object.translation.set( new Coordinate( 0, -2, 0 ) );
        //object.addChild( object2 );
        //object2.translation.set( -20, 0, 0 );
    }

    /**
     * gets called every time window is reshaped.  Also gets called one time after init.
     * Job of this function is to set up projection matrix correctly.
     * @param gLDrawable GLAutoDrawable
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void reshape(GLAutoDrawable gLDrawable, int x,
    int y, int width, int height) {
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
            JavaDia.bQuit = true;
            JavaDia.displayT = null;
            System.exit(0);
        }
        if( e.getKeyCode() == KeyEvent.VK_Z )
        {
            hyper_cube.resetDetail( hyper_cube.getDetail() - 1 );
        }
        if( e.getKeyCode() == KeyEvent.VK_X )
        {
            hyper_cube.resetDetail( hyper_cube.getDetail() + 1 );
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
 }


 public class JavaDia implements Runnable {
     static Thread displayT = new Thread(new JavaDia());
     static boolean bQuit = false;

     public static void main(String[] args) {
         //System.load( System.getProperty( "user.dir" ) + "/jogl.dll" );
         //System.loadLibrary( "jogl" );
         displayT.start();

     }

     public void run() {
         try
         {
             System.out.println( "Starting...." );

             Frame frame = new Frame("Lev Neiman - ln180704@ohio.edu.");
             GLJPanel canvas = new GLJPanel();
             canvas.addGLEventListener(new JavaRenderer());
             frame.add(canvas);
             frame.setSize(640, 480);
             //frame.setUndecorated(true);
             int size = frame.getExtendedState();
             size |= Frame.MAXIMIZED_BOTH;
             //frame.setExtendedState(size);

             frame.addWindowListener(new WindowAdapter()
             {
                 public void windowClosing(WindowEvent e)
                 {
                     bQuit = true;
                 }
             });
             frame.setVisible(true);
             //      frame.show();
             canvas.requestFocus();
             while (!bQuit)
             {
                 canvas.display();
             }
             System.exit( 0 );
         }
         catch( Exception e )
         {
             e.printStackTrace();
         }
     }
 }
