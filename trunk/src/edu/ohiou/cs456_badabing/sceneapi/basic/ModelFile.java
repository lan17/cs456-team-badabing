package edu.ohiou.cs456_badabing.sceneapi.basic;

import java.io.*;
import java.util.zip.*;
import java.util.*;
import edu.ohiou.cs456_badabing.sceneapi.*;

/**
 * <p>Container for data loaded from OBJ and MTL files.  It prepares it for use in ModelNode.</p>
 *
 * <p>Description: Container for data loaded from OBJ and MTL files.  It prepares it for use in ModelNode.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ModelFile
{
        /**
         * unique name for this model.
         */
        private String name;

        /**
         * system specific file path separator.
         */
        public static final String separator = java.io.File.separator;
        /**
         * folder in which to look for model files.
         */
        public static final String data_folder = "data";

        /**
         * vertices in this model.
         */
        public Vector< Coordinate > coordinates = new Vector<Coordinate>();
        /**
         * triangles that make up this model.
         */
        public Vector< Triangle > triangles = new Vector<Triangle>();

        /**
         * different material properties in this model.
         */
        public Hashtable< String, Material > materials = new Hashtable<String,Material>();

        /**
         * array of texture coordinates.
         */
        public Vector< Coordinate > texture_coordinates = new Vector<Coordinate>();

        /**
         * Hashtable which associates a material name with a group of triangles.
         */
        public Hashtable< String, Vector< Triangle > > mtl_groups = new Hashtable<String,Vector<Triangle>>();

        private double x_max, x_min, y_max, y_min, z_max, z_min;

        /**
         * greatest value of x in all coordinates.
         * @return double
         */
        public double getXMax( ){ return x_max; }

        /**
         * least value of x in all coordiantes.
         * @return double
         */
        public double getXMin( ){ return x_min; }

        /**
         * greatest value of y in all coordinates.
         * @return double
         */
        public double getYMax( ){ return y_max; }

        /**
         * least value of y in all coordinates.
         * @return double
         */
        public double getYMin( ){ return y_min; }

        /**
         * greatest value of z in all coordinates.
         * @return double
         */
        public double getZMax( ){ return z_max; }

        /**
         * least value of z in all all coordinates.
         * @return double
         */
        public double getZMin( ){ return z_min; }


        /**
         * This method takes string and breaks it into a Vector of strings based on a given delimeter
         * @param string String
         * @param delimiter char
         * @return Vector
         */
        public static Vector< String > expand( final String string, char delimeter )
        {
            Vector< String > ret = new Vector<String>();

            int begin = 0;
            int i;
            for( i = 0; i < string.length( ); i++ )
            {
                 if( i > 0 && string.charAt( i ) == delimeter && string.charAt( i - 1 ) != delimeter )
                 {
                     ret.add( string.substring( begin, i ) );
                     begin = i + 1;
                 }
                 else
                 {
                     if( string.charAt( i ) == delimeter ){ begin = i + 1; }
                 }
            }
            if( begin < i )
            {
                ret.add( string.substring( begin ) );
            }

            return ret;
        }

    /**
    * Removes extension from the string.
    * @param a String - input string.
    * @return String - substring that is from begining of input to the last occurrence of '.'
    */
    public static String stripExtension( String a )
    {
        String name = new String( a );
        try
        {
            int i = name.length() - 1;
            while (i >= 0 && name.charAt(i) != '.') i--;
            return name.substring(0, i);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return  name;
    }

    /**
     * function that loads the data files.
     */
    private void init( )
    {
        x_max = y_max = z_max = Double.MIN_VALUE;
        x_min = y_min = z_min = Double.MAX_VALUE;

        // String file_name = name + ".obj";
        String file_name = stripExtension( name );


        file_name += ".mtl";
        System.out.println( "ZZZZZZZZZZZZZZ - " + file_name );
        //file_name = System.getProperty( "user.dir" ) + separator + data_folder + separator + name + ".mtl";
        File mdf = new File( file_name );
        Scanner data;
        try
        {
            data = new Scanner(mdf);
        }
        catch( java.io.FileNotFoundException e )
        {
            System.out.println( "Coudlnt read " + mdf.getAbsolutePath() );
            e.printStackTrace( );
            return;
        }
        try
        {
            readMTL(data);
        }
        catch( ModelFile_ReadError e )
        {
            System.out.println( "OH NOES" );
            e.printStackTrace();
        }
        data.close( );

        mdf = null;
        //file_name = null;
        data = null;
        System.out.println( "ZZZZZZZZZZZZZZ - " + file_name );
        file_name = stripExtension( file_name );
        file_name += ".obj";

         mdf = new File( file_name );
        System.out.println( "OBJ File - " + mdf.toString() + " exists? - " + mdf.exists() );
        try
        {
            data = new Scanner( mdf );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return;
        }
        try
        {
            readOBJ( data );
        }
        catch( ModelFile_ReadError e )
        {
            e.printStackTrace();
        }
        data.close( );

        sortMaterials( );
        System.out.println( "Size of mtl_groups = " + mtl_groups.size() );
    }

    /**
     * this method associates Material names to Vector's of triangles that belong to that material group.
     */
    private void sortMaterials( )
    {
        if( materials.size() == 0 ){ return; }
        for( Triangle triangle : triangles )
        {
            //System.out.println( triangle );
            //System.out.println( triangle.material );
            Vector< Triangle > group = mtl_groups.get( triangle.material.name );
            if( group == null )
            {
                group = new Vector<Triangle>( );
                mtl_groups.put( triangle.material.name, group );
            }
            group.add( triangle );
        }
    }


    /**
     * this method loads materials from the MTL file.
     * @param s Scanner
     * @throws ModelFile_ReadError
     */
    private void readMTL( Scanner s ) throws ModelFile_ReadError
    {
        System.out.println( "Reading mtl file......." );
        try
        {
            s.useDelimiter(".*\n");
             Material cur_mtl = null;
            while( s.hasNext() )
            {
                String line_string = s.next();
                line_string.trim();
                Vector< String > line = expand( line_string, ' ' );

                if( line.size( ) > 0 )
                {
                    line.set( 0, line.get( 0 ).trim( ) );
                    if( line.size( ) > 1 ) { line.get( 1 ).trim( );  }
                    if( line.get( 0 ).equals( "newmtl" ) )
                    {
                        if( cur_mtl != null )
                        {
                            materials.put( cur_mtl.name.trim( ), cur_mtl );
                            System.out.println( "added new mtl - " + cur_mtl.name.trim( ) + " :: " + cur_mtl.toString() );
                        }
                        cur_mtl = null;
                        cur_mtl = new Material( line.get( 1 ).trim( ) );

                        System.out.println( "Reading in new material - " + line.get( 1 ).trim( ) );
                    }
                    if( line.get( 0 ).equals( "Ns" ) && cur_mtl != null )
                    {
                        cur_mtl.shininess[0] = Float.valueOf( line.get( 1 ) );
                    }
                    if( line.get( 0 ).equals( "Ka" ) && cur_mtl != null )
                    {
                        cur_mtl.ambient[0] = Float.valueOf( line.get( 1 ) );
                        cur_mtl.ambient[1] = Float.valueOf( line.get( 2 ) );
                        cur_mtl.ambient[2] = Float.valueOf( line.get( 3 ) );
                        cur_mtl.ambient[3] = 1;
                    }
                    if( line.get( 0 ).equals( "Kd" ) && cur_mtl != null )
                    {
                        cur_mtl.diffuse[0] = Float.valueOf( line.get( 1 ) );
                        cur_mtl.diffuse[1] = Float.valueOf( line.get( 2 ) );
                        cur_mtl.diffuse[2] = Float.valueOf( line.get( 3 ) );
                        cur_mtl.diffuse[3] = 1;
                    }
                    if( line.get( 0 ).equals( "Ks" ) && cur_mtl != null )
                    {
                        cur_mtl.specular[0] = Float.valueOf( line.get( 1 ) );
                        cur_mtl.specular[1] = Float.valueOf( line.get( 2 ) );
                        cur_mtl.specular[2] = Float.valueOf( line.get( 3 ) );
                        cur_mtl.specular[3] = 1;
                    }
                    if( line.get( 0 ).equals( "Kc" ) && cur_mtl != null )
                    {
                        cur_mtl.color[0] = Float.valueOf( line.get( 1 ) );
                        cur_mtl.color[1] = Float.valueOf( line.get( 2 ) );
                        cur_mtl.color[2] = Float.valueOf( line.get( 3 ) );
                        cur_mtl.color[3] = Float.valueOf( line.get( 4 ) );
                    }
                    if( line.get( 0 ).equals( "TEXTURE" ) && cur_mtl != null )
                    {
                        cur_mtl.texture = TextureManager.getTexture( line.get( 1 ).trim( ) );
                    }
                    if( line.get( 0 ).equals( "[END]" ) && cur_mtl != null )
                    {
                        materials.put( cur_mtl.name, cur_mtl );
                        System.out.println( "Added new material - " + cur_mtl.name );
                        cur_mtl = null;
                    }
                }
            }
        }
        catch( Exception e )
        {
            throw new ModelFile_ReadError( e );
        }
        System.out.println( "Done reading mtl file... " );
    }

    /**
     * method to read OBJ file.
     * @param input Scanner
     * @throws ModelFile_ReadError if any excpetion is caught
     */
    private void readOBJ( Scanner input ) throws ModelFile_ReadError
    {
        try
        {
            System.out.println("Parsing......");
            System.out.println( System.getProperty( "line.separator" ) );
            input.useDelimiter(".*\n");
            String cur_mtl = "Default";
            while (input.hasNext())
            {
                String line_string = input.next( );
                line_string.trim();
                Vector<String> line = expand( line_string, ' ');
                //System.out.println( "LOL " + Integer.toString( line.size( ) ) );
                if ( line.size() > 1 )
                {
                    line.get( 1 ).trim();
                    //System.out.println(line.get(0));
                    if (line.elementAt(0).equals("usemtl"))
                    {
                        cur_mtl = line.get(1).trim( );
                        //System.out.println(line.elementAt(1));
                    }
                    if (line.elementAt(0).equals("v"))
                    {
                        Coordinate tmp = new Coordinate();
                        tmp.setX(Double.valueOf(line.elementAt(1)));
                        tmp.setY(Double.valueOf(line.elementAt(2)));
                        tmp.setZ(Double.valueOf(line.elementAt(3)));
                        coordinates.add(tmp);

                        if( x_min > tmp.x() ) x_min = tmp.x();
                        if( x_max < tmp.x() ) x_max = tmp.x();
                        if( y_min > tmp.y() ) y_min = tmp.y();
                        if( y_max < tmp.y() ) y_max = tmp.y();
                        if( z_min > tmp.z() ) z_min = tmp.z();
                        if( z_max < tmp.z() ) z_max = tmp.z();
                    }
                    if( line.elementAt(0).equals("vt"))
                    {
                        Coordinate tmp = new Coordinate( );
                        tmp.setX( Double.valueOf( line.get( 1 ) ) );
                        tmp.setY( Double.valueOf( line.get( 2 ) ) );
                        tmp.setZ( 0 );
                        texture_coordinates.add( tmp );
                    }
                    if (line.elementAt(0).equals("f") && line.size() == 4)
                    // f stands for face, so its time to create a Triangle
                    {
                        Integer a,b,c;
                        boolean texturez = false;
                        if( expand( line.get( 1 ), '/' ).size() == 1 )
                        {
                            a = Integer.valueOf(line.get(1));
                            b = Integer.valueOf(line.get(2));
                            c = Integer.valueOf(line.get(3).trim());
                        }
                        else
                        {
                            a = Integer.valueOf( expand( line.get( 1 ), '/' ).get( 0 ) );
                            b = Integer.valueOf( expand( line.get( 2 ), '/' ).get( 0 ) );
                            c = Integer.valueOf( expand( line.get( 3 ), '/' ).get( 0 ) );
                            texturez = true;
                        }
                        Triangle tmp = new Triangle();
                        tmp.a = coordinates.elementAt(a - 1);
                        tmp.b = coordinates.elementAt(b - 1);
                        tmp.c = coordinates.elementAt(c - 1);
                        tmp.material_name = cur_mtl;
                        tmp.material = materials.get( cur_mtl.trim() );
                       // if( tmp.material == null ) { System.out.println( "FUCK FUCK FUCK " + tmp.material_name ); }
                       // else{ System.out.println( "YAAAAY !!!!    :)" ); }
                        //System.out.println(tmp.mtl);
                        // check if there are any texture coordinates.
                        if( texturez )
                        {
                            Integer i = texture_coordinates.size();
                            tmp.ta = texture_coordinates.get( i - 1 );
                            tmp.tb = texture_coordinates.get( i - 2 );
                            tmp.tc = texture_coordinates.get( i - 3 );
                        }
                        triangles.add( tmp );
                    }
                }
            }
            System.out.println("Done Parsing.....");
            System.out.println("Calculating Normals..........");
            for( Triangle triangle : triangles )
            {
                triangle.normal = ANode.getNormal( triangle.a, triangle.b, triangle.c );
            }
            System.out.println("Done calculating normals........." );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            throw new ModelFile_ReadError( e );
        }

        System.out.println( coordinates.size() );
        System.out.println( triangles.size( ) );
    }

    /**
     *
     * @param name String name of the model to load
     */
    public ModelFile( String name )
    {
        this.name = name;
        init( );
    }
}
