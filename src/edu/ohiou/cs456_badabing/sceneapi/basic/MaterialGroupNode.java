package edu.ohiou.cs456_badabing.sceneapi.basic;



import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

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
            if( xMax < triangle.a.getX() ) xMax = triangle.a.getX();
            if( yMax < triangle.a.getY() ) yMax = triangle.a.getY();
            if( zMax < triangle.a.getZ() ) zMax = triangle.a.getZ();
            if( xMin > triangle.a.getX() ) xMin = triangle.a.getX();
            if( yMin > triangle.a.getY() ) yMin = triangle.a.getY();
            if( zMin > triangle.a.getZ() ) zMin = triangle.a.getZ();

            if( xMax < triangle.b.getX() ) xMax = triangle.b.getX();
            if( yMax < triangle.b.getY() ) yMax = triangle.b.getY();
            if( zMax < triangle.b.getZ() ) zMax = triangle.b.getZ();
            if( xMin > triangle.b.getX() ) xMin = triangle.b.getX();
            if( yMin > triangle.b.getY() ) yMin = triangle.b.getY();
            if( zMin > triangle.b.getZ() ) zMin = triangle.b.getZ();

            if( xMax < triangle.c.getX() ) xMax = triangle.c.getX();
            if( yMax < triangle.c.getY() ) yMax = triangle.c.getY();
            if( zMax < triangle.c.getZ() ) zMax = triangle.c.getZ();
            if( xMin > triangle.c.getX() ) xMin = triangle.c.getX();
            if( yMin > triangle.c.getY() ) yMin = triangle.c.getY();
            if( zMin > triangle.c.getZ() ) zMin = triangle.c.getZ();

            //triangle.a.print();
            //triangle.b.print();
            //triangle.c.print();
        }
        ret.set( ( xMax + xMin ) / 2, ( yMax + yMin ) / 2, ( zMax + zMin ) / 2 );
        //System.out.println( "============" );
        //ret.print();
        //System.out.println( "============" );
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
                gl.glNormal3d(triangle.normal.getX(), triangle.normal.getY(),
                              triangle.normal.getZ());
                gl.glTexCoord2d(triangle.ta.getX(), triangle.ta.getY());
                gl.glVertex3d(triangle.a.getX(), triangle.a.getY(), triangle.a.getZ());
                gl.glTexCoord2d(triangle.tb.getX(), triangle.tb.getY());
                gl.glVertex3d(triangle.b.getX(), triangle.b.getY(), triangle.b.getZ());
                gl.glTexCoord2d(triangle.tc.getX(), triangle.tc.getY());
                gl.glVertex3d(triangle.c.getX(), triangle.c.getY(), triangle.c.getZ());
                gl.glEnd();

            }
        }
        else
        {
            for( Triangle triangle : faces )
            {
                gl.glBegin(gl.GL_POLYGON);
                gl.glNormal3d(triangle.normal.getX(), triangle.normal.getY(),
                              triangle.normal.getZ());
                //gl.glTexCoord2d(triangle.ta.x(), triangle.ta.y());
                gl.glVertex3d(triangle.a.getX(), triangle.a.getY(), triangle.a.getZ());
                //gl.glTexCoord2d(triangle.tb.x(), triangle.tb.y());
                gl.glVertex3d(triangle.b.getX(), triangle.b.getY(), triangle.b.getZ());
                //gl.glTexCoord2d(triangle.tc.x(), triangle.tc.y());
                gl.glVertex3d(triangle.c.getX(), triangle.c.getY(), triangle.c.getZ());
                gl.glEnd();

            }
        }
    }
}
