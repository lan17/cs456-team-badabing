package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

/**
 * <p>Title: JOGL stuff</p>
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
public class DeerTuple
{
    public int year;
    public String county;
    public double fawn_does;
    public double yearling_does;
    public double adult_does;
    public double fawn_bucks;
    public double yearling_bucks;
    public double adult_bucks;

    public double total;

    public DeerTuple()
    {
    }

    public double sum()
    {
        return fawn_does + yearling_does + adult_does + fawn_bucks + yearling_bucks + adult_bucks;
    }

    public String toString()
    {
        String ret = "";
        ret += county;
        ret += " " + Integer.toString( year ) + ": " + Double.toString( fawn_does ) + ", " + Double.toString( yearling_does ) + ", " + Double.toString( adult_does);
        ret += ", " + Double.toString( fawn_bucks ) + ", " + Double.toString( yearling_bucks ) + ", " + Double.toString( adult_bucks );

        return ret;
    }
}
