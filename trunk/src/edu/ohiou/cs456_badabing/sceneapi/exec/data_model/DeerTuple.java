package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

/**
 * <p>Represents one tuple of harvest data information.</p>
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
    /**
    * Year of harvest
    */
    public int year;

    /**
     * County that deer was harvested from
     */
    public String county;

    /**
     * how many fawn does were harvested
     */
    public double fawn_does;

    /**
     * how many yearling does were harvested
     */
    public double yearling_does;

    /**
     * how many adult does were harvested
     */
    public double adult_does;

    /**
     * how many fawn bucks were harvested
     */
    public double fawn_bucks;

    /**
     * how many yearling bucks were harvested
     */
    public double yearling_bucks;

    /**
     * how many adult bucks were harvested
     */
    public double adult_bucks;



    public DeerTuple()
    {
    }


    /**
     * what is the total of deer harvested from this county?
     * @return double - sum of 6 other variables.
     */
    public double sum()
    {
        return fawn_does + yearling_does + adult_does + fawn_bucks + yearling_bucks + adult_bucks;
    }

    /**
     * return a String represnation of this deer tuple.
     * @return String
     */
    public String toString()
    {
        String ret = "";
        ret += county;
        ret += " " + Integer.toString( year ) + ": " + Double.toString( fawn_does ) + ", " + Double.toString( yearling_does ) + ", " + Double.toString( adult_does);
        ret += ", " + Double.toString( fawn_bucks ) + ", " + Double.toString( yearling_bucks ) + ", " + Double.toString( adult_bucks );

        return ret;
    }
}
