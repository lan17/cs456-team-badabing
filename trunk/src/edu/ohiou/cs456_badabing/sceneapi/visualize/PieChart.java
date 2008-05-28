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
    public synchronized Double put( String name, Double share )
    {
        synchronized( members )
        {
            return members.put(name, share);
        }
    }

    public synchronized Double get( String a )
    {
        synchronized( members )
        {
            return members.get(a);
        }
    }

    /**
     * value of PI = 3.14159265;
     */
    public static final double PI = 3.14159265;

    public synchronized void clear()
    {
        synchronized( members )
        {
            members = null;
            members = new HashMap<String, Double>();
        }
    }

    /**
     * Members of this pie chart.
     */
    private HashMap< String, Double > members = new HashMap<String,Double>( );

    /**
     * Colors to use for different members.
     */
    public java.awt.Color [] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.pink, Color.GRAY };

    /**
     * depth of the chart as a ratio between its radius.
     */
    private double thickness = .5f;

    /**
     * radius of the chart.
     */
    private double radius = 1f;

    /**
     * controls the detail of the chart.  the lower it is the more triangles are drawn.
     */
    private double detail = .1f;

    private ArrayList< Triangle > mesh = null;

    private void buildMesh()
    {
        for( double i = 0f; i < 2 * PI; i += detail )
        {
            Coordinate a = new Coordinate(0f, 0f, thickness);
            Coordinate b = new Coordinate(Math.cos(i) * radius, Math.sin(i) * radius, thickness);
            Coordinate c = new Coordinate(Math.cos(i + detail) * radius, Math.sin(i + detail) * radius, thickness);
            Coordinate depth = new Coordinate(0, 0, -thickness);
            Coordinate ar = Coordinate.add(a, Coordinate.multiply(depth, 2));
            Coordinate br = Coordinate.add(b, Coordinate.multiply(depth, 2));
            Coordinate cr = Coordinate.add(c, Coordinate.multiply(depth, 2));
            Coordinate normal = new Coordinate(0, 0f, 1);
            Coordinate normalr = new Coordinate(0f, 0f, -1f);
            Coordinate normaln = new Coordinate(Math.cos(i), Math.sin(i), 0);

            Triangle front = new Triangle();
            Triangle back = new Triangle();
            front.a = a;
            front.b = b;
            front.c = c;
            front.a_normal = normal;
            front.b_normal = normaln;
            back.a = ar;
            back.b = br;
            back.c = cr;
            back.a_normal = normalr;


        }
    }

    /**
     * renders the chart.
     * @param panel GLAutoDrawable
     */
    public synchronized void render( GLAutoDrawable panel )
    {
//        System.out.println( "Displaying pie chart" );
        super.render( panel );
        if( show )
        {
            mtl.apply(panel);
            GL gl = panel.getGL();
            double angle = 0f;
            int num = members.size();
            int color_count = 0;
            for (Object n : members.keySet().toArray())
            {
                //System.out.println( colors[color_count].getBlue() );
                gl.glColor3d(((double) colors[color_count].getRed()) / 255f,
                             colors[color_count].getGreen() / 255f,
                             colors[color_count].getBlue() / 255f);
                color_count++;
                if (color_count == colors.length) color_count = 0;
                //System.out.println( (String)n);
                double size = 1;
                try
                {
                    size = get((String) n);

                }
                catch( NullPointerException nexception )
                {
                    System.out.println( "OMG EXCEPTION!" + (String)n );
                }
                for (double i = 0; i < 2f * PI * size; i += detail)
                {
                    Coordinate a = new Coordinate(0f, 0f, thickness);
                    Coordinate b = new Coordinate(Math.cos(i + angle) * radius,
                                                  Math.sin(i + angle) * radius,
                                                  thickness);
                    Coordinate c = new Coordinate(Math.cos(i + angle + detail) *
                                                  radius,
                                                  Math.sin(i + angle + detail) *
                                                  radius, thickness);
                    Coordinate depth = new Coordinate(0, 0, -thickness);
                    Coordinate ar = Coordinate.add(a, Coordinate.multiply(depth, 2));
                    Coordinate br = Coordinate.add(b, Coordinate.multiply(depth, 2));
                    Coordinate cr = Coordinate.add(c, Coordinate.multiply(depth, 2));
                    Coordinate normal = new Coordinate(0, 0f, 1);
                    Coordinate normalr = new Coordinate(0f, 0f, -1f);
                    gl.glBegin(gl.GL_TRIANGLES);
                    {
                        gl.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
                        gl.glVertex3d(a.getX(), a.getY(), a.getZ());
                        gl.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
                        gl.glVertex3d(b.getX(), b.getY(), b.getZ());
                        gl.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
                        gl.glVertex3d(c.getX(), c.getY(), c.getZ());
                    }
                    gl.glEnd();

                    gl.glBegin(gl.GL_TRIANGLES);
                    {
                        gl.glNormal3d(normalr.getX(), normalr.getY(), normalr.getZ());
                        gl.glVertex3d(ar.getX(), ar.getY(), ar.getZ());
                        gl.glNormal3d(normalr.getX(), normalr.getY(), normalr.getZ());
                        gl.glVertex3d(br.getX(), br.getY(), br.getZ());
                        gl.glNormal3d(normalr.getX(), normalr.getY(), normalr.getZ());
                        gl.glVertex3d(cr.getX(), cr.getY(), cr.getZ());

                    }
                    gl.glEnd();

                    gl.glBegin(gl.GL_TRIANGLES);
                    {
                        Coordinate norm1 = new Coordinate(Math.cos(i),
                                Math.sin(i), 0);
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(b.getX(), b.getY(), b.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(br.getX(), br.getY(), br.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(c.getX(), c.getY(), c.getZ());
                    }
                    gl.glEnd();

                    gl.glBegin(gl.GL_TRIANGLES);
                    {
                        Coordinate norm1 = new Coordinate(Math.cos(i),
                                Math.sin(i), 0);
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(cr.getX(), cr.getY(), cr.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(br.getX(), br.getY(), br.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(c.getX(), c.getY(), c.getZ());
                    }
                    gl.glEnd();

                }
                angle += 2f * PI * size;

            }
        }
    }
}
