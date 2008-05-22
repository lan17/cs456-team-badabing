package edu.ohiou.cs456_badabing.sceneapi.lol;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import javax.media.opengl.*;

/**
 * <p>Generates a Tree which represents the HyperCube.</p>
 *
 * <p>Description: Generates a Tree which represents the HyperCube.</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class HyperCube extends ANode
{
    private float sf;
    private float offset;

    private int cur_disp;

    private int detail = 5;

    private void init( )
    {
        sf = .5f;
        offset = (float).75;
        cur_disp = DisplayListManager.getList( "cube" );
        display_list = DisplayListManager.getList( "cube" );

    }

    /**
     *
     * @param g GLAutoDrawable
     */
    public HyperCube( GLAutoDrawable g )
    {
        super( );
        init( );
        makeRCubez( this, detail );

    }

    private void cleanUp( )
    {
        for( Object child : children )
        {
            child = null;
        }
        children.clear();
    }

    /**
     * regenerate tree with new level of recursive detail.
     * @param detail int
     */
    public void resetDetail( int detail )
    {
        this.detail = detail;
        cleanUp( );
        makeRCubez( this, detail );
        System.out.println( ANode.countNodes( this ) );
    }

    /**
     * what is the current recursive detail level.
     * @return int
     */
    public int getDetail( ){ return detail; }

    /**
     * display list that each node will use to render itself.
     * @param list int
     */
    public void setDisplayList( int list )
    {
        super.display_list = list;
        cur_disp = list;
    }

    /**
     * create the HyperCube tree.
     * @param n int
     */
    public void makeRCubez( int n )
    {
        makeRCubez( this, n );
    }

    /**
     * create the HyperCube tree.
     * @param tree ANode
     * @param n int
     */
    public void makeRCubez( ANode tree, int n ){  makeRCubez( tree, n, 0 ); }

    /**
     * create the HyperCube tree.
     * @param tree ANode
     * @param n int
     * @param rec int
     */
    public void makeRCubez( ANode tree, int n, int rec )
    {
         if( rec >= n ) { return; }
         int nrec = rec + 1;
         ANode small_cube = new ANode( cur_disp );
         small_cube.name = Integer.toString( nrec ) + "x1";
         tree.addChild( small_cube );
         small_cube.translation.set( -offset, 0, 0 );
         small_cube.scale.set( sf, sf, sf );
         makeRCubez( small_cube, n, nrec);

         small_cube = new ANode( cur_disp );
         small_cube.name = Integer.toString( nrec ) + "x2";
         tree.addChild( small_cube );
         small_cube.translation.set( offset, 0, 0 );
         small_cube.scale.set( sf, sf, sf );
         makeRCubez( small_cube, n, nrec );

         small_cube = new ANode( cur_disp );
         small_cube.name = Integer.toString( nrec ) + "x3";
         tree.addChild( small_cube );
         small_cube.translation.set( 0, offset, 0 );
         small_cube.scale.set( sf, sf, sf );
         makeRCubez( small_cube, n, nrec );

         small_cube = new ANode( cur_disp );
         small_cube.name = Integer.toString( nrec ) + "x4";
         tree.addChild( small_cube );
         small_cube.translation.set( 0, -offset, 0 );
         small_cube.scale.set( sf, sf, sf );
         makeRCubez( small_cube, n, nrec );

         small_cube = new ANode( cur_disp );
         small_cube.name = Integer.toString( nrec ) + "x5";
         tree.addChild( small_cube );
         small_cube.translation.set( 0, 0, offset );
         small_cube.scale.set( sf, sf, sf );
         makeRCubez( small_cube, n, nrec);

         small_cube = new ANode( cur_disp );
         small_cube.name = Integer.toString( nrec ) + "x6";
         tree.addChild( small_cube );
         small_cube.translation.set( 0, 0, -offset );
         small_cube.scale.set( sf, sf, sf );
         makeRCubez( small_cube, n, nrec );
     }


}
