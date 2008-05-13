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
public class ViewPanel extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener, Runnable
{
    private MainUIPanel main_panel = null;

    private Map map;
    private LightNode light;

    private int mX;
    private int mY;

    private static float xdisp = 0;
    private static float ydisp = 0;

    private static float xtilt = 0f;
    private static float ytilt = 0f;

    private int cur_m_button = -1;
    private static float scale = 1;

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
        for( DeerTuple deer : main_panel.db.tuples )
        {
            if( deer.year == main_panel.current_year )
            {
                PieChart p = map.pie_hash.get( deer.county );
                p.clear();

                double s = deer.sum();
                p.put( "Fawn Does", deer.fawn_does / s );
                p.put( "Yearling Does", deer.yearling_does / s );
                p.put( "Adult Does", deer.adult_does / s );
                p.put( "Fawn Bucks", deer.fawn_bucks / s );
                p.put( "Yearling Bucks", deer.yearling_bucks / s );
                p.put( "Adult Bucks", deer.adult_bucks / s );

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

        gl.glTranslated( 0, 0, -5 );

        gl.glScaled( scale, scale, scale );


        gl.glRotatef( xtilt, 1f, 0f, 0f );
        gl.glRotatef( ytilt, 0, 1, 0 );

        gl.glTranslated( xdisp, ydisp, 0 );

        ANode.renderTree( gLDrawable, map );
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

        map = new Map();
        light = new LightNode( GL.GL_LIGHT0 );
        map.addChild( light );

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
    }

    public void mouseMoved( MouseEvent e )
    {
        mX = e.getX();
        mY = e.getY();

        map.useSelectionMap();
        boolean tmp = map.isShowPies();
        map.setShowPie( false );
        context.makeCurrent();
        display( this );
        Color lol = getColor( mX, mY );
        map.useDefaultMap();
        map.setShowPie( tmp );
        String name = color2name.get( lol );
        if( name != null )
        {
            System.out.println(name);
            main_panel.countyMouseOver( name );
        }
        else
        {
            main_panel.countyMouseOver( "Move mouse to select county" );
            System.out.println( "nothing" );
        }
        System.out.println( lol.toString() );
    }

    public void mouseDragged( MouseEvent e )
    {

        if( cur_m_button == e.BUTTON1 )
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

    public void run()
    {
        while( true )
        {
            display();
        }
    }


}
