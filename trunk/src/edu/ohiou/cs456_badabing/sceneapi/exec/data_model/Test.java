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
public class Test
{
    public Test()
    {
    }

    public static void main(String[] args)
    {
        DataBaseModel db = null;
        try
        {
            db = DataBaseModel.readFile(System.getProperty(
                    "user.dir") + "\\data\\db.xls");
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        System.out.println( db.toString() );
        Test test = new Test();
    }
}
