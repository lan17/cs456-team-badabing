package edu.ohiou.lev_neiman.sceneapi.basic;

import javax.media.opengl.*;
import java.util.*;
import java.lang.*;
import edu.ohiou.lev_neiman.sceneapi.*;

/**
 * <p>Title: ANode</p>
 *
 * <p>Description: Super class for all other nodes in the tree.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ANode
{
    static public void renderTree( GLAutoDrawable contex, ANode root )
    {
        final GL gl = contex.getGL();
        if( root.pushPop( ) ) gl.glPushMatrix();
        root.render( contex );

        if( root.children.size() == 0 )
        {
            if( root.pushPop() ) gl.glPopMatrix();
            return;
        }
        for( Object child : root.children ){ renderTree( contex, ((ANode)child) );}
        if( root.pushPop( ) ) gl.glPopMatrix();
    }

    static public int countNodes( ANode root )
    {
        return countNodes( root, 0 );
    }

    static private int countNodes( ANode root, int count )
    {
        int c = count + 1;
        if( root.children.size() == 0 ) { return c; }
        for( Object n : root.children )
        {
            c += countNodes( (ANode)n, count);
        }
        return c;
    }

    public Coordinate translation;
    public Coordinate scale;
    public Coordinate rotation;
    public float rotation_angle;

    protected static final int no_disp_list = -1;
    protected int display_list = no_disp_list;

    protected boolean ppop;

    /**
     * prbl should make this private later on if i still use it.
     */
    public String name = "no name";

    // tree info.
    public LinkedList children;
    public ANode parent;

    private void init( )
    {
        translation = new Coordinate( );
        scale = new Coordinate( 1, 1, 1 );
        rotation = new Coordinate( );
        rotation_angle = 0;
        ppop = true;
        children = new LinkedList<ANode>();
    }

    public boolean pushPop( ){ return ppop; }

    public void setPushPop( boolean a ){ ppop = a; }

    public void addChild( ANode child )
    {
        children.add( child );
        child.setParent( this );
    }

    public void setParent( ANode parent ){ this.parent = parent; }

    public ANode()
    {
        init( );
    }
    public ANode( String name )
    {
        init( );
        this.name = name;

    }

    public ANode( int list )
    {
        init( );
        display_list = list;
    }

    public void render( GLAutoDrawable g )
    {
        final GL gl = g.getGL();
        gl.glTranslated( translation.x(), translation.y(), translation.z() );
        gl.glScaled( scale.x( ), scale.y( ), scale.z( ) );

        if( display_list != no_disp_list ) gl.glCallList( display_list );
        //else System.out.println( "NO DLIST" );
        //System.out.println( name );
    }

    public static Coordinate getNormal( Coordinate a, Coordinate  b, Coordinate  c )
    {
        Coordinate tmp = new Coordinate( -c.x(), -c.y(), -c.z() );
        a.add( tmp );
        b.add( tmp );
        double i = ( a.y() * b.z() - a.z() * b.y() );
        double j = ( a.x() * b.z() - a.z() * b.x() );
        double k = ( a.x() * b.y() - a.y() * b.x() );
        a.add( c );
        b.add( c );
        Coordinate ret = new Coordinate( i, -j, k );
        return ret;
    }



    public static void drawCube( GLAutoDrawable g )
    {
        GL gl = g.getGL();
        gl.glBegin(gl.GL_QUADS);		// Draw The Cube Using quads
        gl.glColor3d(1.0,1.0,1.0);	// Color Blue
        gl.glNormal3f( 0, 1, 0 );
        gl.glVertex3f( 0.5f, 0.5f,-0.5f);	// Top Right Of The Quad (Top)
        gl.glVertex3f(-0.5f, 0.5f,-0.5f);	// Top Left Of The Quad (Top)
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);	// Bottom Left Of The Quad (Top)
        gl.glVertex3f( 0.5f, 0.5f, 0.5f);	// Bottom Right Of The Quad (Top)
        gl.glNormal3f( 0, -1, 0 );
        gl.glColor3d(1.0,1.0,0.0);	// Color Orange
        gl.glVertex3f( 0.5f,-0.5f, 0.5f);	// Top Right Of The Quad (Bottom)
        gl.glVertex3f(-0.5f,-0.5f, 0.5f);	// Top Left Of The Quad (Bottom)
        gl.glVertex3f(-0.5f,-0.5f,-0.5f);	// Bottom Left Of The Quad (Bottom)
        gl.glVertex3f( 0.5f,-0.5f,-0.5f);	// Bottom Right Of The Quad (Bottom)
        gl.glNormal3f( 0, 0, 1 );
        gl.glColor3f(1,0.0f,0.0f);	// Color Red
        gl.glVertex3f( 0.5f, 0.5f, 0.5f);	// Top Right Of The Quad (Front)
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);	// Top Left Of The Quad (Front)
        gl.glVertex3f(-0.5f,-0.5f, 0.5f);	// Bottom Left Of The Quad (Front)
        gl.glVertex3f( 0.5f,-0.5f, 0.5f);	// Bottom Right Of The Quad (Front)
        gl.glNormal3f( 0, 0, -1 );
        gl.glColor3d(1.0,1.0,0.0);	// Color Yellow
        gl.glVertex3f( 0.5f,-0.5f,-0.5f);	// Top Right Of The Quad (Back)
        gl.glVertex3f(-0.5f,-0.5f,-0.5f);	// Top Left Of The Quad (Back)
        gl.glVertex3f(-0.5f, 0.5f,-0.5f);	// Bottom Left Of The Quad (Back)
        gl.glVertex3f( 0.5f, 0.5f,-0.5f);	// Bottom Right Of The Quad (Back)
        gl.glNormal3f( -1, 0, 0 );
        gl.glColor3f(0.0f,0.0f,1);	// Color Blue
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);	// Top Right Of The Quad (Left)
        gl.glVertex3f(-0.5f, 0.5f,-0.5f);	// Top Left Of The Quad (Left)
        gl.glVertex3f(-0.5f,-0.5f,-0.5f);	// Bottom Left Of The Quad (Left)
        gl.glVertex3f(-0.5f,-0.5f, 0.5f);	// Bottom Right Of The Quad (Left)
        gl.glNormal3f( 1, 0, 0 );
        gl.glColor3f(1,0.0f,1);	// Color Violet
        gl.glVertex3f( 0.5f, 0.5f,-0.5f);	// Top Right Of The Quad (Right)
        gl.glVertex3f( 0.5f, 0.5f, 0.5f);	// Top Left Of The Quad (Right)
        gl.glVertex3f( 0.5f,-0.5f, 0.5f);	// Bottom Left Of The Quad (Right)
        gl.glVertex3f( 0.5f,-0.5f,-0.5f);	// Bottom Right Of The Quad (Right)
        gl.glEnd();			// End Drawing The Cube
    }


}
