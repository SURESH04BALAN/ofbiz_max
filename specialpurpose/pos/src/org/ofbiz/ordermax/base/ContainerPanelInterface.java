/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author administrator
 */
public interface ContainerPanelInterface {

    static public enum ContainerType {

        OnePanelNonSizableContainer,
        TwoPanelNonSizableContainer,
        ThreePanelContainer,
        TwoPanelContainer,
        ThreePanelInternalContainer,
        TwoPanelInternalContainer,
        ThreePanelDialogContainer,
        TwoPanelDialogContainer,
        MDIFrameContainer
    };

    static public enum ReturnStausType {
        RET_CANCEL,
        RET_OK
    };

    
    List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener);

    public void removeActionListener(ActionListener listener);

    public JPanel getPanelDetail();

    public JPanel getPanelSelecton();

    public JPanel getPanelButton();

    public JPanel getPanelParent();

    public void setVisible(boolean value);

    public void okButtonPressed();

    public void cancelButtonPressed();

    public void setDividerLocation(int value);

    public void setCaption(String title);

    public ContainerType getContainerType();
}
