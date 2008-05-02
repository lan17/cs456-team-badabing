package edu.ohiou.lev_neiman.sceneapi;

import edu.ohiou.lev_neiman.sceneapi.basic.*;

import java.awt.Frame;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import edu.ohiou.lev_neiman.sceneapi.basic.*;
import edu.ohiou.lev_neiman.sceneapi.lol.*;

class JavaRenderer
 implements GLEventListener, KeyListener
{
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU( );
    HyperCube hyper_cube;
    ModelNode object;

    public void display(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0, -5.0f);


        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);


        //gl.glRotatef( rotateT, 0, 1, 0 );
        //gl.glTranslatef( 0, -2, 0 );

        rotateT += 0.2f;

        gl.glColor3f( 1, 0, 0 );
        ANode.renderTree( gLDrawable, object );
        //ANode.renderTree( gLDrawable, hyper_cube );
 //       hyper_cube.translation.set( new Coordinate( 1.0, 1.0, 1.0 ) );
    }

    public void displayChanged(GLAutoDrawable gLDrawable,
      boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL gl = gLDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);
        gLDrawable.addKeyListener(this);

        int tmp = gl.glGenLists( 1 );
        gl.glNewList( tmp, gl.GL_COMPILE );
        ANode.drawCube( gLDrawable );
        gl.glEndList();
        DisplayListManager.addList( "cube", tmp );


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





        hyper_cube = new HyperCube(gLDrawable);
        hyper_cube.scale.set( 2, 2, 2 );
        System.out.println( ANode.countNodes( hyper_cube ) );

        ModelFile tmpfile = ModelFileManager.getFile( System.getProperty( "user.dir" ) + "\\data\\globe.obj" );
        object = new ModelNode( tmpfile );
        //ModelNode object2 = new ModelNode( tmpfile );
        object.scale.set( new Coordinate( .1, .1, .1 ) );
        object.translation.set( new Coordinate( 0, -2, 0 ) );
        //object.addChild( object2 );
        //object2.translation.set( -20, 0, 0 );
    }

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
         System.load( System.getProperty( "user.dir" ) + "/jogl.dll" );
         displayT.start();

     }

     public void run() {
         try
         {
             System.out.println( "Starting...." );

             Frame frame = new Frame("Lev Neiman - ln180704@ohio.edu.");
             GLCanvas canvas = new GLCanvas();
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
         }
         catch( Exception e )
         {
             e.printStackTrace();
         }
     }
 }
