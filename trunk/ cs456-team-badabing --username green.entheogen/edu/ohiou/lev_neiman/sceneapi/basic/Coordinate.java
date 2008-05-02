package edu.ohiou.lev_neiman.sceneapi.basic;

import java.io.*;

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
public class Coordinate implements Serializable
{
    private double x,y,z;

    public Coordinate()
    {
        set( 0, 0, 0 );
    }

    public Coordinate( double x, double y, double z )
    {
        set( x, y, z );
    }

    public void setX( double x ) { this.x = x; }
    public void setY( double y ) { this.y = y; }
    public void setZ( double z ) { this.z = z; }

    public void add( Coordinate b )
    {
        this.x += b.x;
        this.y += b.y;
        this.z += b.z;
    }

    public double x( ){ return x; }
    public double y( ){ return y; }
    public double z( ){ return z; }

    public void multiply( Coordinate b )
    {
        this.x *= b.x;
        this.y *= b.y;
        this.z *= b.z;
    }

    public double dot( Coordinate b )
    {
        double ret = 0;
        ret += this.x * b.x;
        ret += this.y * b.y;
        ret += this.z * b.z;
        return ret;
    }

    public void set( Coordinate b )
    {
        this.x = b.x;
        this.y = b.y;
        this.z = b.z;
    }

    public void set( double x, double y, double z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
