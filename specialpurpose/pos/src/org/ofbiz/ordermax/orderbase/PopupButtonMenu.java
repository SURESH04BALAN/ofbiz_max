/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.StatusValidChangeToDetail;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author BBS Auctions
 */
public abstract class PopupButtonMenu {

    public static final String moduleBase = OrderPopupButtonMenu.class.getName();

    public static JToggleButton createActionButtonItemToggle(String name) {
        final JToggleButton btnItem = new JToggleButton(name);
        btnItem.setFont(new Font("Tahoma", 0, 12));
        btnItem.setBorder(BorderFactory.createTitledBorder(""));
        //        btnItem.setContentAreaFilled(false);
        btnItem.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem.setHorizontalTextPosition(SwingConstants.LEFT);
        btnItem.addMouseListener(new MouseAdapter() {
            Font originalFont = null;

            @Override
            public void mouseEntered(MouseEvent evt) {
                originalFont = btnItem.getFont();
                Map attributes = originalFont.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                btnItem.setFont(originalFont.deriveFont(attributes));
                btnItem.setBackground(Color.GREEN);
            }

            public void mouseExited(MouseEvent evt) {
                btnItem.setFont(originalFont);
                btnItem.setBackground(UIManager.getColor("control"));
            }
        });
        return btnItem;
    }
    public JToggleButton popupButton = null;
    protected final JPanel popupPanel = new JPanel();
    protected JDesktopPane desktopPane;
    //  JFrame parentFrame;
    public JPopupMenu popupMenu = null;

    public PopupButtonMenu() {
    }

    public void decoratePopupMenuPanel(final JPanel panel) {
        Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty;

        blackline = BorderFactory.createLineBorder(Color.DARK_GRAY);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        empty = BorderFactory.createEmptyBorder();

        //Compound borders
        Border redline = BorderFactory.createLineBorder(Color.blue);

        //This creates a nice frame.
        //       Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        //Add a title to the red-outlined frame.
        Border compound = BorderFactory.createTitledBorder(empty, getName(),
                TitledBorder.CENTER,
                TitledBorder.BELOW_BOTTOM);

        panel.setBorder(compound);

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new GridLayout(0, 1));

    }

    public void createPopupMenu(final JPanel panel) {
        popupMenu = new JPopupMenu();

        popupMenu.add(panel);
        if (popupButton.getPreferredSize().width > popupMenu.getPreferredSize().width) {
            popupMenu.setPreferredSize(new Dimension(popupButton.getPreferredSize().width, popupMenu.getPreferredSize().height));
            popupMenu.setMinimumSize(new Dimension(popupButton.getPreferredSize().width, popupMenu.getPreferredSize().height));
        }//            popupButton.setMaximumSize(new Dimension(popupMenu.getPreferredSize().width, popupMenu.getPreferredSize().height));
        popupButton.removeAll();

        popupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (popupButton.isSelected()) {

                    /*if (popupOrder == null || popupOrder.getOrderId() == null || popupOrder.getOrderId().isEmpty() == true) {

                     int selection = OrderMaxOptionPane.showConfirmDialog(
                     null, "Order is not created yet. Please save the order", "Order : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                     popupButton.setSelected(false);
                     return;
                     }*/
                    popupMenu.pack();
                    Point pos = new Point();
                    // get the preferred size of the menu...
                    Dimension size = popupMenu.getPreferredSize();
                    // Adjust the x position so that the left side of the popup
                    // appears at the center of  the component
                    pos.x = 1;//(popupButton.getWidth() / 2);
                    // Adjust the y position so that the y postion (top corner)
                    // is positioned so that the bottom of the popup
                    // appears in the center
                    pos.y = /*(popupButton.getHeight() / 2)*/ 0 - size.height;
                    if (popupButton.getPreferredSize().width > popupMenu.getPreferredSize().width) {
                        popupMenu.setPreferredSize(new Dimension(popupButton.getPreferredSize().width, popupMenu.getPreferredSize().height));
                        popupMenu.setMinimumSize(new Dimension(popupButton.getPreferredSize().width, popupMenu.getPreferredSize().height));
                    }
                    popupMenu.show(popupButton, pos.x, pos.y);
                }
            }
        });

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
                Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                Point componentLoc = popupButton.getLocationOnScreen();
                mouseLoc.x -= componentLoc.x;
                mouseLoc.y -= componentLoc.y;
                //               if (!popupButton.contains(mouseLoc)) {
                popupButton.setSelected(false);
                Debug.logInfo("mouseLoc: " + mouseLoc, moduleBase);
                //               }
            }
        });
    }

    public abstract String getName();

    static public List<StatusValidChangeToDetail> getStatusValidChangeToDetail(String statusId, XuiSession session) {
        List<StatusValidChangeToDetail> details = new ArrayList<StatusValidChangeToDetail>();

        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();

            // approve the return
            Map<String, Object> svcRes = dispatcher.runSync("getStatusValidChangeToDetails", UtilMisc.toMap("userLogin", userLogin, "statusId", statusId));
            if (OrderMaxUtility.handleServiceReturn("Not Paid Payment", svcRes) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                List<GenericValue> statusList = null;
                if (svcRes.containsKey("statusValidChangeToDetails")) {
                    statusList = (List<GenericValue>) svcRes.get("statusValidChangeToDetails");
                    for (GenericValue status : statusList) {
                        details.add(new StatusValidChangeToDetail(status));
                    }
                }
            }
        } catch (GenericServiceException ex) {
            Logger.getLogger(PopupButtonMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PopupButtonMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }
}
