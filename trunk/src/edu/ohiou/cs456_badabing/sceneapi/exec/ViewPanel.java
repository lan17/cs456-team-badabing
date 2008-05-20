package edu.ohiou.cs456_badabing.sceneapi.exec;

import java.nio.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import edu.ohiou.cs456_badabing.sceneapi.visualize.Map;
import com.sun.opengl.util.*;
import java.util.*;
import edu.ohiou.cs456_badabing.sceneapi.exec.data_model.*;
import edu.ohiou.cs456_badabing.sceneapi.visualize.*;
import java.util.Random;
import java.io.*;
import javax.imageio.*;

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
public class ViewPanel extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener, Runnable, KeyListener
{

    private class zoomerInner implements Runnable
    {
        public Thread thread;

        private long start_time;
        private double xtilt_i, ytilt_i, x_disp_i, y_disp_i, z_disp_i, scale_i;
        private Coordinate where;

        public long duration = 1000 * 3;
        public zoomerInner( Coordinate where )
        {
            start_time = System.currentTimeMillis();
            xtilt_i = xtilt;
            ytilt_i = ytilt;
            x_disp_i = xdisp;
            y_disp_i = ydisp;
            z_disp_i = zdisp;
            scale_i = scale;
            this.where = where;

        }

        public void run()
        {
            xtilt = interpolate(xtilt_i, 0, start_time, start_time + duration, System.currentTimeMillis());
            ytilt = interpolate(ytilt_i, 0, start_time, start_time + duration, System.currentTimeMillis());

            xdisp = interpolate(x_disp_i, where.x, start_time, start_time + duration, System.currentTimeMillis());
            ydisp = interpolate(y_disp_i, where.y, start_time, start_time + duration, System.currentTimeMillis());
            scale = interpolate(scale_i, 6, start_time, start_time + duration, System.currentTimeMillis());

            try
            {
               thread.sleep(50);
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }

        }

        public double interpolate( double xi, double xk, double ti, float tk, long tj )
        {
            double slope = ( xi - xk ) / ( ti - tk );
            double offset = xk  - slope * tk;
            return slope * tj  + offset;
        }
    }
    Thread zooma = null;
    Thread measure_fps = null;
    public int frames=0;

    private MainUIPanel main_panel = null;

    public Map map = null;
    private LightNode light;

    private int mX;
    private int mY;

    private static double xdisp = 0;
    private static double ydisp = 0;
    private static double zdisp = -4.5;

    private static double xtilt = 0f;
    private static double ytilt = 0f;


    private int cur_m_button = -1;
    private static double scale = 1;

    public Thread render_thread;

    private GLContext context;



    HashMap< String, Color > name2color = new HashMap< String, Color >();
    HashMap< Color, String > color2name = new HashMap<Color,String>();

    private void addSM( String name, Color color )
    {
        name2color.put( name, color );
        color2name.put( color, name );
    }

