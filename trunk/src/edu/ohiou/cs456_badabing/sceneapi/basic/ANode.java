package edu.ohiou.cs456_badabing.sceneapi.basic;

import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 * <p>Super class for all other nodes in the tree.</p>
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

     /**
     * how far away is this node from its parent?
     */
    protected Coordinate translation;

    /**
     * returns translation
     * @return Coordinate
     */
    public synchronized Coordinate translation()
    {
        return translation;
    }

    /**
     * how big is this node compared to its parent?  1.0 is same size.
     */
    protected Coordinate scale;

    public synchronized Coordinate scale()
    {
        return scale;
    }

    /**
     * what is the vector about which this node is rotated, relative to its parent?
     */
    protected Coordinate rotation;

    public synchronized Coordinate rotation()
    {
        return rotation;
    }

    /**
     * what is the angle of rotation about that vector?
     */
    public float rotation_angle;

    /**
     * if display_list == -1 then we know it can't refer to valid OpenGL display list, since they cannot be negative.
     */
    protected static final int no_disp_list = -1;

    /**
     * if this does not equal to -1 then it refers to some Display List in memory.
     */
    protected int display_list = no_disp_list;

    /**
     * if this is true, then modelview matrix is not pushed and poped around rendering this node.
     */
    protected boolean ppop;

    /**
     * prbl should make this private later on if i still use it.
     */
    protected String name = "no name";

    public synchronized String getName( )
    {
        return name;
    }

    public synchronized void setName( String name )
    {
        this.name = name;
    }

    // tree info.
    /**
     * References to the children nodes.  Uses Java's LinkedList data structure.
     */
    protected LinkedList children;

    /**
     * reference to the parent node.
     */
    protected ANode parent; /**
    *
    */
   public boolean show = true;

    /**
     * initializes member variables to their default values.
     */
    private void init( )
    {
        translation = new Coordinate( );
        scale = new Coordinate( 1, 1, 1 );
        rotation = new Coordinate( );
        rotation_angle = 0;
        ppop = true;
        children = new LinkedList<ANode>();
    }

    /**
     * default constructor
     */
    public ANode()
    {
        init( );
    }

    /**
     * constructor
     * @param name String - name for this node.
     */
    public ANode( String name )
    {
        init( );
        this.name = name;
    }

    /**
     * constructor
     * @param list int - display list that this node will call.
     */
    public ANode( int list )
    {
        init( );
        display_list = list;
    }

    /**
     * add child to this node.
     * @param child ANode
     */
    public synchronized void addChild( ANode child )
    {
        children.add( child );
        child.setParent( this );
    }

    /**
     * set parent on this node.
     * @param parent ANode
     */
    public synchronized void setParent( ANode parent ){ this.parent = parent; }

    /**
     * This method should be typically called from render in all derived classes.
     * This one just modifies the current ModelView matrix by this node's offset, rotation and scale.
     * @param g GLAutoDrawable
     */
    public void render( GLAutoDrawable g )
    {
        GL gl = g.getGL();
        gl.glTranslated( translation.getX(), translation.getY(), translation.getZ() ); // modify current model view matrix with this translation.
        gl.glScaled( scale.getX( ), scale.getY( ), scale.getZ( ) ); // scale relative to the parent.
        gl.glRotated( rotation_angle, rotation.getX(), rotation.getY(), rotation.getZ() ); // rotate around rotation by rotation_angle
        if( display_list != no_disp_list && show ) gl.glCallList( display_list ); // if needed, call display list.
    }

    public static void hideTree( ANode root )
    {
        if( root == null ) return;
        root.show = false;
        for( Object kid : root.children )
        {
            hideTree( (ANode)kid );
        }
    }

    public static void showTree( ANode root )
    {
        if( root == null ) return;
        root.show = true;
        for( Object kid : root.children )
        {
            showTree( (ANode)kid );
        }

    }

    /**
    * This static function renders the tree with root as the root.
    * @param contex GLAutoDrawable
    * @param root ANode
    */
    static public void renderTree( GLAutoDrawable contex, ANode root )
    {
        GL gl = contex.getGL();
        if( root.pushPop( ) ) gl.glPushMatrix(); // push current model view matrix on the stack, if pushPop is true
        root.render( contex ); // render current node.  This will potentialy affect modelview matrix.
        for( Object child : root.children ){ renderTree( contex, ((ANode)child) );} // render the children.  Notice that modelview matrix affecting these children is modified by previous call to root.render()
        if( root.pushPop( ) ) gl.glPopMatrix(); // // pop current model view matrix from the stack, if pushPop is true
    }

    /**
     * counts the number of nodes in the tree with root as root.
     * @param root ANode - root to the tree to count nodes of.
     * @return int - number of nodes in root.
     */
    static public int countNodes( ANode root )
    {
        return countNodes( root, 0 );
    }

    /**
     * counts the number of nodes in the tree with root as root.
     * @param root ANode - root to the tree to count nodes of.
     * @param count int - current count.
     * @return int - number of nodes in root.
     */
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



    public boolean pushPop( ){ return ppop; }

    public void setPushPop( boolean a ){ ppop = a; }



    /**
     * computes a normal between (a-c)x(b-c)
     * @param a Coordinate
     * @param b Coordinate
     * @param c Coordinate
     * @return Coordinate
     */
    public static Coordinate getNormal( Coordinate a, Coordinate  b, Coordinate  c )
    {
        Coordinate tmp = new Coordinate( -c.getX(), -c.getY(), -c.getZ() );
        a.add( tmp );
        b.add( tmp );
        double i = ( a.getY() * b.getZ() - a.getZ() * b.getY() );
        double j = ( a.getX() * b.getZ() - a.getZ() * b.getX() );
        double k = ( a.getX() * b.getY() - a.getY() * b.getX() );
        a.add( c );
        b.add( c );
        Coordinate ret = new Coordinate( i, -j, k );
        return ret;
    }


    /**
     * renders a simple cube.
     * @param g GLAutoDrawable
     */
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
