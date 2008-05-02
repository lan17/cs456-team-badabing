package edu.ohiou.lev_neiman.sceneapi.basic;



import java.util.*;
import javax.media.opengl.*;

/**
 * <p>Title: JOGL stuff</p>
 *
 * <p>Description: Experimentation with JOGL</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class MtlNode extends ANode
{
    private Vector< Triangle > faces = null;

    public MtlNode( Vector< Triangle > mtl_group )
    {
        super( );
        this.faces = mtl_group;
    }

    public void render( GLAutoDrawable gld )
    {
        super.render( gld );
        GL gl = gld.getGL();
        String cur_mtl = faces.get( 0 ).material_name;
        try
        {
                faces.get(0).material.apply(gld);
                //System.out.println( "applied - " + faces.get( 0 ).material.name );
        }
        catch( java.lang.NullPointerException ne )
        {
            System.out.println( "Couldn't find " + faces.get( 0 ).material_name );
        }
        if( faces.get( 0 ).material.texture != null )
        {
            for( Triangle triangle : faces )
            {
                gl.glBegin(gl.GL_POLYGON);
                gl.glNormal3d(triangle.normal.x(), triangle.normal.y(),
                              triangle.normal.z());
                gl.glTexCoord2d(triangle.ta.x(), triangle.ta.y());
                gl.glVertex3d(triangle.a.x(), triangle.a.y(), triangle.a.z());
                gl.glTexCoord2d(triangle.tb.x(), triangle.tb.y());
                gl.glVertex3d(triangle.b.x(), triangle.b.y(), triangle.b.z());
                gl.glTexCoord2d(triangle.tc.x(), triangle.tc.y());
                gl.glVertex3d(triangle.c.x(), triangle.c.y(), triangle.c.z());
                gl.glEnd();

            }
        }
        else
        {
            for( Triangle triangle : faces )
            {
                gl.glBegin(gl.GL_POLYGON);
                gl.glNormal3d(triangle.normal.x(), triangle.normal.y(),
                              triangle.normal.z());
                //gl.glTexCoord2d(triangle.ta.x(), triangle.ta.y());
                gl.glVertex3d(triangle.a.x(), triangle.a.y(), triangle.a.z());
                //gl.glTexCoord2d(triangle.tb.x(), triangle.tb.y());
                gl.glVertex3d(triangle.b.x(), triangle.b.y(), triangle.b.z());
                //gl.glTexCoord2d(triangle.tc.x(), triangle.tc.y());
                gl.glVertex3d(triangle.c.x(), triangle.c.y(), triangle.c.z());
                gl.glEnd();

            }
        }
    }
}