    public ViewPanel( MainUIPanel main_panel )
    {
        this.main_panel = main_panel;

        addSM( "lawrence", new Color( 135, 135, 135 ) );
        addSM( "scioto", new Color( 0, 0, 0 ) );
        addSM( "adams", new Color( 0, 5, 0 ) );
        addSM( "brown", new Color( 0, 10, 0 ) );
        addSM( "highland", new Color( 0, 15, 0 ) );
        addSM( "clermont", new Color( 0, 20, 0 ) );
        addSM( "hamilton", new Color( 0, 25, 0 ) );
        addSM( "butler", new Color( 0, 30, 0 ) );
        addSM( "warren", new Color( 0, 35, 0 ) );
        addSM( "clinton", new Color( 0, 40, 0 ) );
        addSM( "preble", new Color( 0, 45, 0 ) );
        addSM( "montgomery", new Color( 0, 50, 0 ) );
        addSM( "greene", new Color( 0, 55, 0 ) );
        addSM( "fayette", new Color( 0, 60, 0 ) );
        addSM( "pike", new Color( 0, 65, 0 ) );
        addSM( "ross", new Color( 0, 70, 0 ) );
        addSM( "jackson", new Color( 0, 75, 0 ) );
        addSM( "gallia", new Color( 0, 80, 0 ) );
        addSM( "meigs", new Color( 0, 85, 0 ) );
        addSM( "vinton", new Color( 0, 90, 0 ) );
        addSM( "athens", new Color( 0, 95, 0 ) );
        addSM( "washington", new Color( 0, 100, 0 ) );
        addSM( "hocking", new Color( 0, 105, 0 ) );
        addSM( "morgan", new Color( 0, 110, 0 ) );
        addSM( "perry", new Color( 0, 115, 0 ) );
        addSM( "monroe", new Color( 0, 120, 0 ) );
        addSM( "noble", new Color( 0, 125, 0 ) );
        addSM( "belmont", new Color( 0, 130, 0 ) );
        addSM( "guernsey", new Color( 0, 135, 0 ) );
        addSM( "muskingum", new Color( 0, 140, 0 ) );
        addSM( "jefferson", new Color( 0, 145, 0 ) );
        addSM( "harrison", new Color( 0, 150, 0 ) );
        addSM( "tuscarawas", new Color( 0, 155, 0 ) );
        addSM( "coshocton", new Color( 0, 160, 0 ) );
        addSM( "fairfield", new Color( 0, 165, 0 ) );
        addSM( "pickaway", new Color( 0, 170, 0 ) );
        addSM( "madison", new Color( 0, 175, 0 ) );
        addSM( "clark", new Color( 0, 180, 0 ) );
        addSM( "miami", new Color( 0, 185, 0 ) );
        addSM( "darke", new Color( 0, 190, 0 ) );
        addSM( "mercer", new Color( 0, 195, 0 ) );
        addSM( "shelby", new Color( 0, 200, 0 ) );
        addSM( "auglaize", new Color( 0, 205, 0 ) );
        addSM( "logan", new Color( 0, 210, 0 ) );
        addSM( "champaign", new Color( 0, 215, 0 ) );
        addSM( "union", new Color( 0, 220, 0 ) );
        addSM( "franklin", new Color( 0, 225, 0 ) );
        addSM( "delaware", new Color( 0, 230, 0 ) );
        addSM( "licking", new Color( 0, 235, 0 ) );
        addSM( "knox", new Color( 0, 240, 0 ) );
        addSM( "morrow", new Color( 0, 245, 0 ) );
        addSM( "marion", new Color( 0, 250, 0 ) );
        addSM( "hardin", new Color( 0, 0, 5 ) );
        addSM( "allen", new Color( 0, 0, 10 ) );
        addSM( "vanwert", new Color( 0, 0, 15 ) );
        addSM( "paulding", new Color( 0, 0, 20 ) );
        addSM( "putnam", new Color( 0, 0, 25 ) );
        addSM( "hancock", new Color( 0, 0, 30 ) );
        addSM( "wyandot", new Color( 0, 0, 35 ) );
        addSM( "crawford", new Color( 0, 0, 40 ) );
        addSM( "richland", new Color( 0, 0, 45 ) );
        addSM( "ashland", new Color( 0, 0, 50 ) );
        addSM( "holmes", new Color( 0, 0, 55 ) );
        addSM( "wayne", new Color( 0, 0, 60 ) );
        addSM( "stark", new Color( 0, 0, 65 ) );
        addSM( "carroll", new Color( 0, 0, 70 ) );
        addSM( "columbiana", new Color( 0, 0, 75 ) );
        addSM( "mahoning", new Color( 0, 0, 80 ) );
        addSM( "portage", new Color( 0, 0, 85 ) );
        addSM( "summit", new Color( 0, 0, 90 ) );
        addSM( "medina", new Color( 0, 0, 95 ) );
        addSM( "huron", new Color( 0, 0, 100 ) );
        addSM( "seneca", new Color( 0, 0, 105 ) );
        addSM( "wood", new Color( 0, 0, 110 ) );
        addSM( "henry", new Color( 0, 0, 115 ) );
        addSM( "defiance", new Color( 0, 0, 120 ) );
        addSM( "williams", new Color( 0, 0, 125 ) );
        addSM( "fulton", new Color( 0, 0, 130 ) );
        addSM( "lucas", new Color( 0, 0, 135 ) );
        addSM( "ottawa", new Color( 0, 0, 140 ) );
        addSM( "sandusky", new Color( 0, 0, 145 ) );
        addSM( "erie", new Color( 0, 0, 150 ) );
        addSM( "lorain", new Color( 0, 0, 155 ) );
        addSM( "cuyahoga", new Color( 0, 0, 160 ) );
        addSM( "lake", new Color( 0, 0, 165 ) );
        addSM( "geauga", new Color( 0, 0, 170 ) );
        addSM( "trumbull", new Color( 0, 0, 175 ) );
        addSM( "ashtabula", new Color( 0, 0, 180 ) );



        // populate selection mapping.

        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        render_thread = new Thread( this );
        render_thread.start();
    }

    public void update()
    {
        Random generator = new Random( 101 );
        for( DeerTuple deer : main_panel.db.tuples )
        {
            if( deer.year == main_panel.current_year )
            {
                PieChart p = (PieChart)map.string2pie.get( deer.county );
                Sphere sphere = map.string2sphere.get( deer.county );
                p.clear();
                //sphere.radius = generator.nextFloat();

                double s = deer.sum();
                p.put( "Fawn Does", deer.fawn_does / s );
                p.put( "Yearling Does", deer.yearling_does / s );
                p.put( "Adult Does", deer.adult_does / s );
                p.put( "Fawn Bucks", deer.fawn_bucks / s );
                p.put( "Yearling Bucks", deer.yearling_bucks / s );
                p.put( "Adult Bucks", deer.adult_bucks / s );

                /*
                double lol = generator.nextDouble();
                p.put( "lol", lol );
                p.put( "unlol", 1-lol );
                */

                System.out.println( deer.toString() );
            }
        }
    }

    public Map getMap()
    {
        return map;
    }

