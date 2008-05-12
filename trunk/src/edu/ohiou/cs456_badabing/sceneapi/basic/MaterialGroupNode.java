package edu.ohiou.cs456_badabing.sceneapi.basic;



import java.util.*;
import javax.media.opengl.*;

/**
 * <p>Node that renders a material group.</p>
 *
 * <p>Description: node that renders a material group.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class MaterialGroupNode extends ANode
{
    /**
    * Triangles belonging to this group.
    */
    private Vector< Triangle > faces = null;

    /**
     * default constructor
     * @param mtl_group Vector - triangles that belong to this group.
     */
    public MaterialGroupNode( Vector< Triangle > mtl_group )
    {
        super( );
        this.faces = mtl_group;
    }

    public Coordinate getCenter( )
    {
        Coordinate ret = new Coordinate();
        double xMax, yMax, zMax, xMin, yMin, zMin;
        xMax = yMax = zMax = -1 * Double.MAX_VALUE;
        xMin = yMin = zMin = Double.MAX_VALUE;
        for( Triangle triangle : faces )
        {
            if( xMax < triangle.a.x() ) xMax = triangle.a.x();
            if( yMax < triangle.a.y() ) yMax = triangle.a.y();
            if( zMax < triangle.a.z() ) zMax = triangle.a.z();
            if( xMin > triangle.a.x() ) xMin = triangle.a.x();
            if( yMin > triangle.a.y() ) yMin = triangle.a.y();
            if( zMin > triangle.a.z() ) zMin = triangle.a.z();

            if( xMax < triangle.b.x() ) xMax = triangle.b.x();
            if( yMax < triangle.b.y() ) yMax = triangle.b.y();
            if( zMax < triangle.b.z() ) zMax = triangle.b.z();
            if( xMin > triangle.b.x() ) xMin = triangle.b.x();
            if( yMin > triangle.b.y() ) yMin = triangle.b.y();
            if( zMin > triangle.b.z() ) zMin = triangle.b.z();

            if( xMax < triangle.c.x() ) xMax = triangle.c.x();
            if( yMax < triangle.c.y() ) yMax = triangle.c.y();
            if( zMax < triangle.c.z() ) zMax = triangle.c.z();
            if( xMin > triangle.c.x() ) xMin = triangle.c.x();
            if( yMin > triangle.c.y() ) yMin = triangle.c.y();
            if( zMin > triangle.c.z() ) zMin = triangle.c.z();

            triangle.a.print();
            triangle.b.print();
            triangle.c.print();
        }
        ret.set( ( xMax + xMin ) / 2, ( yMax + yMin ) / 2, ( zMax + zMin ) / 2 );
        System.out.println( "============" );
        ret.print();
        System.out.println( "============" );
        return ret;
    }

    public Coordinate centrify()
    {
        Coordinate center = getCenter();
        //center.multiply( -1f );
        for( Triangle t : faces )
        {
            t.a.subtract( center );
            t.b.subtract( center );
            t.c.subtract( center );
        }
        return center;
    }

    /**
     * Overloaded render method.  This method actually renders the material group, and maps the texture, applies material properties, etc.
     * @param gld GLAutoDrawable
     */
    public void render( GLAutoDrawable gld )
    {
        super.render( gld ); // call super method, so that spacial information is applied.
        GL gl = gld.getGL();
        String cur_mtl = faces.get( 0 ).material_name;
        try
        {
                faces.get(0).material.apply(gld);
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
