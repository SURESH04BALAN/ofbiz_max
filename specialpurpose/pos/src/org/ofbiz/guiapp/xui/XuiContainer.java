/**
 * *****************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * *****************************************************************************
 */
package org.ofbiz.guiapp.xui;

import com.openbravo.basic.BasicException;
import com.openbravo.format.Formats;
import com.openbravo.pos.config.JPanelConfiguration;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.scale.DeviceScale;
//import com.openbravo.pos.scale.DeviceScale;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.xoetrope.swing.XApplet;

import org.ofbiz.base.container.Container;
import org.ofbiz.base.container.ContainerConfig;
import org.ofbiz.base.container.ContainerException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.service.GenericDispatcher;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.pos.device.DeviceLoader;

public abstract class XuiContainer implements Container {

    public static final String module = XuiContainer.class.getName();
    protected static XuiSession xuiSession = null;

    protected String startupDir = null;
    protected String startupFile = null;
    protected String configFile = null;

    public void init(String[] args, String configFile) throws ContainerException {
        this.configFile = configFile;
    }

    protected void initUI() {
        Point p1 = null;
        Point p2 = null;
        for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            if (p1 == null) {
                p1 = gd.getDefaultConfiguration().getBounds().getLocation();
            } else if (p2 == null) {
                p2 = gd.getDefaultConfiguration().getBounds().getLocation();
            }

        }
        if (p2 == null) {
            p2 = new Point(p1);
            p2.x = 500;
        }

