package edu.ohiou.lev_neiman.sceneapi.edit.material_editor;

/**
 * <p>Title: JOGL stuff</p>
 *
 * <p>Description: Experimentation with JOGL</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import edu.ohiou.lev_neiman.sceneapi.edit.*;

public class MaterialEditorUI extends JFrame
{
    private MaterialEditorPanel panel = new MaterialEditorPanel( this );

    public MaterialEditorUI()
    {
        super( "MTL Editor" );
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        MaterialEditorUI modeleditorui = new MaterialEditorUI();
    }

    private void jbInit()
            throws Exception
    {
        super.setSize( new Dimension( 850, 650 ) );
        super.add( panel );
        super.setVisible( true );
    }

    public void exit( )
    {
        System.exit( 0 );
    }

    protected void processWindowEvent(WindowEvent e)
    {
        //super.processWindowEvent(e);
        if (e.getID()==WindowEvent.WINDOW_CLOSING)
        {
            System.exit( 0 );
        }
    }

}
