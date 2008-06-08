package edu.ohiou.cs456_badabing.sceneapi;




import java.awt.Frame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import edu.ohiou.cs456_badabing.sceneapi.visualize.*;
import javax.swing.event.ChangeEvent;
/**
 *
 * <p>GLEventListener for HW6A</p>
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
  class HW6ARenderer
 implements GLEventListener, KeyListener, MouseListener, MouseMotionListener
{
    private static final GLU glu = new GLU();

    private LightNode light = new LightNode( GL.GL_LIGHT0 );
    public PieChart chart = new PieChart( );

    private int mX;
    private int mY;

    private static float xtilt = -45f;
    private static float ytilt = 45f;

    /**
     * scale for the chart.
     */
    private static float scale = 1;

    /**
     * current mouse button.
     */
    private int cur_m_button = -1;

    public void display(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);


        gl.glTranslatef(0.0f, 0.0f, -3.0f); // camera looks into -z direction, so lets move the cube in front of the camera, otherwise it would be too close to it.

        /*
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        */



        gl.glScaled( scale, scale, scale );
        light.render( gLDrawable );

        gl.glRotatef( xtilt, 1f, 0f, 0f );
        gl.glRotatef( ytilt, 0, 1, 0 );
        ANode.renderTree( gLDrawable, chart );
        //drawCube( gLDrawable );

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
        //gLDrawable.setGL(new DebugGL(gLDrawable.getGL()));
        final GL gl = gLDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);
        gLDrawable.addKeyListener(this);

        //light.addChild( chart );
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
            HW6A.bQuit = true;
            HW6A.displayT = null;
            System.exit(0);
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseExited( MouseEvent e )
    {
        System.out.println( "Mouse Exited" );
    }

    public void mouseEntered( MouseEvent e )
    {
        System.out.println( "Mouse Entered" );
    }

    public void mouseReleased( MouseEvent e )
    {
        cur_m_button = -1;
    }

    public void mousePressed( MouseEvent e )
    {
        cur_m_button = e.getButton();

    }

    public void mouseClicked( MouseEvent e )
    {
        System.out.println( e.getButton() );
    }

    public void mouseMoved( MouseEvent e )
    {
        mX = e.getX();
        mY = e.getY();


    }

    public void mouseDragged( MouseEvent e )
    {

        if( cur_m_button == e.BUTTON1 )
        {
            ytilt += e.getX() - mX;
            xtilt += e.getY() - mY;

        }
        if( cur_m_button == 3 )
        {
            System.out.println( scale );
            if( mY - e.getY() > 0 )
            {
                scale += .01;
            }
            else
            {
                scale -= .01;
            }

        }
        mY = e.getY();
        mX = e.getX();
    }
 }

 /**
  *
  * <p>Main class for this hw.</p>
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
 public class HW6A implements Runnable, javax.swing.event.ChangeListener {
     static Thread displayT = new Thread(new HW6A());
     static boolean bQuit = false;

     public static JSlider slida = new JSlider(JSlider.HORIZONTAL, 0, 100, 60 );
     public static HW6ARenderer rendera = new HW6ARenderer();

     public static JLabel numba = new JLabel("60 - Blue       ||         40 - Red");

     public static void main(String[] args)
     {
         System.load( System.getProperty( "user.dir" ) + "/jogl.dll" );
         displayT.start();
     }

     public void stateChanged(ChangeEvent e)
     {
         /*
         JSlider source = (JSlider)e.getSource();
         int s = source.getValue();
         numba.setText( Integer.toString( s ) + " - Blue       ||         " + Integer.toString( 100-s ) + " - Red" );
         double new_blue = ((double)s)/100f;
         rendera.chart.put( "Blue", new_blue );
         rendera.chart.put( "Red", 1-new_blue );
         */

     }

     public void run()
     {
         try
         {
             JPanel control = new JPanel( new BorderLayout( ) );
             control.setSize( new Dimension( 640, 200 ) );
             control.add( new JLabel( "Control:" ), BorderLayout.NORTH );

             slida.addChangeListener(this);
             slida.setMajorTickSpacing(10);
             slida.setPaintTicks(true);

             control.add( slida, BorderLayout.SOUTH );
             control.add( numba, BorderLayout.CENTER );



             System.out.println( "Starting...." );
             Frame frame = new Frame("Homework 6A for CS456.  Lev A Neiman - ln180704");
             frame.setResizable( false );
             GLCanvas canvas = new GLCanvas();
             canvas.addGLEventListener(rendera);

             /*
             frame.setLayout( new BorderLayout( ) );
             frame.add(canvas, BorderLayout.CENTER);
             frame.add( control, BorderLayout.SOUTH);
             */
            frame.add( canvas );

             frame.setSize(640, 580);

             canvas.addMouseListener(  rendera );
             canvas.addMouseMotionListener( rendera );

             frame.addWindowListener(new WindowAdapter()
             {
                 public void windowClosing(WindowEvent e)
                 {
                     bQuit = true;
                 }
             });


             frame.setVisible(true);
             //canvas.requestFocus();
             while (!bQuit)
             {
                 canvas.display();
             }
             System.exit( 0 ) ;
         }
         catch( Exception e )
         {
             e.printStackTrace();
         }
     }
 }