        if (xuiSession != null) {
            xuiSession.setScreenP1(p1);
            xuiSession.setScreenP2(p2);
        }
//        createFrameAtLocation(p1);
//        createFrameAtLocation(p2);
    }

    public boolean start() throws ContainerException {
        // make sure the subclass sets the config name
        if (this.getContainerConfigName() == null) {
            throw new ContainerException("Unknown container config name");
        }
        // get the container config
        ContainerConfig.Container cc = ContainerConfig.getContainer(this.getContainerConfigName(), configFile);
        if (cc == null) {
            throw new ContainerException("No " + this.getContainerConfigName() + " configuration found in container config!");
        }

        // get the delegator
        String delegatorName = ContainerConfig.getPropertyValue(cc, "delegator-name", "default");
        Delegator delegator = null;
        delegator = DelegatorFactory.getDelegator(delegatorName);

        // get the dispatcher
        String dispatcherName = ContainerConfig.getPropertyValue(cc, "dispatcher-name", "xui-dispatcher");
        LocalDispatcher dispatcher = GenericDispatcher.getLocalDispatcher(dispatcherName, delegator);

        // get the pre-defined session ID
        String xuiSessionId = ContainerConfig.getPropertyValue(cc, "xui-session-id", null);
        if (UtilValidate.isEmpty(xuiSessionId)) {
            throw new ContainerException("No xui-session-id value set in " + this.getContainerConfigName() + "!");
        }

        String laf = ContainerConfig.getPropertyValue(cc, "look-and-feel", null);
        if (UtilValidate.isNotEmpty(laf)) {
            try {
            //    UIManager.setLookAndFeel(laf);
            } catch (Exception e) {
                throw new ContainerException(e);
            }
        }

        /*        try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(XuiContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(XuiContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(XuiContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(XuiContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         */
        // create and cache the session
        xuiSession = new XuiSession(xuiSessionId, delegator, dispatcher, this);

        // configure the rest of the container
        this.configure(cc);

        // load the XUI and render the initial screen
        if (this.startupFile == null) {
            this.startupDir = ContainerConfig.getPropertyValue(cc, "startup-directory", "specialpurpose/pos/config/");
            this.startupFile = ContainerConfig.getPropertyValue(cc, "startup-file", "xpos.properties");
        }
//        String startMode = this.startupFile = ContainerConfig.getPropertyValue(cc, "startup-file", "xpos.properties");;

        String classPackageName = ContainerConfig.getPropertyValue(cc, "class-package-name", "net.xoetrope.swing");
        // try {
            /*
         Debug.logInfo("ClientEditor", module);     
         //        new ClientEditor().setVisible(true);   
         GenericValue mgrUl = null;
         try {
         mgrUl = xuiSession.checkLogin("1" ,"1");
         } catch (XuiSession.UserLoginFailure e) {
         //                        output.print(e.getMessage());
         //                        input.clear();
         }
         if (mgrUl != null) {
         boolean isMgr = xuiSession.hasRole(mgrUl, "MANAGER");
         if (!isMgr) {
         Debug.logInfo("PosUserNotManager",module);
         //                            input.clear();
         } else {
         Debug.logInfo("PosUser a Manager",module);                            
         //                          ManagerEvents.mgrLoggedIn = true;
         //                        pos.showPage("mgrpanel");
         }
         }
                    
         /*                javax.swing.JFrame frame = new javax.swing.JFrame("Client Editor");
         frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         frame.getContentPane().add(new ClientEditor());
         frame.pack();
         frame.setVisible(true);
         */
//            try {
/*


         } catch (XuiSession.UserLoginFailure ex) {
         Debug.logError(ex, module);
         }
         */
        String startupProperties = this.startupDir + this.startupFile;
        Debug.logInfo("startupProperties: " + startupProperties, module);
        String startMode = UtilProperties.getPropertyValue(startupProperties, "StartMode");
        //startMode = "ASK";
        if ("ASK".equalsIgnoreCase(startMode)) {

            String[] choices = {"CONFIGURE POS", "RUN POS", "RUN ERP"};
            String mode = (String) JOptionPane.showInputDialog(null, "Please choose one...",
                    "Which mode to run", JOptionPane.QUESTION_MESSAGE, null, // Use
                    // default
                    // icon
                    choices, // Array of choices
                    choices[1]); // Initial choice

            if ("CONFIGURE POS".equalsIgnoreCase(mode)) {
                startMode = "CPOS";
            } else if ("RUN POS".equalsIgnoreCase(mode)) {
                startMode = "POS";
            } else {
                startMode = "ERP";
            }
            /*int selection = OrderMaxOptionPane.showConfirmDialog(
             null, "Do you want to Start POS?", "Start Option", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
             if (selection == JOptionPane.YES_OPTION) {
             startMode = "POS";
             }*/
        }

        if ("CPOS".equalsIgnoreCase(startMode)) {
            showConfig();
        } else if ("POS".equalsIgnoreCase(startMode)) {
            JFrame jframe1 = new javax.swing.JFrame();//OrderMaxPosMainForm();
            DeviceLoader.deviceScale = new DeviceScale(jframe1, xuiSession.getAppConfig());
            //ControllerOptions.setMainApp(jframe);
            jframe1.setUndecorated(true);
             changeLAF(laf, jframe1);
            initUI();
            new XuiScreen(new String[]{this.startupDir + this.startupFile, classPackageName}, jframe1);
        } else {
            try {
                //                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                xuiSession.login("admin", "ofbiz");
                OrderMaxMainForm frame = new OrderMaxMainForm(xuiSession);
                ControllerOptions.setMainApp(frame);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationRelativeTo(null);
                //su changeLAF(laf, frame);
                frame.setVisible(true);

                //DeviceLoader.deviceScale = new DeviceScale(frame, xuiSession.getAppConfig());
            } catch (XuiSession.UserLoginFailure ex) {
                Logger.getLogger(XuiContainer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return true;
    }

    public void stop() throws ContainerException {
    }

    public String getXuiPropertiesName() {
        return this.startupFile;
    }

    /**
     * @return String the name of the container name property
     */
    public abstract String getContainerConfigName();

    /**
     * Implementation specific configuration from the container config This
     * method is called after the initial XUI configuration, after the session
     * creation; before the initial screen is rendered.
     *
     * @param cc The container config object used to obtain the information
     * @throws ContainerException
     */
    public abstract void configure(ContainerConfig.Container cc) throws ContainerException;

    public static XuiSession getSession() {
        return xuiSession;
    }

    @SuppressWarnings("serial")
    class XuiScreen extends XApplet {

        protected String startupProperties = "";

        public XuiScreen(String[] args, JFrame frame) {
            super(args, frame);
            if (args.length > 0) {
                startupProperties = args[0];
            }
            String languageSuffix = UtilProperties.getPropertyValue("xui.properties", "languageSuffix", "");
            String suffix = null;
            if (UtilValidate.isEmpty(languageSuffix)) {
                suffix = Locale.getDefault().getLanguage();
            } else {
                suffix = languageSuffix;
            }
            if ("en".equals(suffix)) {
                suffix = "";
            } else {
                suffix = "_" + suffix;
            }

            String language = UtilProperties.getPropertyValue(startupProperties, "Language");
            if (language.compareTo("XuiLabels" + suffix) != 0) {
                UtilProperties.setPropertyValue(startupProperties, "Language", "XuiLabels" + suffix);
            }
            if (suffix.equals("_zh")) { // TODO maybe needed for other languages using non Latin alphabet http://en.wikipedia.org/wiki/Alphabet#Types
                UtilProperties.setPropertyValue(startupProperties, "StyleFile", "posstyles" + suffix + ".xml"); // For the moment only a Chinese StyleFile is provided
            } else {
                UtilProperties.setPropertyValue(startupProperties, "StyleFile", "posstyles.xml"); // Languages using Latin alphabet
            }
            frame.setVisible(true);
            frame.getContentPane().add(this);
            frame.validate();
        }
    }

    static public void appConfig() {
        String[] str = null;
        //AppConfig
        AppConfig config = new AppConfig(str);
        config.load();

        // set Locale.
        String slang = config.getProperty("user.language");
        String scountry = config.getProperty("user.country");
        String svariant = config.getProperty("user.variant");
        if (slang != null && !slang.equals("") && scountry != null && svariant != null) {
            Locale.setDefault(new Locale(slang, scountry, svariant));
        }

        // Set the format patterns
        Formats.setIntegerPattern(config.getProperty("format.integer"));
        Formats.setDoublePattern(config.getProperty("format.double"));
        Formats.setCurrencyPattern(config.getProperty("format.currency"));
        Formats.setPercentPattern(config.getProperty("format.percent"));
        Formats.setDatePattern(config.getProperty("format.date"));
        Formats.setTimePattern(config.getProperty("format.time"));
        Formats.setDateTimePattern(config.getProperty("format.datetime"));

        // Set the look and feel.
        try {

            Object laf = Class.forName(config.getProperty("swing.defaultlaf")).newInstance();

            if (laf instanceof LookAndFeel) {
              //su  UIManager.setLookAndFeel((LookAndFeel) laf);
            } //else if (laf instanceof SubstanceSkin) {
            //    SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
            //}
        } catch (Exception e) {
            Debug.logError(e, "module");
        }

        XuiContainer.getSession().setAppConfig(config);

        // Inicializamos la bascula
        //    m_Scale = new DeviceScale(this, config);
    }

    static public void showConfig() {

        int y = 10;
        int x = 150;

        //load config files
        XuiContainer.appConfig();

        JPanelConfiguration pannConfig = new JPanelConfiguration(ControllerOptions.getSession().getAppConfig());

        try {
            pannConfig.activate();
        } catch (BasicException ex) { // never thrown ;-)

        }

        //JFrame f = new JFrame();
        // f.setSize(1000, 700);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // display/center the jdialog when the button is pressed
        final JDialog d = new JDialog(new JFrame(), "Pos Configuration ", true);
        d.setSize(1000, 700);
        //d.setLocationRelativeTo(f);
        d.add(pannConfig);
        d.setLocation(x, y);
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        pannConfig.btnClose.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                d.dispose();
            }
        });

        d.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        //f.setVisible(true);
        d.setVisible(true);
        //System.exit(0);

    }

    private void changeLAF(final String laf, final Component comp) {

        //final LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        if (laf != null && !laf.equals(UIManager.getLookAndFeel().getClass().getName())) {
            // The selected look and feel is different from the current look and feel.
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    try {
                        String lafname = laf;
                        Object laf = Class.forName(lafname).newInstance();

                        if (laf instanceof LookAndFeel) {
                           UIManager.setLookAndFeel((LookAndFeel) laf);
                        } /*else if (laf instanceof SubstanceSkin) {
                         SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                         }*/

                        SwingUtilities.updateComponentTreeUI(comp);
                    } catch (Exception e) {
                    }
                }
            });
        }
    }
}
//($V{totalinvoiceCost}.equals( BigDecimal.ZERO ) ||  $V{totalProfit}.equals( BigDecimal.ZERO )) ? BigDecimal.ZERO : $V{totalinvoiceCost}.divide( $V{totalProfit} )
