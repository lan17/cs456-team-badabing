package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;
import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * <p>Title: JOGL stuff</p>
 *
 * <p>Description: Experimentation with JOGL</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Eugene Farber
 * @version 1.0
 */
public class Test
{
    public Test()
    {
    }
  




//This function will take the parameters struct as well as a deer tuple. Values are Defaulted at this
//Point
 	
    public DeerTuple Default(DeerTuple default1, DeerTuple Harvest, PredictionData Constants_in){

     DataBaseModel db = new DataBaseModel();  


     DeerTuple harvest=Harvest;
  
     DeerTuple prebirth= new DeerTuple();
     DeerTuple postbirth= new DeerTuple();
     DeerTuple prehunt= new DeerTuple();
     DeerTuple summer= new DeerTuple();   
     DeerTuple winter= new DeerTuple();
     DeerTuple posthunt= new DeerTuple();
     
     PredictionData Constants=Constants_in;
     
     double expansion_factor=Constants.expansion_factor;
     double sex_fawn=Constants.sex_fawn;
     double sex_year=Constants.sex_year;
     double sex_adult=Constants.sex_adult;
     
     double rep_fawn=Constants.rep_fawn;
     double rep_year=Constants.rep_year;
     double rep_adult=Constants.rep_adult;
     
     double summer_fawn=Constants.summer_fawn;
     double summer_year=Constants.summer_year;
     double summer_adult=Constants.summer_adult;
     
     double winter_rate=Constants.winter_rate;
     double winter_rate_bucks=Constants.winter_rate_bucks;
     
     double antlerless=Constants.antlerless;
     double antlered=Constants.antlered;
    
     prehunt.year=default1.year;
     prehunt.county=default1.county;
     prehunt.fawn_does=default1.fawn_does*expansion_factor;
     prehunt.yearling_does=default1.yearling_does*expansion_factor;
     prehunt.adult_does=default1.adult_does*expansion_factor;
     prehunt.fawn_bucks=default1.fawn_bucks*expansion_factor;
     prehunt.yearling_bucks=default1.yearling_bucks*expansion_factor;
     prehunt.adult_bucks=default1.adult_bucks*expansion_factor;
     //DeerList.add(prehunt);
     	     
     posthunt.year=default1.year;
     posthunt.county=default1.county;
     posthunt.fawn_does=prehunt.fawn_does-( (Double)harvest.fawn_does*antlerless);
     posthunt.yearling_does=prehunt.yearling_does-((Double)harvest.yearling_does*antlerless);
     posthunt.adult_does=prehunt.adult_does-((Double)harvest.adult_does*antlerless);
     posthunt.fawn_bucks=prehunt.fawn_bucks-((Double)harvest.fawn_bucks*antlerless);
     posthunt.yearling_bucks=prehunt.yearling_bucks-((Double)harvest.yearling_bucks*antlered);
     posthunt.adult_bucks=prehunt.adult_bucks-((Double)harvest.adult_bucks*antlered);
     //DeerList.add(posthunt);
     
     winter.year=default1.year;
     winter.county=default1.county;
     winter.fawn_does=posthunt.fawn_does*winter_rate;
     winter.yearling_does=posthunt.yearling_does*winter_rate;
     winter.adult_does=posthunt.adult_does*winter_rate;
     winter.fawn_bucks=posthunt.fawn_bucks*winter_rate;
     winter.yearling_bucks=posthunt.yearling_bucks*winter_rate_bucks;
     winter.adult_bucks=posthunt.adult_bucks*winter_rate_bucks;
     //DeerLis.add(winter);
     
     
     prebirth.year=default1.year;
     prebirth.county=default1.county;
     prebirth.fawn_does=posthunt.fawn_does-( (Double)winter.fawn_does);
     prebirth.yearling_does=posthunt.yearling_does-((Double)winter.yearling_does);
     prebirth.adult_does=posthunt.adult_does-((Double)winter.adult_does);
     prebirth.fawn_bucks=posthunt.fawn_bucks-((Double)winter.fawn_bucks);
     prebirth.yearling_bucks=posthunt.yearling_bucks-((Double)winter.yearling_bucks);
     prebirth.adult_bucks=posthunt.adult_bucks-((Double)winter.adult_bucks);
     //DeerList.add(prebirth);
     
     
     postbirth.year=default1.year;
     postbirth.county=default1.county;
     postbirth.fawn_does=(rep_fawn*(Double)prebirth.fawn_does*(1-sex_fawn))+(rep_year*(Double)prebirth.yearling_does*(1-sex_year))+(rep_adult*(Double)prebirth.adult_does*(1-sex_adult));
     postbirth.yearling_does=prebirth.fawn_does;
     postbirth.adult_does=(Double)prebirth.yearling_does+(Double)prebirth.adult_does;
     postbirth.fawn_bucks=(rep_fawn*(Double)prebirth.fawn_does*(sex_fawn))+(rep_year*(Double)prebirth.yearling_does*(sex_year))+(rep_adult*(Double)prebirth.adult_does*(sex_adult));
     postbirth.yearling_bucks=prebirth.fawn_bucks;
     postbirth.adult_bucks=(Double)prebirth.yearling_bucks+(Double)prebirth.adult_bucks;
    
     return postbirth;
    	
    }
    


//This function will also take a parameters struct and deer tuple.

public DeerTuple Next_Year(DeerTuple Post, DeerTuple Harvest_in, PredictionData Constants_in){

     DataBaseModel db = new DataBaseModel();  

     DeerTuple harvest= Harvest_in;
  
     DeerTuple prebirth= new DeerTuple();
     DeerTuple postbirth= new DeerTuple();
     DeerTuple prehunt= new DeerTuple();
     DeerTuple summer= new DeerTuple();   
     DeerTuple winter= new DeerTuple();
     DeerTuple posthunt= new DeerTuple();
     
     PredictionData Constants=Constants_in;

     double expansion_factor=Constants.expansion_factor;
     double sex_fawn=Constants.sex_fawn;
     double sex_year=Constants.sex_year;
     double sex_adult=Constants.sex_adult;
     
     double rep_fawn=Constants.rep_fawn;
     double rep_year=Constants.rep_year;
     double rep_adult=Constants.rep_adult;
     
     double summer_fawn=Constants.summer_fawn;
     double summer_year=Constants.summer_year;
     double summer_adult=Constants.summer_adult;
     
     double winter_rate=Constants.winter_rate;
     double winter_rate_bucks=Constants.winter_rate_bucks;
     
     double antlerless=Constants.antlerless;
     double antlered=Constants.antlered;


     summer.year=Post.year;
     summer.county=Post.county;
     summer.fawn_does=Post.fawn_does*(Double)summer_fawn;
     summer.yearling_does=Post.yearling_does*(Double)summer_year;
     summer.adult_does=Post.adult_does*(Double)summer_year;
     summer.fawn_bucks=Post.fawn_bucks*(Double)summer_fawn;
     summer.yearling_bucks=Post.yearling_bucks*(Double)summer_adult;
     summer.adult_bucks=Post.adult_bucks*(Double)summer_adult;

     System.out.println(Post.yearling_does);
     System.out.println((Double)summer_year);

     System.out.print(summer.toString());
  
     prehunt.year=Post.year;
     prehunt.county=Post.county;
     prehunt.fawn_does=(Double)Post.fawn_does-summer.fawn_does;
     prehunt.yearling_does=(Double)Post.yearling_does-summer.yearling_does;
     prehunt.adult_does=(Double)Post.adult_does-summer.adult_does;
     prehunt.fawn_bucks=(Double)Post.fawn_bucks-summer.fawn_bucks;
     prehunt.yearling_bucks=(Double)Post.yearling_bucks-summer.yearling_bucks;
     prehunt.adult_bucks=(Double)Post.adult_bucks-summer.adult_bucks;
     //DeerList.add(prehunt);
     
     	     
     posthunt.year=Post.year;
     posthunt.county=Post.county;
     posthunt.fawn_does=prehunt.fawn_does-( (Double)harvest.fawn_does*antlerless);
     posthunt.yearling_does=prehunt.yearling_does-((Double)harvest.yearling_does*antlerless);
     posthunt.adult_does=prehunt.adult_does-((Double)harvest.adult_does*antlerless);
     posthunt.fawn_bucks=prehunt.fawn_bucks-((Double)harvest.fawn_bucks*antlerless);
     posthunt.yearling_bucks=prehunt.yearling_bucks-((Double)harvest.yearling_bucks*antlered);
     posthunt.adult_bucks=prehunt.adult_bucks-((Double)harvest.adult_bucks*antlered);
     //DeerList.add(posthunt);
     
     winter.year=Post.year;
     winter.county=Post.county;
     winter.fawn_does=posthunt.fawn_does*winter_rate;
     winter.yearling_does=posthunt.yearling_does*winter_rate;
     winter.adult_does=posthunt.adult_does*winter_rate;
     winter.fawn_bucks=posthunt.fawn_bucks*winter_rate;
     winter.yearling_bucks=posthunt.yearling_bucks*winter_rate_bucks;
     winter.adult_bucks=posthunt.adult_bucks*winter_rate_bucks;
     //DeerLis.add(winter);
     
     
     prebirth.year=Post.year;
     prebirth.county=Post.county;
     prebirth.fawn_does=posthunt.fawn_does-( (Double)winter.fawn_does);
     prebirth.yearling_does=posthunt.yearling_does-((Double)winter.yearling_does);
     prebirth.adult_does=posthunt.adult_does-((Double)winter.adult_does);
     prebirth.fawn_bucks=posthunt.fawn_bucks-((Double)winter.fawn_bucks);
     prebirth.yearling_bucks=posthunt.yearling_bucks-((Double)winter.yearling_bucks);
     prebirth.adult_bucks=posthunt.adult_bucks-((Double)winter.adult_bucks);
     //DeerList.add(prebirth);
     
     
     postbirth.year=Post.year;
     postbirth.county=Post.county;
     postbirth.fawn_does=(rep_fawn*(Double)prebirth.fawn_does*(1-sex_fawn))+(rep_year*(Double)prebirth.yearling_does*(1-sex_year))+(rep_adult*(Double)prebirth.adult_does*(1-sex_adult));
     postbirth.yearling_does=prebirth.fawn_does;
     postbirth.adult_does=(Double)prebirth.yearling_does+(Double)prebirth.adult_does;
     postbirth.fawn_bucks=(rep_fawn*(Double)prebirth.fawn_does*(sex_fawn))+(rep_year*(Double)prebirth.yearling_does*(sex_year))+(rep_adult*(Double)prebirth.adult_does*(sex_adult));
     postbirth.yearling_bucks=prebirth.fawn_bucks;
     postbirth.adult_bucks=(Double)prebirth.yearling_bucks+(Double)prebirth.adult_bucks;
    
     return postbirth;
    	
    	
    }

