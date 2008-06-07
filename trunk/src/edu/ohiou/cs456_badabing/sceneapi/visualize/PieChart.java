package edu.ohiou.cs456_badabing.sceneapi.visualize;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import java.util.*;
import java.math.*;
import java.awt.*;
import java.nio.*;

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
    private static Material mtl = new Material("default");

    public static void setTransperency( float t )
    {
        mtl.color[3] = t;
    }

    /**
     * default constructor.
     */
    public PieChart()
    {
        put( "Blue", new Double( .6f) );
        put( "Red", new Double( .4f ) );

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
            //System.out.println( "ZOMG" + new Integer( percent.intValue() ).toString());
            slices.add( share );
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
            slices = null;
            members = new HashMap<String, Double>();
            slices = new ArrayList<Double>();
        }
    }

    private ArrayList< Double > slices =  new ArrayList< Double >();

    /**
     * Members of this pie chart.
     */
    private HashMap< String, Double > members = new HashMap<String,Double>( );

    /**
     * Colors to use for different members.
     */
    public static java.awt.Color [] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.pink, Color.GRAY };



    /**
     * depth of the chart as a ratio between its radius.
     */
    private static double thickness = .5f;

    /**
     * radius of the chart.
     */
    private static double radius = 1f;

    /**
     * controls the detail of the chart.  the lower it is the more triangles are drawn.
     */
    private static double detail = .1f;

    private static ArrayList< Triangle > mesh = null;

    private static boolean need_to_build_mesh = true;

    private static double d_size_mesh = 0f;

    private static int vertexCount;    // Vertex Count
    private static FloatBuffer vertices;    // Vertex Data
    private static FloatBuffer normals; // Normals data.
    private static int[] VBOVertices = new int[1];
    private static int[] VBONormals = new int[1];

    private static void buildMesh()
    {
        mesh = null;
        mesh = new ArrayList< Triangle >();

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

            mesh.add( front );
            mesh.add( back );


        }
        Integer size = mesh.size();
        d_size_mesh = size.doubleValue();

        if( VBOsupported )
        {
            vertexCount = mesh.size()  *18;
            vertices = BufferUtil.newFloatBuffer(vertexCount);
            normals = BufferUtil.newFloatBuffer(vertexCount );
            for( int i = 0; i < mesh.size(); i += 2 )
            {
                Triangle front = mesh.get( i );
                Triangle back = mesh.get( i + 1 );
                Triangle sidea = new Triangle();
                Triangle sideb = new Triangle();
                Coordinate normaln = front.b_normal;



                // define sidea, sideb triangles.
                /*
                gl.glBegin(gl.GL_TRIANGLES);
                    {
                        Coordinate norm1 = normaln;
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(cr.getX(), cr.getY(), cr.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(br.getX(), br.getY(), br.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(c.getX(), c.getY(), c.getZ());
                    }
                    gl.glEnd();
                 */

                sidea.a.set( front.b );
                sidea.b.set( back.b );
                sidea.c.set( front.c );

                sideb.a.set( back.c );
                sideb.b.set( back.b );
                sideb.c.set( front.c );


                // put sidea triangle

                vertices.put( (float) sidea.a.getX() );
                vertices.put( (float) sidea.a.getY() );
                vertices.put( (float) sidea.a.getZ() );

                vertices.put( (float) sidea.b.getX() );
                vertices.put( (float) sidea.b.getY() );
                vertices.put( (float) sidea.b.getZ() );

                vertices.put( (float) sidea.c.getX() );
                vertices.put( (float) sidea.c.getY() );
                vertices.put( (float) sidea.c.getZ() );

                for( int j = 0; j < 3; ++j )
                {
                    normals.put( (float) normaln.getX() );
                    normals.put( (float) normaln.getY() );
                    normals.put( (float) normaln.getZ() );
                }

                // put sideb triangle

                vertices.put( (float) sideb.a.getX() );
                vertices.put( (float) sideb.a.getY() );
                vertices.put( (float) sideb.a.getZ() );

                vertices.put( (float) sideb.b.getX() );
                vertices.put( (float) sideb.b.getY() );
                vertices.put( (float) sideb.b.getZ() );

                vertices.put( (float) sideb.c.getX() );
                vertices.put( (float) sideb.c.getY() );
                vertices.put( (float) sideb.c.getZ() );

                for( int j = 0; j < 3; ++j )
                {
                    normals.put( (float) normaln.getX() );
                    normals.put( (float) normaln.getY() );
                    normals.put( (float) normaln.getZ() );
                }



                // put front triangle

                vertices.put( (float) front.a.getX() );
                vertices.put( (float) front.a.getY() );
                vertices.put( (float) front.a.getZ() );

                vertices.put( (float) front.b.getX() );
                vertices.put( (float) front.b.getY() );
                vertices.put( (float) front.b.getZ() );

                vertices.put( (float) front.c.getX() );
                vertices.put( (float) front.c.getY() );
                vertices.put( (float) front.c.getZ() );

                for( int j = 0; j < 3; ++j )
                {
                    normals.put( (float) front.a_normal.getX() );
                    normals.put( (float) front.a_normal.getY() );
                    normals.put( (float) front.a_normal.getZ() );
                }

                // put back triangle

                vertices.put( (float) back.a.getX() );
                vertices.put( (float) back.a.getY() );
                vertices.put( (float) back.a.getZ() );

                vertices.put( (float) back.b.getX() );
                vertices.put( (float) back.b.getY() );
                vertices.put( (float) back.b.getZ() );

                vertices.put( (float) back.c.getX() );
                vertices.put( (float) back.c.getY() );
                vertices.put( (float) back.c.getZ() );

                for( int j = 0; j < 3; ++j )
                {
                    normals.put( (float) back.a_normal.getX() );
                    normals.put( (float) back.a_normal.getY() );
                    normals.put( (float) back.a_normal.getZ() );
                }




            }
            System.out.println( mesh.size() );
            System.out.println( vertices.toString() );
        }

    }

    private static boolean VBOsupported;

    /**
     * renders the chart.
     * @param panel GLAutoDrawable
     */
    public synchronized void render( GLAutoDrawable panel )
    {
        GL gl = panel.getGL();
        if( need_to_build_mesh )
        {
            VBOsupported = gl.isFunctionAvailable("glGenBuffersARB") &&
                gl.isFunctionAvailable("glBindBufferARB") &&
                gl.isFunctionAvailable("glBufferDataARB") &&
                gl.isFunctionAvailable("glDeleteBuffersARB");

            buildMesh();
            if( !VBOsupported )
            {

                gl.glGenBuffersARB(1, VBOVertices, 0);  // Get A Valid Name
                gl.glBindBufferARB(GL.GL_ARRAY_BUFFER_ARB, VBOVertices[0]);  // Bind The Buffer
                // Load The Data
                gl.glBufferDataARB(GL.GL_ARRAY_BUFFER_ARB, vertexCount * BufferUtil.SIZEOF_FLOAT, vertices, GL.GL_STATIC_DRAW_ARB);

                /*
                gl.glGenBuffersARB(1, VBONormals, 0);  // Get A Valid Name
                gl.glBindBufferARB(GL.GL_ARRAY_BUFFER_ARB, VBONormals[0]);  // Bind The Buffer
                // Load The Data
                gl.glBufferDataARB(GL.GL_ARRAY_BUFFER_ARB, vertexCount *
                BufferUtil.SIZEOF_FLOAT, normals, GL.GL_STATIC_DRAW_ARB);
                */
                vertices = null;
                normals = null;




            }
            need_to_build_mesh = false;
        }
//        System.out.println( "Displaying pie chart" );
        super.render( panel );
        if( show )
        {
            mtl.apply(panel);

            double angle = 0f;
            int num = members.size();
            int color_count = -1;
            boolean need_to_change_color = true;
            Double share = new Double( 0 );

            int old_i = 0;
            if( !VBOsupported )
            {

            gl.glEnableClientState(GL.GL_VERTEX_ARRAY);  // Enable Vertex Arrays
            //gl.glEnableClientState( GL.GL_NORMAL_ARRAY );


            // Set Pointers To Our Data
            //gl.glBindBufferARB(GL.GL_ARRAY_BUFFER_ARB, VBONormals[0]);
                   // Set The Vertex Pointer To The Vertex Buffer
                  // gl.glNormalPointer( GL.GL_FLOAT, 0, 0);


                gl.glBindBufferARB(GL.GL_ARRAY_BUFFER_ARB, VBOVertices[0]);
                // Set The Vertex Pointer To The Vertex Buffer
                gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0);





            // Render
            // Draw All Of The Triangles At Once
            gl.glDrawArrays(GL.GL_TRIANGLES, 0, vertexCount );

            // Disable Pointers
            // Disable Vertex Arrays
            gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
            // Disable Texture Coord Arrays
            gl.glDisableClientState(GL.GL_NORMAL_ARRAY );

            }
            if(  VBOsupported )
            {
                for (int i = 0; i < mesh.size(); i += 2)
                {
                    if (need_to_change_color)
                    {
                        // take care of color.
                        color_count++;
                        gl.glColor4d(colors[color_count].getRed() / 255f,
                                     colors[color_count].getGreen() / 255f,
                                     colors[color_count].getBlue() / 255f, mtl.color[3]);

                        need_to_change_color = false;

                        // take care of share.
                        old_i = i;
                        if (color_count >= slices.size())break;
                        share = d_size_mesh * slices.get(color_count);
                        //System.out.println( share );

                    }
                    if (i - old_i > share)
                    {
                        need_to_change_color = true;
                    }
                    Triangle front = mesh.get(i);
                    Triangle back = mesh.get(i + 1);

                    Coordinate a = front.a;
                    Coordinate b = front.b;
                    Coordinate c = front.c;
                    Coordinate ar = back.a;
                    Coordinate br = back.b;
                    Coordinate cr = back.c;
                    Coordinate normalr = back.a_normal;
                    Coordinate normal = front.a_normal;
                    Coordinate normaln = front.b_normal;

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
                        gl.glNormal3d(normalr.getX(), normalr.getY(),
                                      normalr.getZ());
                        gl.glVertex3d(ar.getX(), ar.getY(), ar.getZ());
                        gl.glNormal3d(normalr.getX(), normalr.getY(),
                                      normalr.getZ());
                        gl.glVertex3d(br.getX(), br.getY(), br.getZ());
                        gl.glNormal3d(normalr.getX(), normalr.getY(),
                                      normalr.getZ());
                        gl.glVertex3d(cr.getX(), cr.getY(), cr.getZ());

                    }
                    gl.glEnd();

                    gl.glBegin(gl.GL_TRIANGLES);
                    {
                        Coordinate norm1 = normaln;
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
                        Coordinate norm1 = normaln;
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(cr.getX(), cr.getY(), cr.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(br.getX(), br.getY(), br.getZ());
                        gl.glNormal3d(norm1.getX(), norm1.getY(), norm1.getZ());
                        gl.glVertex3d(c.getX(), c.getY(), c.getZ());
                    }
                    gl.glEnd();

                }
            }

            // Below is old code for rendering pie chart.
            /*
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
            */

        }
    }
}
