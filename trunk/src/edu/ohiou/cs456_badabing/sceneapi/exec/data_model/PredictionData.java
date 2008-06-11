package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

/**
 * <p>Container for Prediction</p>
 *
 * <p>Description: Contains factors for predictions.</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class PredictionData
{

     public double expansion_factor=1.069;
     public double sex_fawn=.51;
     public double sex_year=.51;
     public double sex_adult=.51;

     public double rep_fawn=.5;
     public double rep_year=1.75;
     public double rep_adult=1.75;

     public double summer_fawn=.27;
     public double summer_year=.075;
     public double summer_adult=.08;

     public double winter_rate=.05;
     public double winter_rate_bucks=.08;

     public double antlerless = 1.15;
     public double antlered = 1.1;

     public static String names[] = { "Expansion Factor", "Winter Rate", "Winter Bucks Rate", "Antlered", "Antlerless", " ",
                             "Sex Ratio", "Rep Rate", "Summer Mortality" };


    public PredictionData()
    {


    }

    public String toString()
    {
        String ret = new String();
        return ret;
    }
}
