package edu.ohiou.cs456_badabing.sceneapi.edit.material_editor;

/**
 * <p>Title: class MaterialEditorPanel</p>
 *
 * <p>Description: Panel for my material editor.</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.awt.*;
import java.awt.event.*;
import edu.ohiou.cs456_badabing.sceneapi.*;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;

import edu.ohiou.cs456_badabing.sceneapi.edit.*;

class DisplayRender implements GLEventListener
{

    ModelNode model;

    public void displayChanged(GLAutoDrawable gLDrawable,
  boolean modeChanged, boolean deviceChanged) {
}

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height)
    {
        final GL gl = gLDrawable.getGL();
        final GLU glu = new GLU( );
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display( GLAutoDrawable drawable )
    {

    }

    public void init( GLAutoDrawable drawable )
    {
        final GL gl = drawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,
        GL.GL_NICEST);
    }


}

public class MaterialEditorPanel extends JPanel implements ActionListener
{
    private ModelNode model;


    private ModelViewGLPanel render_panel = new ModelViewGLPanel();
    private java.awt.Frame parent_frame;

    FileChooser file_chooser = new FileChooser( );

    public MaterialEditorPanel( java.awt.Frame parent_frame )
    {
        this.parent_frame = parent_frame;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private void jbInit()
            throws Exception
    {
        render_panel.setPreferredSize( new Dimension( 800, 600 ) );
        super.setLayout( new BorderLayout( ) );

        super.add( new MenuBar( this ), BorderLayout.NORTH );
        super.add( render_panel );
        super.setSize( new Dimension( 800, 600 ) );
    }

    public void openFileActionPerformed( )
    {
        if( file_chooser.showOpenDialog( this ) == file_chooser.APPROVE_OPTION )
        {
            String file_name =  file_chooser.getSelectedFile().getAbsolutePath();
            model = null;
            ModelFile model_file = ModelFileManager.getFile( file_name );
            this.model = new ModelNode( model_file );

            render_panel.left = model_file.getXMin();
            render_panel.right = model_file.getXMax();
            render_panel.top = model_file.getYMax();
            render_panel.bottom = model_file.getYMin();
            render_panel.near = model_file.getZMin();
            render_panel.far = model_file.getZMax();
            render_panel.loadModel( model );

        }
    }

    public static String stripExtension( String input )
    {
        int i = input.length( ) - 1;
        while( i >= 0 && input.charAt( i ) != '.' ) i--;
        return input.substring( 0, i );
    }

    public void exitPerformed( )
    {
        ((MaterialEditorUI)parent_frame).exit( );
    }

    public void actionPerformed( ActionEvent e )
    {
        java.lang.String what = e.getActionCommand();
        if( what == MenuBar.open_file_text )
        {
            openFileActionPerformed( );
        }
        if( what == MenuBar.exit_text )
        {
            exitPerformed( );
        }

    }
}

