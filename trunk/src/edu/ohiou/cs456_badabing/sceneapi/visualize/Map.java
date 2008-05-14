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

    public ArrayList pie_charts = new ArrayList<PieChart>();

    private boolean show_pie_charts = true;

    public HashMap< String, PieChart > pie_hash = new HashMap<String,PieChart>();


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
                PieChart pie = new PieChart();
                pie_hash.put( group.name, pie );
                pie.name = group.name;
                pie_charts.add(pie);
                pie.scale.set(.05, .05, .05);
                pie.translation.set(group.getCenter());
                pie.translation.y = -.1;
                pie.thickness = .1;
                pie.rotation.set(1, 0, 0);
                pie.rotation_angle = 90f;
                lol.addChild(pie);
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

    public void setShowPie( boolean f )
    {
        show_pie_charts = f;
        for( Object pie : pie_charts )
        {
            ((PieChart)pie).show = f;
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
