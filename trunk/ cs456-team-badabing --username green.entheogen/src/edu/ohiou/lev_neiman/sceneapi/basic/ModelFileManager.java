package edu.ohiou.lev_neiman.sceneapi.basic;

import java.util.*;


/**
 * <p>Title: class ModelFileManager</p>
 *
 * <p>Description: static Hashtable wrapper for ModelFiles associated by their names.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ModelFileManager
{
        /**
         * private static hashtable that relates String to ModelFile
         */
        private static Hashtable< String, ModelFile > models = new Hashtable< String, ModelFile>( );

        /**
         *
         * @param name String
         * @return ModelFile - mesh data
         */
        public static ModelFile getFile( String name )
        {
            ModelFile ret = null;
            ret = models.get( name );
            if( ret != null ) { return ret; }
            try
            {
                ret = new ModelFile(name);
            }
            catch( Exception e )
            {
                return null;
            }
            models.put( name, ret );
            return ret;
        }
}
