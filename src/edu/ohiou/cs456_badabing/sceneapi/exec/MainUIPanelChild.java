package edu.ohiou.cs456_badabing.sceneapi.exec;

/**
 * <p>Interface that should be implemented by all panels that wish to talk back to MainUIPanel.</p>
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
public interface MainUIPanelChild
{
    /**
    * add reference to MainUIPanel to this child object.  Class implementing this interface should allocate a private MainUIPanel variable to store the reference.
    * @param main_panel MainUIPanel
    */
    public void addMainPanel( MainUIPanel main_panel );

    /**
     * return reference to MainUIPanel that is stored by the child.
     * @return MainUIPanel
     */
    public MainUIPanel getMainPanel( );

}
