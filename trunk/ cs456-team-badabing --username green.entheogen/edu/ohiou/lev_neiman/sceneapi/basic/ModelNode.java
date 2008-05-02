package edu.ohiou.lev_neiman.sceneapi.basic;



import javax.media.opengl.*;
import java.util.*;
import edu.ohiou.lev_neiman.sceneapi.*;

/**
 * <p>Title: class ModelNode</p>
 *
 * <p>Description: wrapper class for ModelFile that can be put anywhere in the scene tree.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ModelNode extends ANode
{
        /**
         * handle on the ModelFile object which contains model data.
         */
        private ModelFile data = null;

        Vector< MtlNode > mtl_nodes = new Vector<MtlNode>();

        private void populateMtls( )
        {
            Set<String> names = data.mtl_groups.keySet();
            for( String name : names )
            {
                MtlNode tmp = new MtlNode( data.mtl_groups.get( name ) );
                mtl_nodes.add( tmp );
                addChild( tmp );
            }
        }

        /**
         * default constructor.
         * @param data ModelFile
         */
        public ModelNode( ModelFile data )
        {
            this.data = data;
            try
            {
                populateMtls();
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
        }

        /**
         * Overridden method from ANode.  calls super's version and then renders model.
         * @param gld GLAutoDrawable
         */
        public void render( GLAutoDrawable gld )
        {
            super.render( gld );
        }
}
