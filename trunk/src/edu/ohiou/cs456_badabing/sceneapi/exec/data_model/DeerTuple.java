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

    public void multiply( double f )
    {
        adult_bucks *= f;
        adult_does *= f;
        fawn_bucks *= f;
        fawn_does *= f;
        yearling_bucks *= f;
        yearling_does *= f;

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

    public static DeerTuple Default(DeerTuple default1, DeerTuple Harvest,
                             PredictionData Constants_in)
    {

        DataBaseModel db = new DataBaseModel();

        DeerTuple harvest = Harvest;

        DeerTuple prebirth = new DeerTuple();
        DeerTuple postbirth = new DeerTuple();
        DeerTuple prehunt = new DeerTuple();
        DeerTuple summer = new DeerTuple();
        DeerTuple winter = new DeerTuple();
        DeerTuple posthunt = new DeerTuple();

        PredictionData Constants = Constants_in;

        double expansion_factor = Constants.expansion_factor;
        double sex_fawn = Constants.sex_fawn;
        double sex_year = Constants.sex_year;
        double sex_adult = Constants.sex_adult;

        double rep_fawn = Constants.rep_fawn;
        double rep_year = Constants.rep_year;
        double rep_adult = Constants.rep_adult;

        double summer_fawn = Constants.summer_fawn;
        double summer_year = Constants.summer_year;
        double summer_adult = Constants.summer_adult;

        double winter_rate = Constants.winter_rate;
        double winter_rate_bucks = Constants.winter_rate_bucks;

        double antlerless = Constants.antlerless;
        double antlered = Constants.antlered;

        prehunt.year = default1.year;
        prehunt.county = default1.county;
        prehunt.fawn_does = default1.fawn_does * expansion_factor;
        prehunt.yearling_does = default1.yearling_does * expansion_factor;
        prehunt.adult_does = default1.adult_does * expansion_factor;
        prehunt.fawn_bucks = default1.fawn_bucks * expansion_factor;
        prehunt.yearling_bucks = default1.yearling_bucks * expansion_factor;
        prehunt.adult_bucks = default1.adult_bucks * expansion_factor;
        //DeerList.add(prehunt);

        posthunt.year = default1.year;
        posthunt.county = default1.county;
        posthunt.fawn_does = prehunt.fawn_does -
                             ((Double) harvest.fawn_does * antlerless);
        posthunt.yearling_does = prehunt.yearling_does -
                                 ((Double) harvest.yearling_does * antlerless);
        posthunt.adult_does = prehunt.adult_does -
                              ((Double) harvest.adult_does * antlerless);
        posthunt.fawn_bucks = prehunt.fawn_bucks -
                              ((Double) harvest.fawn_bucks * antlerless);
        posthunt.yearling_bucks = prehunt.yearling_bucks -
                                  ((Double) harvest.yearling_bucks * antlered);
        posthunt.adult_bucks = prehunt.adult_bucks -
                               ((Double) harvest.adult_bucks * antlered);
        //DeerList.add(posthunt);

        winter.year = default1.year;
        winter.county = default1.county;
        winter.fawn_does = posthunt.fawn_does * winter_rate;
        winter.yearling_does = posthunt.yearling_does * winter_rate;
        winter.adult_does = posthunt.adult_does * winter_rate;
        winter.fawn_bucks = posthunt.fawn_bucks * winter_rate;
        winter.yearling_bucks = posthunt.yearling_bucks * winter_rate_bucks;
        winter.adult_bucks = posthunt.adult_bucks * winter_rate_bucks;
        //DeerLis.add(winter);


        prebirth.year = default1.year;
        prebirth.county = default1.county;
        prebirth.fawn_does = posthunt.fawn_does - ((Double) winter.fawn_does);
        prebirth.yearling_does = posthunt.yearling_does -
                                 ((Double) winter.yearling_does);
        prebirth.adult_does = posthunt.adult_does - ((Double) winter.adult_does);
        prebirth.fawn_bucks = posthunt.fawn_bucks - ((Double) winter.fawn_bucks);
        prebirth.yearling_bucks = posthunt.yearling_bucks -
                                  ((Double) winter.yearling_bucks);
        prebirth.adult_bucks = posthunt.adult_bucks -
                               ((Double) winter.adult_bucks);
        //DeerList.add(prebirth);


        postbirth.year = default1.year;
        postbirth.county = default1.county;
        postbirth.fawn_does = (rep_fawn * (Double) prebirth.fawn_does *
                               (1 - sex_fawn)) +
                              (rep_year * (Double) prebirth.yearling_does *
                               (1 - sex_year)) +
                              (rep_adult * (Double) prebirth.adult_does *
                               (1 - sex_adult));
        postbirth.yearling_does = prebirth.fawn_does;
        postbirth.adult_does = (Double) prebirth.yearling_does +
                               (Double) prebirth.adult_does;
        postbirth.fawn_bucks = (rep_fawn * (Double) prebirth.fawn_does *
                                (sex_fawn)) +
                               (rep_year * (Double) prebirth.yearling_does *
                                (sex_year)) +
                               (rep_adult * (Double) prebirth.adult_does *
                                (sex_adult));
        postbirth.yearling_bucks = prebirth.fawn_bucks;
        postbirth.adult_bucks = (Double) prebirth.yearling_bucks +
                                (Double) prebirth.adult_bucks;

        return postbirth;

    }

    //This function will also take a parameters struct and deer tuple.

    public static DeerTuple Next_Year(DeerTuple Post, DeerTuple Harvest_in,
                               PredictionData Constants_in)
    {

        DataBaseModel db = new DataBaseModel();

        DeerTuple harvest = Harvest_in;

        DeerTuple prebirth = new DeerTuple();
        DeerTuple postbirth = new DeerTuple();
        DeerTuple prehunt = new DeerTuple();
        DeerTuple summer = new DeerTuple();
        DeerTuple winter = new DeerTuple();
        DeerTuple posthunt = new DeerTuple();

        PredictionData Constants = Constants_in;

        double expansion_factor = Constants.expansion_factor;
        double sex_fawn = Constants.sex_fawn;
        double sex_year = Constants.sex_year;
        double sex_adult = Constants.sex_adult;

        double rep_fawn = Constants.rep_fawn;
        double rep_year = Constants.rep_year;
        double rep_adult = Constants.rep_adult;

        double summer_fawn = Constants.summer_fawn;
        double summer_year = Constants.summer_year;
        double summer_adult = Constants.summer_adult;

        double winter_rate = Constants.winter_rate;
        double winter_rate_bucks = Constants.winter_rate_bucks;

        double antlerless = Constants.antlerless;
        double antlered = Constants.antlered;

        summer.year = Post.year;
        summer.county = Post.county;
        summer.fawn_does = Post.fawn_does * (Double) summer_fawn;
        summer.yearling_does = Post.yearling_does * (Double) summer_year;
        summer.adult_does = Post.adult_does * (Double) summer_year;
        summer.fawn_bucks = Post.fawn_bucks * (Double) summer_fawn;
        summer.yearling_bucks = Post.yearling_bucks * (Double) summer_adult;
        summer.adult_bucks = Post.adult_bucks * (Double) summer_adult;

        //System.out.println(Post.yearling_does);
        //System.out.println((Double) summer_year);

        //System.out.print(summer.toString());

        prehunt.year = Post.year;
        prehunt.county = Post.county;
        prehunt.fawn_does = (Double) Post.fawn_does - summer.fawn_does;
        prehunt.yearling_does = (Double) Post.yearling_does -
                                summer.yearling_does;
        prehunt.adult_does = (Double) Post.adult_does - summer.adult_does;
        prehunt.fawn_bucks = (Double) Post.fawn_bucks - summer.fawn_bucks;
        prehunt.yearling_bucks = (Double) Post.yearling_bucks -
                                 summer.yearling_bucks;
        prehunt.adult_bucks = (Double) Post.adult_bucks - summer.adult_bucks;
        //DeerList.add(prehunt);


        posthunt.year = Post.year;
        posthunt.county = Post.county;
        posthunt.fawn_does = prehunt.fawn_does -
                             ((Double) harvest.fawn_does * antlerless);
        posthunt.yearling_does = prehunt.yearling_does -
                                 ((Double) harvest.yearling_does * antlerless);
        posthunt.adult_does = prehunt.adult_does -
                              ((Double) harvest.adult_does * antlerless);
        posthunt.fawn_bucks = prehunt.fawn_bucks -
                              ((Double) harvest.fawn_bucks * antlerless);
        posthunt.yearling_bucks = prehunt.yearling_bucks -
                                  ((Double) harvest.yearling_bucks * antlered);
        posthunt.adult_bucks = prehunt.adult_bucks -
                               ((Double) harvest.adult_bucks * antlered);
        //DeerList.add(posthunt);

        winter.year = Post.year;
        winter.county = Post.county;
        winter.fawn_does = posthunt.fawn_does * winter_rate;
        winter.yearling_does = posthunt.yearling_does * winter_rate;
        winter.adult_does = posthunt.adult_does * winter_rate;
        winter.fawn_bucks = posthunt.fawn_bucks * winter_rate;
        winter.yearling_bucks = posthunt.yearling_bucks * winter_rate_bucks;
        winter.adult_bucks = posthunt.adult_bucks * winter_rate_bucks;
        //DeerLis.add(winter);


        prebirth.year = Post.year;
        prebirth.county = Post.county;
        prebirth.fawn_does = posthunt.fawn_does - ((Double) winter.fawn_does);
        prebirth.yearling_does = posthunt.yearling_does -
                                 ((Double) winter.yearling_does);
        prebirth.adult_does = posthunt.adult_does - ((Double) winter.adult_does);
        prebirth.fawn_bucks = posthunt.fawn_bucks - ((Double) winter.fawn_bucks);
        prebirth.yearling_bucks = posthunt.yearling_bucks -
                                  ((Double) winter.yearling_bucks);
        prebirth.adult_bucks = posthunt.adult_bucks -
                               ((Double) winter.adult_bucks);
        //DeerList.add(prebirth);


        postbirth.year = Post.year;
        postbirth.county = Post.county;
        postbirth.fawn_does = (rep_fawn * (Double) prebirth.fawn_does *
                               (1 - sex_fawn)) +
                              (rep_year * (Double) prebirth.yearling_does *
                               (1 - sex_year)) +
                              (rep_adult * (Double) prebirth.adult_does *
                               (1 - sex_adult));
        postbirth.yearling_does = prebirth.fawn_does;
        postbirth.adult_does = (Double) prebirth.yearling_does +
                               (Double) prebirth.adult_does;
        postbirth.fawn_bucks = (rep_fawn * (Double) prebirth.fawn_does *
                                (sex_fawn)) +
                               (rep_year * (Double) prebirth.yearling_does *
                                (sex_year)) +
                               (rep_adult * (Double) prebirth.adult_does *
                                (sex_adult));
        postbirth.yearling_bucks = prebirth.fawn_bucks;
        postbirth.adult_bucks = (Double) prebirth.yearling_bucks +
                                (Double) prebirth.adult_bucks;

        return postbirth;

    }
}
