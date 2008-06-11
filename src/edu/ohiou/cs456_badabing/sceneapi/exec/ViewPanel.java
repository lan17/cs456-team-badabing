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
 * <p>OpenGL accelerated panel that renders 3D visualization data.</p>
 *
 * <p>Description: extends GLJPanel and renders Map.  Responds to user interaction.</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ViewPanel extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener, Runnable, KeyListener, MouseWheelListener
{

    /**
    * handle to the zoomerInner object.  When it is not null the display method calls its step() method and checks whether it is done or not. </BR> If it is null then display method doesnt call it.
    */
    private zoomerInner zooming = null;

    /**
     *
     * <p>inner class to handle zooming in stuff.</p>
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
    private class zoomerInner
    {
        /**
        * if this is set to true then shouldDestroy will return true, do appropriate steps, and display method will destroy instance of this object by setting it to null.
        */
        boolean should_destroy = false;

        /**
         * time that this object was created.
         */
        private long start_time;

        /**
         * values of position, zoom_in and tilt at the time of creation.
         */
        private double xtilt_i, ytilt_i, x_disp_i, y_disp_i, z_disp_i, scale_i;

        /**
         * Coordinate of where zooming in should zoom in to.
         */
        private Coordinate where;

        /**
         * how long should the whole zoom-in process take place (in milliseconds) ?  1000 * 2 = 2 seconds.
         */
        public long duration = 1000 * 2;

        /**
         * this is equivalent to how close the zoom_in should be when its done.
         */
        public double zdisp_f = -1f;

        /**
         * default constructor.
         * @param where Coordinate
         */
        public zoomerInner( Coordinate where )
        {
            start_time = System.currentTimeMillis();
            xtilt_i = xtilt;
            ytilt_i = ytilt;
            x_disp_i = xdisp;
            y_disp_i = ydisp;
            z_disp_i = zdisp;
            this.where = where;
            where.setX( where.getX() * -1f );
            where.setY( where.getZ() );
        }

        /**
         * advance one step in zooming process.
         */
        public void step()
        {

            xtilt = interpolate(xtilt_i, 0, start_time, start_time + duration, System.currentTimeMillis());
            ytilt = interpolate(ytilt_i, 0, start_time, start_time + duration, System.currentTimeMillis());


            xdisp = interpolate(x_disp_i, where.getX(), start_time, start_time + duration, System.currentTimeMillis());
            ydisp = interpolate(y_disp_i, where.getY(), start_time, start_time + duration, System.currentTimeMillis());
            zdisp = interpolate(z_disp_i, zdisp_f, start_time, start_time + duration, System.currentTimeMillis());

           //System.out.println( Double.toString( xtilt )
       }

       /**
        * if this object should be destroyes, then set position and tilt values to their final ones, and return true so display  method can destroy this object.
        * @return boolean
        */
       public boolean shouldDestroy()
        {
            if( ( start_time + duration ) < System.currentTimeMillis() || should_destroy )
            {
                xtilt = 0;
                ytilt = 0;

                xdisp = where.getX();
                ydisp = where.getY();
                zdisp = zdisp_f;

                return true;
            }
            return false;

        }


    }

    /**
     * Interpolation function between (t1, y1) and (t2, y2).  takes those input plus ti and reterns yi.
     * @param y1 double
     * @param y2 double
     * @param t1 double
     * @param t2 double
     * @param ti long
     * @return double
     */
    public double interpolate( double y1, double y2, double t1, double t2, long ti )
    {
        double alpha = ( y2 - y1 ) / ( t2 - t1 );
        double beta = y1 - alpha * t1;
        return alpha * ti + beta;

    }

    /**
     * handle to thread that measures and prints out FPS info.
     */
    Thread measure_fps = null;

    /**
     * frame counter.
     */
    public int frames=0;

    /**
     * handle to MainUIPanel
     */
    private MainUIPanel main_panel = null;

    /**
     * handle to Map
     */
    public Map map = null;

    /**
     * handle to light source.
     */
    private LightNode light;

    /**
     * x position of last mouse move.
     */
    private int mX;

    /**
     * y position of the last mouse move.
     */
    private int mY;

    /**
     * x position of camera.
     */
    private static double xdisp = 0;

    /**
     * y position of camera.
     */
    private static double ydisp = 0;

    /**
     * z position of camera (how close is it to the map?).
     */
    private static double zdisp = -4.5;

    /**
     * xtilt of the camera.
     */
    private static double xtilt = 0f;

    /**
     * ytilt of the camera.
     */
    private static double ytilt = 0f;

    /**
     * mouse button that was pressed.
     */
    private int cur_m_button = -1;

    /**
     * handle to thread that calls display() continuously.
     */
    public Thread render_thread;

    /**
     * GLContext of this panel.
     */
    public GLContext context;

    /**
     * how many times has this panel been re-initialized.
     */
    private int num_inits = 0;

    /**
     * mapping of county name to RGB color
     */
    HashMap< String, Color > name2color = new HashMap< String, Color >();

    /**
     * mapping of RGB color to county name.
     */
    HashMap< Color, String > color2name = new HashMap<Color,String>();


    // flags

    /**
     * if this is switched to true, ViewPanel will attempt to read DataBaseModel from MainUIPanel and update Spheres and PieCharts accordingly.
     */
    private boolean should_update = false;

    /**
     * previouis value for enable_transperency.
     */
    private boolean enable_transperency_p = true;

    /**
     * if this is false then transperency is disabled.  otherwise it is enabled.
     */
    private boolean enable_transperency = false;

    /**
     * set whether transperency should be used or not.
     * @param transp boolean
     */
    public void setTransperency( boolean transp )
    {
        enable_transperency_p = enable_transperency;
        enable_transperency = transp;
    }

    /**
     * add county name to RGB color mapping.
     * @param name String
     * @param color Color
     */
    private void addSM( String name, Color color )
    {
        name2color.put( name, color );
        color2name.put( color, name );
    }

    /**
     * default constructor.
     * @param main_panel MainUIPanel
     */
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

            //main_panel.loadHarvestData( System.getProperty( "user.dir" ) + "\\data\\db.xls" );

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        render_thread = new Thread( this );
        render_thread.start();



    }

    /**
     * triggers ViewPanel to update sizes of Spheres and PieCharts
     */
    public void triggerUpdate(  )
    {
        should_update = true;
    }

    /**
     * the radius of the Sphere representing the county with smallest deer harvest.
     */
    private static final double min_radius = .5;

    /**
     * the radius of the Sphere representing the county with largest deer harvest.
     */
    private static final double max_radius = 3;

    /**
     * take a deer Tuple
     */
    public void updateDeerTuple( DeerTuple info )
    {
        PieChart p = (PieChart)map.string2pie.get( info.county );
        Sphere sphere = map.string2sphere.get( info.county );

        p.clear();

        double max_population = main_panel.getDB().getMaxHarvest();
        double min_population = main_panel.getDB().getMinHarvest();

        if( max_population < info.sum() ) max_population = info.sum();

        double alpha = (max_radius - min_radius)  / ( max_population - min_population );
        double beta = min_radius - alpha * min_population;

        double new_radius = alpha * info.sum() + beta ;

        sphere.setRadius( (float)new_radius );

        double s = info.sum();
        p.put( "Fawn Does", info.fawn_does / s );
        p.put( "Yearling Does", info.yearling_does / s );
        p.put( "Adult Does", info.adult_does / s );
        p.put( "Fawn Bucks", info.fawn_bucks / s );
        p.put( "Yearling Bucks", info.yearling_bucks / s );
        p.put( "Adult Bucks", info.adult_bucks / s );

    }

    /**
     * Updates all objects in map to reflect information from DataBaseModel and current year.
     */
    private void update()
    {
        if( main_panel.getDB() == null ) return;


        Random generator = new Random(  );
        for( DeerTuple deer : main_panel.getDB().tuples )
        {
            if( deer.year == main_panel.current_year )
            {
                /*
                PieChart p = (PieChart)map.string2pie.get( deer.county );
                Sphere sphere = map.string2sphere.get( deer.county );
                p.clear();

                double max_population = main_panel.getDB().getMaxHarvest();
                double min_population = main_panel.getDB().getMinHarvest();

                double alpha = (max_radius - min_radius)  / ( max_population - min_population );
                double beta = min_radius - alpha * min_population;

                double new_radius = alpha * deer.sum() + beta ;


                sphere.setRadius( (float)new_radius );

                double s = deer.sum();
                p.put( "Fawn Does", deer.fawn_does / s );
                p.put( "Yearling Does", deer.yearling_does / s );
                p.put( "Adult Does", deer.adult_does / s );
                p.put( "Fawn Bucks", deer.fawn_bucks / s );
                p.put( "Yearling Bucks", deer.yearling_bucks / s );
                p.put( "Adult Bucks", deer.adult_bucks / s );
                */

                updateDeerTuple( deer );


                /*
                double lol = generator.nextDouble();
                p.put( "lol", lol );
                p.put( "unlol", 1-lol );
                */

                //System.out.println( deer.toString() );
            }
        }
    }

    /**
     * return handle to Map object.
     * @return Map
     */
    public Map getMap()
    {
        return map;
    }

    /**
     * zoom in a little bit.
     */
    public void zoomIn()
    {
        zdisp += .1;
    }

    /**
     * zoom out a little bit.
     */
    public void zoomOut()
    {
        zdisp -= .1;
    }

    /**
     * render ViewPanel.
     * @param gLDrawable GLAutoDrawable
     */
    public void display(GLAutoDrawable gLDrawable)
    {
        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        if( zooming != null )
        {
            zooming.step();
            if( zooming.shouldDestroy() )
            {
                zooming = null;
            }
        }

        if( enable_transperency_p != enable_transperency )
        {
            if( enable_transperency )
            {
                gl.glEnable( GL.GL_BLEND);
                gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
            }
            else
            {
                gl.glDisable( GL.GL_BLEND );
            }
            enable_transperency_p = enable_transperency;
        }

        gl.glTranslated( 0, 0, zdisp );



        gl.glRotated( xtilt, 1f, 0f, 0f );
        gl.glRotated( ytilt, 0, 1, 0 );

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

        num_inits++;

        System.out.println( "ViewPanel is initializing " + Integer.toString( num_inits ) + " timez!" + super.getSize().toString());
        final GL gl = gLDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(1f, 1f, 1f, 0.0f);
        //gl.glClearColor( 0, 0, 0, 0 );
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);




        if( map == null )
        {
            map = new Map();
            light = new LightNode(GL.GL_LIGHT0);
            LightNode light2 = new LightNode( GL.GL_LIGHT1 );
            light2.translation().setY( light2.translation().getY() * - 1 );
            LightNode light3 =  new LightNode( GL.GL_LIGHT2 );
            light3.translation().setX( light3.translation().getX() * - 1 );
            LightNode light4 =  new LightNode( GL.GL_LIGHT3 );
            light4.translation().setZ( light4.translation().getZ() * -1 ) ;

            map.addChild(light);
            map.addChild( light2 );
            map.addChild( light3 );
            map.addChild( light4 );

        }
        else
        {
            TextureManager.clear();
            DisplayListManager.clear( gLDrawable );
            map.reInit();
        }

        if( measure_fps == null )
        {
            measure_fps = new Thread( new FPSMeasurer( this, measure_fps ) );
            measure_fps.start();
        }
        context = GLContext.getCurrent();

    }

    /**
     * calls when ViewPanel is being reshaped.
     * @param gLDrawable GLAutoDrawable
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
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
        glu.gluPerspective(50.0f, h, .01, 1000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);

    }

    /**
     * set a new Transperency.
     * @param transperency int
     */
    public void changeTransperency( int transperency )
    {
        float t = (float)transperency / 100f;
        //System.out.println( t );
        Sphere.setTransperency( t );
        PieChart.setTransperency( t );
    }


    /**
     * adds some event listeners.
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {


        addGLEventListener( this );
        addMouseListener( this );
        addMouseMotionListener( this );
        addMouseWheelListener( this );

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


    /**
     * on mouse pressed.
     * @param e MouseEvent
     */
    public void mousePressed( MouseEvent e )
    {
        cur_m_button = e.getButton();

    }

    /**
     * on mouse Clicked event.  Does the zoom in, zoom out stuff.
     * @param e MouseEvent
     */
    public void mouseClicked( MouseEvent e )
    {
        if( e.getButton() != 1 )
        {
            zoomDefault();
            return;
        }
        if( zooming != null ) zooming = null;
        String county = selectCounty();
        if( county == null ) return;
        if( e.getButton() == 1 )
        {
            //zoomIn(county);
            main_panel.setCurrentCounty( county );
        }

    }

    /**
     * causes ViewPanel to zoom in on selected county.
     * @param county_name String
     */
    public void zoomIn( String county_name )
    {
        if( county_name == null ) return;
        if( map == null ) return;
        Coordinate where = map.getCountyCoordinate( county_name );
        if( where == null ) return;
        //System.out.println( county + " selected " + where.toString());
        zooming = new zoomerInner( where );
        //zooming.zdisp_f = -1.25f;

    }

    /**
     * zoom in to default view.
     */
    public void zoomDefault()
    {
        Coordinate where = new Coordinate( 0, 0, 0 );
        zooming = new zoomerInner( where );
        zooming.zdisp_f = -4.5f;
    }

    /**
     * return the color at x, y position on the screen. this is used for selection.
     * @param x int
     * @param y int
     * @return Color
     */
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

    /**
     * on mouse moved.  update mX and mY, and do selectCounty().
     * @param e MouseEvent
     */
    public void mouseMoved( MouseEvent e )
    {
        mX = e.getX();
        mY = e.getY();

        selectCounty();
    }

    /**
     * method to select county based on the color that is at mX, mY position.  Get that color and then return its mapping to county name.
     * @return String
     */
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

    /**
     * on mouse dragged.  moves the map on left mouse button, and rotates it on right mouse button.
     * @param e MouseEvent
     */
    public void mouseDragged( MouseEvent e )
    {
        zooming = null;
        if( cur_m_button == e.BUTTON1  )
        {
            xdisp += ( e.getX() - mX ) / 100f;
            ydisp += ( mY - e.getY() ) / 100f;

            //System.out.println( "Current position - " + Double.valueOf( xdisp ) + ", " + Double.valueOf( ydisp ) );
        }
        if( cur_m_button == 3 )
        {
            ytilt += ( e.getX() - mX );
            xtilt += ( e.getY() - mY );
        }

        mY = e.getY();
        mX = e.getX();
    }

    /**
     * handle mouse wheel events.
     * @param e MouseWheelEvent
     */
    public void mouseWheelMoved( MouseWheelEvent e )
    {
        if( e.getWheelRotation( ) > 0 )
        {
            zoomOut();
        }
        else
        {
            zoomIn();
        }

    }

    public void keyReleased( KeyEvent k )
    {


    }

    public void keyPressed( KeyEvent k )
    {

    }

    public void keyTyped( KeyEvent k )
    {


    }

    public boolean should_display = true;

    /**
     * run method for rendering thread.
     */
    public void run()
    {
        while( true )
        {
            if( should_update )
            {
                update();
                should_update = false;
            }
            if( should_display )
            {
                display();
                frames++;
                //render_thread.yield();
            }
        }
    }

    /**
     * saves the screen shot of the color buffer into file in some format.
     * @param file File
     * @param format String
     * @return boolean
     */
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


/**
 *
 * <p>Helper class to print out and measure FPS.</p>
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
            //System.out.println(adaptee.frames);
            adaptee.frames = 0;
        }
    }
}
