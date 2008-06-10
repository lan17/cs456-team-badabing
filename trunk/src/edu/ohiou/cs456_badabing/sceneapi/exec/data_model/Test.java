package edu.ohiou.cs456_badabing.sceneapi.exec.data_model;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
public class Test extends DeerTuple
{
    public Test()
    {
    }





//This function will take the parameters struct as well as a deer tuple. Values are Defaulted at this
//Point
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