    public void display(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslated( 0, 0, zdisp );

        gl.glScaled( scale, scale, scale );


        gl.glRotated( xtilt, 1f, 0f, 0f );
        gl.glRotated( ytilt, 0, 1, 0 );

        gl.glTranslated( xdisp, ydisp, 0 );

        ANode.renderTree( gLDrawable, map );
        /*
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();


 glu.gluSphere(quadric, 0.5f, 20, 20);
*/

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
        gl.glClearColor(1f, 1f, 1f, 0.0f);
        //gl.glClearColor( 0, 0, 0, 0 );
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);
        TextureManager.clear();

        map = new Map();
        light = new LightNode(GL.GL_LIGHT0);
        map.addChild(light);

        if( measure_fps == null )
        {
            measure_fps = new Thread( new FPSMeasurer( this, measure_fps ) );
            measure_fps.start();
        }
        context = GLContext.getCurrent();

    }

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height)
    {
        final GL gl = gLDrawable.getGL();
        GLU glu = new GLU();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);

    }

    private void jbInit()
            throws Exception
    {
        addKeyListener( this );
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
        cur_m_button = -1;
    }

    public void mousePressed( MouseEvent e )
    {
        cur_m_button = e.getButton();

    }

    public void mouseClicked( MouseEvent e )
    {
        String county = selectCounty();
        if( county == null ) return;
        Coordinate where = new Coordinate( map.string2node.get( county ).translation );

    }

    private Color getColor( int x, int y )
    {

        context.makeCurrent();

        // I borrowed the code to transform int to 3 color components from this page
        // http://www.rgagnon.com/javadetails/java-0257.html
        BufferedImage image = com.sun.opengl.util.Screenshot.readToBufferedImage( super.getWidth(), super.getHeight() );
        //System.out.println( Integer.toString( x ) + "," + Integer.toString( y ) + " :: " + Integer.toString( image.getWidth() ) + ", " + Integer.toString( image.getHeight( )) );
        int c = image.getRGB(x,y);
        int  red = (c & 0x00ff0000) >> 16;
        int  green = (c & 0x0000ff00) >> 8;
        int  blue = c & 0x000000ff;

        Color ret = new Color( red, green, blue );
        return ret;


       /*

       IntBuffer f = IntBuffer.allocate( 3 );
       context.getGL().glReadPixels( x, y, 1, 1, GL.GL_RGB, GL.GL_INT, f );
       for( int a : f.array() )
       {
           System.out.println( a );
       }

       return new Color( 0, 0, 0 );
       */
    }

    public void mouseMoved( MouseEvent e )
    {
        mX = e.getX();
        mY = e.getY();

        selectCounty();
    }

    private String selectCounty()
    {
        map.useSelectionMap(this);
        boolean tmp = map.isShowPies();
        boolean tmp2 = map.isShowSpheres();
        map.setShowSpheres( false );
        map.setShowPie( false );
        context.makeCurrent();
        display( this );
        Color lol = getColor( mX, mY );
        map.usePreviousSelectionMap( this );
        map.setShowPie( tmp );
        map.setShowSpheres( tmp2 );
        display();
        String name = color2name.get( lol );
        if( name != null )
        {
            //System.out.println(name);
            main_panel.countyMouseOver( name );
        }
        else
        {
            main_panel.countyMouseOver( "Move mouse to select county" );
            //System.out.println( "nothing" );
        }
        return name;

    }

    public void mouseDragged( MouseEvent e )
    {

        if( cur_m_button == e.BUTTON1  )
        {
            xdisp += ( e.getX() - mX ) / 100f;
            ydisp += ( mY - e.getY() ) / 100f;
        }
        if( cur_m_button == 3 )
        {
            ytilt += ( e.getX() - mX );
            xtilt += ( e.getY() - mY );
        }
        if( cur_m_button == 2 )
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

    private boolean shift_down = false;

    public void keyReleased( KeyEvent k )
    {
        System.out.println( "key pressed" );
        if( !k.isShiftDown() )
        {
            shift_down = false;
        }

    }

    public void keyPressed( KeyEvent k )
    {

        if( k.isShiftDown() )
        {
            shift_down = true;
            System.out.println( "SHIFT" );
        }
    }

    public void keyTyped( KeyEvent k )
    {

        if( k.getKeyCode() == k.VK_Q )
        {
            scale += .1;
        }
        if( k.getKeyCode() == k.VK_A )
        {
            scale -= .1;
        }

    }



    public void run()
    {
        while( true )
        {
            display();
            frames++;
        }
    }

    public boolean takeScreenshot( File file, String format )
    {
        context.makeCurrent();
        BufferedImage image = Screenshot.readToBufferedImage( super.getWidth(), super.getHeight()  );
        try
        {
            ImageIO.write(image, format, file);
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}

class FPSMeasurer implements Runnable
{
    ViewPanel adaptee;
    Thread thread;

    FPSMeasurer( ViewPanel adaptee, Thread thread )
    {
        this.thread = thread;
        this.adaptee = adaptee;
    }

    public void run()
    {
        while( true )
        {
            try
            {
                thread.sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println(adaptee.frames);
            adaptee.frames = 0;
        }
    }
}