 private static BufferedReader stdin = new BufferedReader( new InputStreamReader( System.in ) );



    
    public static DeerTuple Harvest()
    {
               DeerTuple tuple = new DeerTuple();
                 tuple.year = 1977;
                 tuple.county = "adams";
                 tuple.fawn_does = 1;
                 tuple.yearling_does = 1;
                 tuple.adult_does = 1;
                 tuple.fawn_bucks = 2;
                 tuple.yearling_bucks = 60;
                 tuple.adult_bucks = 37;
 
      return tuple;	
    }

 
    public static DeerTuple Harvest2()
    {
               DeerTuple tuple = new DeerTuple();
                 tuple.year = 1977;
                 tuple.county = "adams";
                 tuple.fawn_does = 1;
                 tuple.yearling_does = 1;
                 tuple.adult_does = 0;
                 tuple.fawn_bucks = 1;
                 tuple.yearling_bucks = 62;
                 tuple.adult_bucks = 36;
      return tuple;	
    }
    




    public static void main(String[] args)
    {
        DataBaseModel db = null;
      
    
     Test Deer_Test=new Test();
     
     boolean  initial=true;
  
     DeerTuple default1= new DeerTuple ();
    
     default1.year=1977;
     default1.county="Adams";
     default1.fawn_does=126;
     default1.yearling_does=99;
     default1.adult_does=111;
     default1.fawn_bucks=126;
     default1.yearling_bucks=114;
     default1.adult_bucks=61;

     DeerTuple Initial_postbirth=new DeerTuple();
     
     DeerTuple Harvest=Harvest();     
     DeerTuple Harvest2=Harvest2();
     
     PredictionData Con=new PredictionData();

     Initial_postbirth=Deer_Test.Default(default1,Harvest,Con);
     
     DeerTuple Next_year=new DeerTuple();
	 
     Next_year=Deer_Test.Next_Year(Initial_postbirth,Harvest2,Con);
          
     System.out.println("\n\nNext POST BIRTH:");
     System.out.print(Next_year.toString());  	
    
    }
}
