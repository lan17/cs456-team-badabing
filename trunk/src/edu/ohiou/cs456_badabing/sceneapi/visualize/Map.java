package edu.ohiou.cs456_badabing.sceneapi.visualize;

import edu.ohiou.cs456_badabing.sceneapi.basic.ANode;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.*;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import edu.ohiou.cs456_badabing.sceneapi.*;

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
public class Map extends ANode
{
    /**
    * file name of the map texture.
    */
    public String district_texture_name = "\\data\\district_map.jpg";

    public String deer_managment_regions_texture_name = "\\data\\deer_management_regions.jpg";

    public String plain_texture_name = "\\data\\ohio.jpg";

    public ModelNode counties= new ModelNode( new ModelFile( System.getProperty( "user.dir" ) + "\\data\\ohio.obj" ) );

    public String selection_texture_name = "\\data\\district_selection_map.png";


    private boolean show_pie_charts = true;
    private boolean show_spheres = true;

    public ArrayList< PieChart > piez = new ArrayList<PieChart>();
    public ArrayList< Sphere > spherez = new ArrayList<Sphere>();

    public HashMap< String, ANode > string2node = new HashMap<String,ANode>();
    public HashMap< String, PieChart > string2pie = new HashMap< String,PieChart >();
    public HashMap< String, Sphere > string2sphere = new HashMap<String,Sphere>();

    private static double horizontal_offset = 2f ;

    /**
     * texture to use for the map.
     */
    public Texture texture;

    private Texture prev_texture;

    /**
     * Default constructor.
     */
    public Map()
    {
        ANode lol = new ANode();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + district_texture_name );
        TextureManager.getTexture( System.getProperty( "user.dir" ) + selection_texture_name );
        TextureManager.getTexture( System.getProperty( "user.dir" ) + deer_managment_regions_texture_name );
        TextureManager.getTexture( System.getProperty( "user.dir" ) + this.plain_texture_name );
        for( MaterialGroupNode group : counties.mtl_nodes )
        {
            if( ! group.name.equals( "Default" ) )
            {
                ANode containa =  new ANode();
                string2node.put( group.name, containa );

                PieChart pie = new PieChart();
                Sphere sphere =  new Sphere();
                sphere.name = group.name;
                sphere.translation.set( -horizontal_offset, 0, 0 );
                pie.translation.set( horizontal_offset, 0, 0 );
                spherez.add( sphere );
                string2sphere.put( group.name, sphere );
                piez.add( pie );
                string2pie.put( group.name, pie );
                pie.name = group.name;
                containa.scale.set(.05, .05, .05);
                containa.translation.set(group.getCenter());
                containa.translation.y = -.1;
                //pie.thickness = .1;
                pie.rotation.set(1, 0, 0);
                pie.rotation_angle = 90f;

                containa.addChild( pie );
                containa.addChild( sphere );
                lol.addChild(containa);
            }
        }
        lol.rotation.set( 1, 0, 0 );
        lol.rotation_angle = 90;
        lol.translation.set( 0, 0, .25 );
        addChild( lol );
        counties.scale.set( 1, 1, -1 );
    }

    public void useSelectionMap( GLAutoDrawable gl )
    {
        gl.getContext().makeCurrent();
        prev_texture = texture;
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + selection_texture_name );
    }

    public void useDistrictMap( GLAutoDrawable gl )
    {
        gl.getContext().makeCurrent();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + district_texture_name );
    }

    public void useDeerManagmentMap( GLAutoDrawable gl )
    {
        gl.getContext().makeCurrent();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + deer_managment_regions_texture_name  );
    }

    public void usePlainMap( GLAutoDrawable gl )
    {
        gl.getContext().makeCurrent();
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + this.plain_texture_name  );
    }

    public void usePreviousSelectionMap( GLAutoDrawable gl )
    {
        gl.getContext().makeCurrent();
        texture = prev_texture;
    }

    public boolean isShowPies()
    {
        return show_pie_charts;
    }

    public boolean isShowSpheres()
    {
        return show_spheres;
    }



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
                p.translation.set( 0, 0, 0);
            }
        }
        if( f == true )
        {
            for( PieChart p : piez )
            {
                p.translation.set( horizontal_offset, 0, 0 );
            }
        }

    }

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
                    s.translation.set( 0, 0, 0 );
                }
            }
            if( f == true )
            {
                ((ANode)kid).show = true;

                for( Sphere s : spherez )
                {
                    s.translation.set( -horizontal_offset, 0, 0 );
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
