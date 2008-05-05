package edu.ohiou.cs456_badabing.sceneapi.visualize;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import javax.media.opengl.*;
import java.util.*;
import java.math.*;
import java.awt.*;

/**
 * <p>Node that renders a Pie Chart</p>
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
public class PieChart extends ANode
{
    /**
    * material properties for the pie chart.
    */
    private Material mtl = new Material("default");

    /**
     * default constructor.
     */
    public PieChart()
    {
        members.put( "Blue", new Double( .6f) );
        members.put( "Red", new Double( .4f ) );
    }

    /**
     * Put new member into pie chart.
     * @param name String
     * @param share Double
     * @return Double
     */
    public Double put( String name, Double share )
    {
        return members.put( name, share );
    }

    /**
     * value of PI = 3.14159265;
     */
    public static final double PI = 3.14159265;

    /**
     * Members of this pie chart.
     */
    public TreeMap< String, Double > members = new TreeMap<String,Double>( );

    /**
     * Colors to use for different members.
     */
    public java.awt.Color [] colors = { Color.BLUE, Color.RED, Color.BLUE, };

    /**
     * depth of the chart as a ratio between its radius.
     */
    public double thickness = .5f;

    /**
     * radius of the chart.
     */
    public double radius = 1f;

    /**
     * controls the detail of the chart.  the lower it is the more triangles are drawn.
     */
    public double detail = .001f;

    /**
     * renders the chart.
     * @param panel GLAutoDrawable
     */
    public synchronized void render( GLAutoDrawable panel )
    {
//        System.out.println( "Displaying pie chart" );
        super.render( panel );
        mtl.apply( panel );
        GL gl = panel.getGL();
        double angle = 0f;
        int num = members.size();
        int color_count = 0;
        for( Object n : members.keySet().toArray() )
        {
            //System.out.println( colors[color_count].getBlue() );
            gl.glColor3d( ((double)colors[color_count].getRed() ) / 255f, colors[color_count].getGreen() / 255f, colors[color_count].getBlue() / 255f );
            color_count++;
            if( color_count == colors.length ) color_count = 0;
            //System.out.println( (String)n);
            double size = members.get( (String) n );
            for( double i = 0; i < 2f*PI*size; i += detail )
            {
                Coordinate a = new Coordinate( 0f, 0f, thickness );
                Coordinate b = new Coordinate( Math.cos( i + angle ) * radius , Math.sin( i + angle ) * radius , thickness );
                Coordinate c = new Coordinate( Math.cos( i + angle + detail ) * radius, Math.sin( i + angle + detail ) * radius, thickness );
                Coordinate depth = new Coordinate( 0, 0, -thickness );
                Coordinate ar = Coordinate.add( a, Coordinate.multiply(depth, 2)  );
                Coordinate br = Coordinate.add( b, Coordinate.multiply(depth, 2) );
                Coordinate cr = Coordinate.add( c, Coordinate.multiply(depth, 2) );
                Coordinate normal = new Coordinate( 0, 0f, 1 );
                Coordinate normalr = new Coordinate( 0f, 0f, -1f);
                gl.glBegin( gl.GL_TRIANGLES );
                {
                    gl.glNormal3d( normal.x(), normal.y(), normal.z() );
                    gl.glVertex3d( a.x(), a.y(), a.z() );
                    gl.glNormal3d( normal.x(), normal.y(), normal.z() );
                    gl.glVertex3d( b.x(), b.y(), b.z() );
                    gl.glNormal3d( normal.x(), normal.y(), normal.z() );
                    gl.glVertex3d( c.x(), c.y(), c.z() );
                }
                gl.glEnd();

                gl.glBegin( gl.GL_TRIANGLES );
                {
                    gl.glNormal3d( normalr.x(), normalr.y(), normalr.z() );
                    gl.glVertex3d( ar.x(), ar.y(), ar.z() );
                    gl.glNormal3d( normalr.x(), normalr.y(), normalr.z() );
                    gl.glVertex3d( br.x(), br.y(), br.z() );
                    gl.glNormal3d( normalr.x(), normalr.y(), normalr.z() );
                    gl.glVertex3d( cr.x(), cr.y(), cr.z() );

                }
                gl.glEnd();

                gl.glBegin( gl.GL_TRIANGLES );
                {
                    Coordinate norm1 = new Coordinate( Math.cos( i ), Math.sin( i ), 0 );
                    gl.glNormal3d( norm1.x(), norm1.y(), norm1.z() );
                    gl.glVertex3d( b.x(), b.y(), b.z() );
                    gl.glNormal3d( norm1.x(), norm1.y(), norm1.z() );
                    gl.glVertex3d( br.x(), br.y(), br.z() );
                    gl.glNormal3d( norm1.x(), norm1.y(), norm1.z() );
                    gl.glVertex3d( c.x(), c.y(), c.z() );
                }
                gl.glEnd();

                gl.glBegin( gl.GL_TRIANGLES );
                {
                    Coordinate norm1 = new Coordinate( Math.cos( i ), Math.sin( i ), 0 );
                    gl.glNormal3d( norm1.x(), norm1.y(), norm1.z() );
                    gl.glVertex3d( cr.x(), cr.y(), cr.z() );
                    gl.glNormal3d( norm1.x(), norm1.y(), norm1.z() );
                    gl.glVertex3d( br.x(), br.y(), br.z() );
                    gl.glNormal3d( norm1.x(), norm1.y(), norm1.z() );
                    gl.glVertex3d( c.x(), c.y(), c.z() );
                }
                gl.glEnd();

            }
            angle += 2f*PI*size;

        }
    }
}
