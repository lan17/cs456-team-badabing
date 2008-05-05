package edu.ohiou.lev_neiman.sceneapi.basic;



/**
 * <p>Instance of this class is thrown when ModelFile encounters error reading 3d Model File.</p>
 *
 * <p>Description: instance of this class is thrown when ModelFile encounters error reading 3d Model File.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class ModelFile_ReadError extends Exception
{
    public ModelFile_ReadError( Exception e )
    {
        super( e );
    }
}
