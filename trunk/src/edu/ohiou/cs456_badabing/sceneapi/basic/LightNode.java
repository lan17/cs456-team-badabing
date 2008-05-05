package edu.ohiou.cs456_badabing.sceneapi.basic;

import edu.ohiou.cs456_badabing.sceneapi.basic.*;

import java.awt.Frame;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import edu.ohiou.cs456_badabing.sceneapi.basic.*;
import edu.ohiou.cs456_badabing.sceneapi.lol.*;

/**
 * <p>Node that lights stuff.</p>
 *
 * <p>Description: Node that lights stuff.</p>
 *
 * <p>Copyright: Copyright (c) 2008, Lev A Neiman</p>
 *
 * <p>Company: Ohio University EECS</p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class LightNode
        extends ANode
{
    /**
    * ambience
    */
    public float ambient[]  = { 0.0f, 0.0f, 0.0f, 1f };

    /**
     * color or diffusion
     */
    public float diffuse[]  = { 1.0f, 1.0f, 1.0f, 1.0f };

    /**
     * specularity
     */
    public float specular[] = { 1.0f, 1.0f, 1.0f, 0.0f };

    /**
     * position
     */
    public float position[] = { 0.1f, 0.1f, 5.0f, 0.0f };

    /**
     * OpenGL int for this light they go from GL.GL_LIGHT0 to GL.LIGHT8
     */
    public int light_num = GL.GL_LIGHT0;

    /**
     * default constructor
     * @param num int - OpenGL int for this light.
     */
    public LightNode( int num )
    {
        light_num = num;
    }

    /**
     * applies this light to panel.
     * @param panel GLAutoDrawable
     */
    public void render( GLAutoDrawable panel )
    {
        super.render( panel );


        GL gl = panel.getGL();

        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(light_num);
        gl.glEnable(gl.GL_NORMALIZE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        gl.glLightfv(gl.GL_LIGHT0, gl.GL_AMBIENT,  ambient, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_DIFFUSE,  diffuse, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_SPECULAR, specular, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, position, 0);

    }


}
