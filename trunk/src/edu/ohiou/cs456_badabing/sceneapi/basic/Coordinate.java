package edu.ohiou.cs456_badabing.sceneapi.basic;

import java.io.*;

/**
 * <p>Container for 3D coordinate.</p>
 *
 * <p>Description: container for 3D coordinate.</p>
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
    /**
    * x y and z values for this coordinate.
    */
    private double x,y,z;

    /**
     * default constructor
     */
    public Coordinate()
    {
        set( 0, 0, 0 );
    }

    public Coordinate( Coordinate a )
    {
        set( a.x(), a.y(), a.z() );
    }

    /**
     * Constructor with xyz passed to it.
     * @param x double
     * @param y double
     * @param z double
     */
    public Coordinate( double x, double y, double z )
    {
        set( x, y, z );
    }

    /**
     * set x value for this coordinate.
     * @param x double
     */
    public void setX( double x ) { this.x = x; }

    /**
     * set y value for this coordinate.
     * @param y double
     */
    public void setY( double y ) { this.y = y; }

    /**
     * set z value for this coordinate.
     * @param z double
     */
    public void setZ( double z ) { this.z = z; }

    /**
     * add this coordinate and coordinate b.
     * @param b Coordinate
     */
    public void add( Coordinate b )
    {
        this.x += b.x;
        this.y += b.y;
        this.z += b.z;
    }

    /**
     * add a and b and return result as new Coordinate
     * @param a Coordinate
     * @param b Coordinate
     * @return Coordinate
     */
    public static Coordinate add( Coordinate a, Coordinate b )
    {
        Coordinate ret = new Coordinate( a.x + b.x, a.y + b.y, a.z + b.z );
        return ret;
    }

    public static Coordinate multiply( Coordinate a, double b )
    {
        Coordinate ret = new Coordinate( a.x * b, a.y * b, a.z * b );
        return ret;
    }


    /**
     *
     * @param a double
     */
    public void multiply( double a )
    {
        x *= a;
        y *= a;
        y *= a;

    }

    /**
     * return the value of x.
     * @return double
     */
    public double x( ){ return x; }

    /**
     * return the value of y.
     * @return double
     */
    public double y( ){ return y; }

    /**
     * return the value of z.
     * @return double
     */
    public double z( ){ return z; }

    /**
     * multiply this coordinate with b.
     * @param b Coordinate
     */
    public void multiply( Coordinate b )
    {
        this.x *= b.x;
        this.y *= b.y;
        this.z *= b.z;
    }

    /**
     * compute dot product between this and b.
     * @param b Coordinate
     * @return double
     */
    public double dot( Coordinate b )
    {
        double ret = 0;
        ret += this.x * b.x;
        ret += this.y * b.y;
        ret += this.z * b.z;
        return ret;
    }

    /**
     * set this coordinate equal to b.
     * @param b Coordinate
     */
    public void set( Coordinate b )
    {
        this.x = b.x;
        this.y = b.y;
        this.z = b.z;
    }

    /**
     * set x y and z for this coordinate.
     * @param x double
     * @param y double
     * @param z double
     */
    public void set( double x, double y, double z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
