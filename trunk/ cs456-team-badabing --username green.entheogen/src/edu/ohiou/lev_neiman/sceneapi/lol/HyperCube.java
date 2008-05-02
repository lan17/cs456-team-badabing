package edu.ohiou.lev_neiman.sceneapi.lol;

import edu.ohiou.lev_neiman.sceneapi.basic.*;

/**
 * <p>Title: JOGL stuff</p>
 *
 * <p>Description: Experimentation with JOGL</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */

import javax.media.opengl.*;

public class HyperCube extends ANode
{
    private float sf;
    private float offset;

    private int cur_disp;

    private int detail = 5;

    private void init( )
    {
        sf = (float).5;
        offset = (float).75;
        cur_disp = DisplayListManager.getList( "cube" );
        display_list = DisplayListManager.getList( "cube" );

    }

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

    public void resetDetail( int detail )
    {
        this.detail = detail;
        cleanUp( );
        makeRCubez( this, detail );
        System.out.println( ANode.countNodes( this ) );
    }

    public int getDetail( ){ return detail; }

    public void setDisplayList( int list )
    {
        super.display_list = list;
        cur_disp = list;
    }

    public void makeRCubez( int n )
    {
        makeRCubez( this, n );
    }

    public void makeRCubez( ANode tree, int n ){  makeRCubez( tree, n, 0 ); }

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
