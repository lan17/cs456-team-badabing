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
    public String texture_name = "\\data\\ohio.jpg";

    public ModelNode counties= new ModelNode( new ModelFile( System.getProperty( "user.dir" ) + "\\data\\ohio.obj" ) );

    public ArrayList pie_charts = new ArrayList<PieChart>();

    private boolean show_pie_charts = true;


    /**
     * texture to use for the map.
     */
    public Texture texture;

    /**
     * Default constructor.
     */
    public Map()
    {
        texture = TextureManager.getTexture( System.getProperty( "user.dir" ) + texture_name );
        for( MaterialGroupNode group : counties.mtl_nodes )
        {
            if( ! group.name.equals( "Default" ) )
            {
                PieChart pie = new PieChart();
                pie_charts.add(pie);
                pie.scale.set(.05, .05, .05);
                pie.translation.set(group.getCenter());
                pie.translation.y = -.1;
                pie.thickness = .1;
                pie.rotation.set(1, 0, 0);
                pie.rotation_angle = 90f;
                counties.addChild(pie);
            }
        }
        counties.rotation.set( 1, 0, 0 );
        counties.rotation_angle = 90;
        counties.translation.set( 0, 0, -.01);
        addChild( counties );
        counties.scale.set( 1, 1, -1 );
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

    }
}
