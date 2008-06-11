package edu.ohiou.cs456_badabing.sceneapi.visualize;

import java.util.ArrayList;
import java.util.HashMap;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.texture.Texture;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;

/**
 * <p>Renders Ohio Map and adds Sphere and PieChart objects as its children.</p>
 *
 * <p>Description: This is the only thing (besides the light) rendered in the view panel.</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class Map extends ANode
{
    /**
    * file name of the default map texture.
    */
    public String district_texture_name = "\\data\\district_map.jpg";

    /**
     * path to deer_managment_regions texture.
     */
    public String deer_managment_regions_texture_name = "\\data\\deer_management_regions.jpg";

    /**
     * path to black and white map of ohio texture.
     */
    public String plain_texture_name = "\\data\\ohio.jpg";

    /**
     * path to the OBJ file of Ohio that provides Coordinates for each county.
     */
    public ModelNode counties= new ModelNode( new ModelFile( System.getProperty( "user.dir" ) + "\\data\\ohio.obj" ) );

    /**
     * path to selection texture.
     */
    public String selection_texture_name = "\\data\\district_selection_map.png";

    /**
     * true if pie_charts are to be displayed.
     */
    private boolean show_pie_charts = true;

    /**
     * true if the spheres are to be displayed.
     */
    private boolean show_spheres = true;

    /**
     * array of PieCharts.
     */
    public ArrayList< PieChart > piez = new ArrayList<PieChart>();

    /**
     * array of Spheres.
     */
    public ArrayList< Sphere > spherez = new ArrayList<Sphere>();

    /**
     * mapping of string (county name) to a node.
     */
    private HashMap< String, ANode > string2node = new HashMap<String,ANode>();

    /**
     * mapping of string (county name) to a PieChart.
     */
    public HashMap< String, PieChart > string2pie = new HashMap< String,PieChart >();

    /**
     * mapping of string (county name) to a Sphere.
     */
    public HashMap< String, Sphere > string2sphere = new HashMap<String,Sphere>();

    /**
     * the horizontal offset between spheres and pie charts.
     */
    private static double horizontal_offset = 2f ;


    /**
     * texture to use for the map.
     */
    public Texture texture;

    /**
     * previous texture used.
     */
    private Texture prev_texture;

    /**
     * return Coordinate of a county
     * @param county String
     * @return Coordinate
     */
    public Coordinate getCountyCoordinate( String county )
    {
        if( !string2node.containsKey( county ) )
        {
            return null;
        }
        else
        {
            return new Coordinate( string2node.get( county ).translation() );
        }
    }

    /**
     * Default constructor.
     */
    public Map()
    {
        ANode lol = new ANode();
        initialize_textures();
        for( MaterialGroupNode group : counties.mtl_nodes )
        {
            if( ! group.getName().equals( "Default" ) )
            {
                ANode containa =  new ANode();
                string2node.put( group.getName(), containa );

                PieChart pie = new PieChart();
                Sphere sphere =  new Sphere();
                sphere.setName( group.getName() );
                sphere.translation().set( -horizontal_offset, 0, 0 );
                pie.translation().set( horizontal_offset, 0, 0 );
                spherez.add( sphere );
                string2sphere.put( group.getName(), sphere );
                piez.add( pie );
                string2pie.put( group.getName(), pie );
                pie.setName( group.getName() );
                containa.scale().set(.05, .05, .05);
                containa.translation().set(group.getCenter());
                containa.translation().setY( -.1 );
                //pie.thickness = .1;
                pie.rotation().set(1, 0, 0);
                pie.rotation_angle = 90f;

                containa.addChild( pie );
                containa.addChild( sphere );
                lol.addChild(containa);
            }
        }
        lol.rotation().set( 1, 0, 0 );
        lol.rotation_angle = 90;
        lol.translation().set( 0, 0, .25 );
        addChild( lol );
        counties.scale().set( 1, 1, -1 );
    }

    /**
     * this function pre-loads the textures into memory.
     */
    private void initialize_textures()
    {
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + district_texture_name );
        TextureManager.getTexture( System.getProperty( "user.dir" ) + selection_texture_name );
        TextureManager.getTexture( System.getProperty( "user.dir" ) + deer_managment_regions_texture_name );
        TextureManager.getTexture( System.getProperty( "user.dir" ) + this.plain_texture_name );

    }

    /**
     * reinitilize texture stuff and re-generate display lists.
     */
    public void reInit()
    {
        initialize_textures();
        Sphere.need_to_generate_display_list = true;
    }

    /**
     * bind selectin texture to map.
     * @param gl GLAutoDrawable
     */
    public void useSelectionMap( GLAutoDrawable gl )
    {
        //gl.getContext().makeCurrent();
        prev_texture = texture;
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + selection_texture_name );
    }

    /**
     * use district map texture.
     * @param gl GLAutoDrawable
     */
    public void useDistrictMap( GLAutoDrawable gl )
    {
        //gl.getContext().makeCurrent();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + district_texture_name );
    }

    /**
     * use deer managment map texture.
     * @param gl GLAutoDrawable
     */
    public void useDeerManagmentMap( GLAutoDrawable gl )
    {
        //gl.getContext().makeCurrent();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + deer_managment_regions_texture_name  );
    }

    /**
     * use black and white map texture.
     * @param gl GLAutoDrawable
     */
    public void usePlainMap( GLAutoDrawable gl )
    {
        //gl.getContext().makeCurrent();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + this.plain_texture_name  );
    }

    /**
     * revert texture to whatever it was before (prev_texture).
     * @param gl GLAutoDrawable
     */
    public void usePreviousSelectionMap( GLAutoDrawable gl )
    {
        //gl.getContext().makeCurrent();
        texture = prev_texture;
    }

    /**
     * return if the PieCharts are to be displayed.
     * @return boolean
     */
    public boolean isShowPies()
    {
        return show_pie_charts;
    }

    /**
     * return if the Spheres are to be displayed.
     * @return boolean
     */
    public boolean isShowSpheres()
    {
        return show_spheres;
    }

    /**
     * set whether to show spheres or not.
     * @param f boolean
     */
    public void setShowSpheres( boolean f )
    {
        show_spheres = f;

        for( Sphere s : spherez )
        {
            s.show = f;
        }
        if( f == false )
        {
            for( PieChart p : piez )
            {
                p.translation().set( 0, 0, 0);
            }
        }
        if( f == true )
        {
            for( PieChart p : piez )
            {
                p.translation().set( horizontal_offset, 0, 0 );
            }
        }

    }

    /**
     * set whether to show PieCharts or not.
     * @param f boolean
     */
    public void setShowPie( boolean f )
    {
        show_pie_charts = f;
        for( PieChart kid : piez )
        {
            if( f == false )
            {
                ((ANode)kid).show = false;

                for( Sphere s : spherez )
                {
                    s.translation().set( 0, 0, 0 );
                }
            }
            if( f == true )
            {
                ((ANode)kid).show = true;

                for( Sphere s : spherez )
                {
                    s.translation().set( -horizontal_offset, 0, 0 );
                }

            }
        }
    }

    /**
     * Renders the map.
     * @param panel GLAutoDrawable
     */
    public void render( GLAutoDrawable panel )
    {
        super.render( panel );
        GL gl = panel.getGL();
        gl.glColor3d( 1, 1, 1 );
        texture.enable();
        texture.bind();
        gl.glDisable( GL.GL_LIGHTING );
        gl.glBegin( GL.GL_QUADS );
        {
            gl.glTexCoord2d( 0f, 0f );
            gl.glVertex3d( -2, 2, 0 );
            gl.glTexCoord2d( 0, 1 );
            gl.glVertex3d( -2, -2, 0 );
            gl.glTexCoord2d( 1, 1 );
            gl.glVertex3d( 2, -2, 0 );
            gl.glTexCoord2d( 1, 0 );
            gl.glVertex3d( 2, 2, 0 );
        }
        gl.glEnd();
        gl.glEnable( GL.GL_LIGHTING );

        texture.disable();

    }
}
