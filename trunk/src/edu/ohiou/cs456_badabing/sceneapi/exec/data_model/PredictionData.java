package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;

/**
 * <p>Container for different factors used in prediction.</p>
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
public class PredictionData
{
    public double expansion_factors[] = { .9, .9, .9, .9, .9, .9 };
    public double fertility_rates[] = { .2, .5, .6 };

    public double harvest[] = { 1, 1, 1, 1, 1, 1 };
    public double winter_mortality[] = { 1, 1, 1, 1, 1, 1 };
    public double summer_mortality[] = { 1, 1, 1, 1, 1, 1 };
    public double harvest_reported[] = { 1, 1, 1, 1, 1, 1 };


    public PredictionData()
    {
    }
}
