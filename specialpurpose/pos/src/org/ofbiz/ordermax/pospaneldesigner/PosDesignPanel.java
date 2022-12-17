/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.xml.sax.SAXException;

/**
 *
 * @author siranjeev
 */
public class PosDesignPanel extends javax.swing.JPanel {

    /**
     * Creates new form PosDesignPanel
     */
    public PosDesignPanel() {
        initComponents();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        txtFilePath.setText(s + "\\specialpurpose\\pos\\screens\\default\\menu");
        txtPageFilePath.setText(s + "\\specialpurpose\\pos\\screens\\default");
        //"C:\AuthLog\server\finalpos\specialpurpose\pos\config\"
        //"C:\AuthLog\server\finalpos\specialpurpose\pos\styles\"
        txtEventFilePath.setText(s + "\\specialpurpose\\pos\\config\\");
        txtButtonStyleFilePath.setText(s + "\\specialpurpose\\pos\\styles\\");
        System.out.println("Current relative path is: " + s);
    }

    public static void main(String[] args) {

        final PosDesignPanel example = new PosDesignPanel();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    example.createAndShowGUI();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
//    protected JPanel panel;
    protected File file;
    protected XmlPosButtonReader engine;
    protected XmlPosButtonEventReader eventReader;
    protected XmlPosButtonStyleReader styleReader = new XmlPosButtonStyleReader();

    PosButton currSelBtn = null;
    JPanel productCardPanel = null;
    SkuButtonDesignPanel skuPanel = null;
    SystemButtonDesignPanel systemPanel = null;
    ActionButtonDesignPanel actionPanel = null;
    String skuName = "SKU";
    String systemName = "SYSTEM";
    String actionName = "ACTION";
    ButtonDesignPanelInterface panelInterface;
    ProductPosCardPanel posButtonsPanel;

    public void createAndShowGUI() throws ParserConfigurationException, SAXException, IOException {
        JFrame frame = new JFrame("Button Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboButtonType.addItem(skuName);
        comboButtonType.addItem(systemName);
        comboButtonType.addItem(actionName);

        productCardPanel = new JPanel(new CardLayout());
        skuPanel = new SkuButtonDesignPanel();
        panelInterface = skuPanel;
        productCardPanel.add(skuPanel, skuName);
        //action
        actionPanel = new ActionButtonDesignPanel();
        productCardPanel.add(actionPanel, actionName);

        //system
        systemPanel = new SystemButtonDesignPanel();
        productCardPanel.add(systemPanel, systemName);

        addAPanelGrid(productCardPanel, jPanel8);
        showCard(skuName);

        loadXmlFileButton(false);
//        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        createTwoDimensionArray();

        //      frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.getContentPane().setPreferredSize(new Dimension(900, 700));
//buttonPanel
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.pack();
        loadPageFiles(new File(txtFilePath.getText()), comboPageName, false);
        loadPageFiles(new File(txtPageFilePath.getText()), actionPanel.comboActionPage, true);
        systemPanel.loadEventFile(txtEventFilePath.getText() + "buttonevents.xml");
        loadButtonStyleFile(txtButtonStyleFilePath.getText() + "posstyles.xml");

        posButtonsPanel = new ProductPosCardPanel("test", newArrayContent, action, rows, columns, styleReader.styleList);
        buttonPanel.add(posButtonsPanel, BorderLayout.CENTER);

        skuPanel.setStyles(styleReader.styleList);
        systemPanel.setStyles(styleReader.styleList);
        actionPanel.setStyles(styleReader.styleList);
//        jButton3ActionPerformed(null);
//        jButton5ActionPerformed(null);
        frame.setVisible(true);

    }

    public void createGUI() throws ParserConfigurationException, SAXException, IOException {
//        JFrame frame = new JFrame("Button Example");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboButtonType.addItem(skuName);
        comboButtonType.addItem(systemName);
        comboButtonType.addItem(actionName);

        productCardPanel = new JPanel(new CardLayout());
        skuPanel = new SkuButtonDesignPanel();
        panelInterface = skuPanel;
        productCardPanel.add(skuPanel, skuName);
        //action
        actionPanel = new ActionButtonDesignPanel();
        productCardPanel.add(actionPanel, actionName);

        //system
        systemPanel = new SystemButtonDesignPanel();
        productCardPanel.add(systemPanel, systemName);

        addAPanelGrid(productCardPanel, jPanel8);
        showCard(skuName);

        loadXmlFileButton(false);

        createTwoDimensionArray();

        //      frame.getContentPane().add(panel, BorderLayout.SOUTH);
//        frame.getContentPane().setPreferredSize(new Dimension(900, 700));
//buttonPanel
//        frame.getContentPane().add(this, BorderLayout.CENTER);
///        frame.pack();
        loadPageFiles(new File(txtFilePath.getText()), comboPageName, false);
        loadPageFiles(new File(txtPageFilePath.getText()), actionPanel.comboActionPage, true);
        systemPanel.loadEventFile(txtEventFilePath.getText() + "buttonevents.xml");
        loadButtonStyleFile(txtButtonStyleFilePath.getText() + "posstyles.xml");

        posButtonsPanel = new ProductPosCardPanel("test", newArrayContent, action, rows, columns, styleReader.styleList);
        buttonPanel.add(posButtonsPanel, BorderLayout.CENTER);

        skuPanel.setStyles(styleReader.styleList);
        systemPanel.setStyles(styleReader.styleList);
        actionPanel.setStyles(styleReader.styleList);
//        jButton3ActionPerformed(null);
//        jButton5ActionPerformed(null);
//        frame.setVisible(true);

    }

    public void loadButtonStyleFile(String filePath) {
        try {

            SAXParserFactory spfac1 = SAXParserFactory.newInstance();
            SAXParser sp1 = spfac1.newSAXParser();
            try {
                sp1.parse(new File(filePath), styleReader);
                styleReader.readList();

            } catch (IOException ex) {
                Logger.getLogger(SystemButtonDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SystemButtonDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SystemButtonDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadPageFiles(File filePath, javax.swing.JComboBox pageCombo, Boolean removeExt) {
        if (filePath == null) {
            return;
        }
        // create new filename filter
        FilenameFilter fileNameFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.lastIndexOf('.') > 0) {
                    // get last index for '.' char
                    int lastIndex = name.lastIndexOf('.');

                    // get extension
                    String str = name.substring(lastIndex);

                    // match path name extension
                    if (str.equals(".xml")) {
                        return true;
                    }
                }
                return false;
            }
        };

        // returns pathnames for files and directory
        File[] paths = filePath.listFiles(fileNameFilter);
        pageCombo.removeAllItems();
        // for each pathname in pathname array
        for (File path : paths) {
            // prints file and directory paths
            System.out.println(path);
            String name = path.getName();
            if (removeExt) {
                name = name.substring(0, name.lastIndexOf('.'));
            }
            pageCombo.addItem(name);
        }
    }

    public static void addAPanelGrid(JPanel childPanel, JPanel container) {

        container.setLayout(new java.awt.GridLayout(0, 1, 5, 5));
        container.add(childPanel);
    }

    public void showCard(String desc) {
        Debug.logInfo("showCard: " + desc, "module");
        if (productCardPanel != null) {
            Debug.logInfo("showCard productCardPanel: " + desc, "module");
            CardLayout cl = (CardLayout) (productCardPanel.getLayout());
            cl.show(productCardPanel, desc);
            if (desc.equalsIgnoreCase(skuName)) {
                panelInterface = skuPanel;
            } else if (desc.equalsIgnoreCase(actionName)) {
                panelInterface = actionPanel;
            } else if (desc.equalsIgnoreCase(systemName)) {
                panelInterface = systemPanel;
            }
        }
    }

    public void setSelectionData(String id, String name) {
        panelInterface.setSelectionData(id, name);
    }

    protected boolean isExistsInTheList(String name) {
        for (int i = 0; i < engine.cardList.size(); ++i) {
            PosButton posBtn = engine.cardList.get(i);
            if (UtilValidate.isNotEmpty(posBtn.getId()) && posBtn.getId().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void loadXmlFileButton(boolean shuffle) throws ParserConfigurationException, SAXException, IOException {

        if (file == null) {
            return;
        }

        if (engine != null && shuffle == true) {
            engine.shuffleList(1);
        } else {
            engine = new XmlPosButtonReader();

            SAXParserFactory spfac = SAXParserFactory.newInstance();
            SAXParser sp = spfac.newSAXParser();
            sp.parse(file, engine);
            engine.readList();
        }

        Collections.sort(engine.cardList, new PosButtonXComparator());
        Collections.sort(engine.cardList);
    }

    int calcRow(int y) {
        int row = (y + 2) / defaultRowHeight;
        return row;
    }

    int calcCol(int x) {
        int col = (x + 2) / defaultColWidth;
        return col;
    }

    private PosButton[][] newArrayContent = null;
    private int rows = 7;
    protected int columns = 6;
    protected int defaultRowHeight = 58;
    protected int defaultColWidth = 100;
    protected int defaultVGap = 1;
    protected int defaultHGap = 1;

    protected PosButton createDefaultPosButton(int row, int col) {
        PosButton posBtn = new PosButton();
        posBtn.setId(String.valueOf(row * columns + col));
        posBtn.setName(String.valueOf(row * columns + col));
        posBtn.setTextOne(posBtn.getName());
        posBtn.setRow(row);
        posBtn.setCol(col);
        int h = getMaximumRowHeight(row);
        int w = getMaximumColumnWidth(col);
        int y = getTopPosition(row);
        int x = getLeftPosition(col);
        posBtn.setH(h);
        posBtn.setW(w);
        posBtn.setY(y);
        posBtn.setX(x);
        posBtn.setAlignment("Center");
        posBtn.setStyle("posButton");
        posBtn.setX(x);

        return posBtn;
    }

    protected void resetPosButton(PosButton posBtn) {
        int row = posBtn.getRow();
        int col = posBtn.getCol();
        posBtn.setId(String.valueOf(row * columns + col));
        posBtn.setName(String.valueOf(row * columns + col));
        posBtn.setTextOne(posBtn.getName());
        posBtn.setContent("");
        int h = getMaximumRowHeight(row);
        int w = getMaximumColumnWidth(col);
        int y = getTopPosition(row);
        int x = getLeftPosition(col);
        posBtn.setH(h);
        posBtn.setW(w);
        posBtn.setY(y);
        posBtn.setX(x);
        posBtn.setAlignment("Center");
        posBtn.setStyle("posButton");
        posBtn.setX(x);
        posBtn.setEmpty(Boolean.TRUE);
    }

    protected void resetButtonBounds() {

        for (int r = 0; r < rows; r++) {
            for (int z = 0; z < columns; z++) {

                PosButton posBtn = newArrayContent[r][z];

                int h = getMaximumRowHeight(r);
                int w = getMaximumColumnWidth(z);
                int y = getTopPosition(r);
                int x = getLeftPosition(z);
                posBtn.setH(h);
                posBtn.setW(w);
                posBtn.setY(y);
                posBtn.setX(x);
            }
        }
    }

    protected void resetButtonBoundsToDefault() {

        for (int r = 0; r < rows; r++) {
            for (int z = 0; z < columns; z++) {

                PosButton posBtn = newArrayContent[r][z];

                int h = defaultRowHeight;
                int w = defaultColWidth;
                int y = r * (defaultRowHeight + defaultVGap);
                int x = z * (defaultColWidth + defaultHGap);
                posBtn.setH(h);
                posBtn.setW(w);
                posBtn.setY(y);
                posBtn.setX(x);
            }
        }
    }

    private int getTopPosition(int row) {
        int y = 0;
        for (int i = 0; i < row; ++i) {
            y += getMaximumRowHeight(i) + defaultVGap;
        }
        return y;
    }

    private int getLeftPosition(int col) {
        int y = 0;
        for (int i = 0; i < col; ++i) {
            y += getMaximumColumnWidth(i) + defaultHGap;
        }
        return y;
    }

    class BorderPainter extends AbstractBorder {

        JButton button = null;

        public BorderPainter(JButton button) {
            this.button = button;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2.setPaint(new GradientPaint(0, 10, Color.RED, 10, 10, Color.RED, true));
            // We are free to paint wherever we want :-) 
            g2.fillRect(0, 0, button.getWidth(), button.getHeight());
        }
    }
    JButton previousButton = null;

    private void createTwoDimensionArray() {
        newArrayContent = new PosButton[rows][columns];
        for (int x = 0; x < rows; x++) {
            for (int z = 0; z < columns; z++) {
                PosButton posBtn = createDefaultPosButton(x, z);
                final JButton button = new JButton(posBtn.getName());
                final String test = posBtn.getName() + "[x=" + posBtn.getX() + "," + "y=" + posBtn.getY() + "]";
                posBtn.setButton(button);
                button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        Border oldBorder = button.getBorder();
                        if (previousButton != null) {
                            previousButton.setBorder(oldBorder);
                        }
                        button.setBorder(new CompoundBorder(oldBorder, new BorderPainter(button)));
                        previousButton = button;
                    }
                });
                button.setBounds(posBtn.getX(), posBtn.getY(), posBtn.getW(), posBtn.getH());
                newArrayContent[x][z] = posBtn;
            }
        }

        if (engine != null && engine.cardList != null) {
//            rows = (engine.cardList.size() / columns) + 1;
//            int numberOfContacts = arrlist.size() / columns;
            //lets put the valid buttons
            for (int i = 0; i < engine.cardList.size(); ++i) {
                PosButton posBtn = engine.cardList.get(i);
                int row = calcRow(posBtn.getY());
                int col = calcCol(posBtn.getX());// + 2) / defaultColWidth;
                if (col < columns && row < rows) {
                    if (newArrayContent[row][col].isEmpty() == false) {
                        col++;
                    }

                    if (newArrayContent[row][col].isEmpty() == false) {
                        row++;
                    }

                    newArrayContent[row][col] = posBtn;
                    posBtn.setRow(row);
                    posBtn.setCol(col);
//                    posBtn.setId(col);
                    posBtn.setId(String.valueOf(row * columns + col));
                    JButton button = new JButton(posBtn.getName());
                    final String test = "Id: " + posBtn.getId() + ": " + posBtn.getName() + "[x=" + posBtn.getX() + "," + "y=" + posBtn.getY() + "]";
                    System.out.println(test);
                    posBtn.setButton(button);
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
//                            text.setText(test);
                        }
                    });
                    button.setBounds(posBtn.getX(), posBtn.getY(), posBtn.getW(), posBtn.getH());
                    newArrayContent[row][col] = posBtn;
                    System.out.println(newArrayContent[row][col].toString());
                    posBtn.setEmpty(false);
                    //putItNextVacantSpot(row, col, posBtn);
                }
            }
        }
    }

    private String[] COLUMN_NAMES = null;//new String[]{"Button1", "Button2", "Button3", "Button4", "Button5", "Button6"};

    private void createColumnNames() {
        COLUMN_NAMES = new String[columns];
        for (int i = 0; i < columns; i++) {
            COLUMN_NAMES[i] = "Button" + i;
        }
    }

    private int getMaximumRowHeight(int row) {
        int rowHeight = 0;
        if (row < rows) {
            for (int col = 0; col < columns; col++) {
                PosButton posbtn = newArrayContent[row][col];
                if (posbtn != null && posbtn.getH() > rowHeight) {
                    rowHeight = posbtn.getH();
                }
            }
        }
        if (rowHeight == 0) {
            rowHeight = defaultRowHeight;
        }
        return rowHeight;
    }

    private int getMaximumColumnWidth(int col) {
        int colWidth = 0;
        if (col < columns) {
            for (int row = 0; row < rows; row++) {
                PosButton posbtn = newArrayContent[row][col];
                if (posbtn != null && posbtn.getW() > colWidth) {
                    colWidth = posbtn.getW();
                }
            }
        }
        if (colWidth == 0) {
            colWidth = defaultColWidth;
        }
        return colWidth;
    }

    public void createPosDesignTable() {

        createTwoDimensionArray();
        createColumnNames();
    }

    String selProdId = null;
    Action productSelectAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {

            selProdId = e.getActionCommand();
            ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
            action.actionPerformed(event);
        }
    };
    //private Action action;
    final Action action = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            selProdId = e.getActionCommand();
            //           text.setText(selProdId);

            for (int x = 0; x < rows; x++) {
                for (int z = 0; z < columns; z++) {
                    PosButton posBtn = newArrayContent[x][z];
                    if (posBtn.getName().equals(selProdId)) {
                        currSelBtn = posBtn;
                        if (currSelBtn.isEmpty() == false) {
                            String val = currSelBtn.getName();
                            int index = val.indexOf(".");
                            if (index != -1) {
                                if (val.substring(0, index).equalsIgnoreCase(skuName)) {
                                    val = skuName;
                                } else {
                                    val = actionName;
                                }
                            } else {
                                val = systemName;
                            }

                            //showCard(val);
                            comboButtonType.setSelectedItem(val);
                        }
                        panelInterface.setPanelFields(posBtn);

                        return;
                    }
                }
            }
        }
    };

    public void createPosDesignPanel() {

//        createTwoDimensionArray();
//        ProductPosCardPanel panel = new ProductPosCardPanel("Test.XML", newArrayContent, action, rows, columns);
//        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    class PosButtonXComparator implements Comparator<PosButton> {

        public int compare(PosButton chair1, PosButton chair2) {
            return chair1.getX() - chair2.getX();
        }
    }

    public File getButtonFilePath(String choosertitle, String name) {
        File fileName = null;

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new java.io.File(name));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile();
        } else {
            System.out.println("No Selection ");
        }
        return fileName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnSaveAs = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboButtonType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        comboPageName = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtFilePath = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtPageFilePath = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtEventFilePath = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtButtonStyleFilePath = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtRowHeight = new javax.swing.JTextField();
        txtColumnWidth = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnUpdateBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        buttonPanel.setLayout(new java.awt.BorderLayout());
        add(buttonPanel, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(1038, 200));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(160, 148));

        jButton1.setText("Set");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnSaveAs.setText("Save As");
        btnSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAsActionPerformed(evt);
            }
        });

        jButton4.setText("Remove All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveAs)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel1.setPreferredSize(new java.awt.Dimension(1031, 154));
        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(878, 40));

        jLabel2.setText("Button Type:");

        comboButtonType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboButtonTypeActionPerformed(evt);
            }
        });

        jLabel1.setText("Menu Name:");

        comboPageName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPageNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboButtonType, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboPageName, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(252, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboButtonType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(comboPageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(115, 115, 115))
        );

        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel3.add(jPanel1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab1", jPanel3);

        jPanel2.setLayout(new java.awt.BorderLayout());

        txtFilePath.setText("\\specialpurpose\\pos\\screens\\default\\menu");

        jButton3.setText("Set Menu Directory");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setText("Menu Locations:");

        jLabel12.setText("Page Locations:");

        txtPageFilePath.setText("\\specialpurpose\\pos\\screens\\default");

        jButton5.setText("Set Page Directory");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel13.setText("Event File Location:");

        txtEventFilePath.setText("\\specialpurpose\\pos\\config\\");
            txtEventFilePath.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtEventFilePathActionPerformed(evt);
                }
            });

            jButton6.setText("Set Event File");
            jButton6.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton6ActionPerformed(evt);
                }
            });

            jLabel14.setText("Style Sheet Location:");

            txtButtonStyleFilePath.setText("specialpurpose\\pos\\styles\\");

                jButton7.setText("Set Style Sheet");
                jButton7.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton7ActionPerformed(evt);
                    }
                });

                jLabel3.setText("Row Height:");

                txtRowHeight.setText("58");

                txtColumnWidth.setText("100");

                jLabel4.setText("Column Width:");

                btnUpdateBtn.setText("Update");
                btnUpdateBtn.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnUpdateBtnActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel3))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtRowHeight, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtColumnWidth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateBtn)
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPageFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEventFilePath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(txtButtonStyleFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton3, jButton5, jButton6});

                jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jLabel11))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPageFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jButton5)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtRowHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdateBtn))))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEventFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtButtonStyleFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(jButton7)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtColumnWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                );

                jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

                jTabbedPane1.addTab("tab2", jPanel2);

                add(jTabbedPane1, java.awt.BorderLayout.PAGE_END);
            }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        panelInterface.getPanelFields(currSelBtn);
        posButtonsPanel.updateCurrSelection();
        if (isExistsInTheList(currSelBtn.getId()) == false) {
            engine.cardList.add(currSelBtn);
        }
        currSelBtn.setEmpty(Boolean.FALSE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            // resetButtonBounds();
            resetButtonBoundsToDefault();
            XMLPageWriter configFile = new XMLPageWriter(engine);
            String str = txtFilePath.getText() + "\\" + comboPageName.getSelectedItem().toString();
            int result = JOptionPane.OK_OPTION;
            if (BaseHelper.isFileExists(str)) {
                result = OrderMaxOptionPane.showConfirmDialog(null, "Do you want to overwrite the file " + comboPageName.getSelectedItem().toString() + "?", "Save File",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            if (result == JOptionPane.OK_OPTION) {
                System.out.println("Save path: " + str);

                configFile.setFile(str);
                configFile.saveConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void comboButtonTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboButtonTypeActionPerformed
        // TODO add your handling code here:
        showCard(comboButtonType.getSelectedItem().toString());
        if (panelInterface != null && currSelBtn != null && currSelBtn.isEmpty() == true) {
            panelInterface.setPanelFields(currSelBtn);
        }

    }//GEN-LAST:event_comboButtonTypeActionPerformed

    private void btnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAsActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new java.io.File(txtFilePath.getText()));
        fileChooser.setDialogTitle("Save as");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                String fileName = file.getCanonicalPath();
                int lastIndex = fileName.lastIndexOf('.');
                if (lastIndex == -1) {
                    fileName += ".xml";
                }
                XMLPageWriter configFile = new XMLPageWriter(engine);
                configFile.setFile(fileName);
                // resetButtonBounds();
                resetButtonBoundsToDefault();
                configFile.saveConfig();
                //(txtPageFilePath.getText(), fileName + "Panel", fileName);
                String fileName1 = file.getName();
                String name = file.getName();
                lastIndex = name.lastIndexOf('.');
                if (lastIndex == -1) {
                    fileName1 += ".xml";
                } else {
                    name = name.substring(0, lastIndex);
                }
                fileName1 = "panel" + fileName1;
                PosPageCreateClass pageCreate = new PosPageCreateClass(txtPageFilePath.getText(), fileName1, name);
                pageCreate.savePageFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // save to file
        }

    }//GEN-LAST:event_btnSaveAsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

//        panelInterface.getPanelFields(currSelBtn);
        if (isExistsInTheList(currSelBtn.getId()) == true) {
            engine.cardList.remove(currSelBtn);
            resetPosButton(currSelBtn);

        }
        posButtonsPanel.updateCurrSelection();
        currSelBtn.setEmpty(Boolean.TRUE);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new java.io.File(txtEventFilePath.getText()));
        chooser.setDialogTitle("Button Event File");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File filePath = chooser.getSelectedFile();

            try {
                String fileName = filePath.getCanonicalPath();
                txtEventFilePath.setText(fileName);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No Selection ");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        File filePath = getButtonFilePath("original", txtPageFilePath.getText());
        txtPageFilePath.setText(filePath.getPath());

        // create new filename filter
        FilenameFilter fileNameFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.lastIndexOf('.') > 0) {
                    // get last index for '.' char
                    int lastIndex = name.lastIndexOf('.');

                    // get extension
                    String str = name.substring(lastIndex);

                    // match path name extension
                    if (str.equals(".xml")) {
                        return true;
                    }
                }
                return false;
            }
        };
        // returns pathnames for files and directory
        File[] paths = filePath.listFiles(fileNameFilter);
        actionPanel.comboActionPage.removeAllItems();
        // for each pathname in pathname array
        for (File path : paths) {
            // prints file and directory paths
            String name = path.getName();
            System.out.println(path);
            if (name.lastIndexOf('.') > 0) {
                // get last index for '.' char
                int lastIndex = name.lastIndexOf('.');

                // get extension
                String str = name.substring(0, lastIndex);

                //         comboPageName.addItem(str);
                actionPanel.comboActionPage.addItem(str);
            }

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        File filePath = getButtonFilePath("original", txtFilePath.getText());
        if (filePath != null) {
            txtFilePath.setText(filePath.getPath());
            loadPageFiles(filePath, comboPageName, false);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void comboPageNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPageNameActionPerformed
        String filePath = txtFilePath.getText() + "\\" + comboPageName.getSelectedItem().toString();
        file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        //            text.setText("test.xml");
        try {
            buttonPanel.removeAll();

            loadXmlFileButton(false);
            createTwoDimensionArray();
            posButtonsPanel = new ProductPosCardPanel("styleReader.styleList", newArrayContent, action, rows, columns, styleReader.styleList);
            buttonPanel.add(posButtonsPanel, BorderLayout.CENTER);
            buttonPanel.updateUI();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboPageNameActionPerformed

    private void btnUpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBtnActionPerformed
        defaultRowHeight = Integer.valueOf(txtRowHeight.getText());
        defaultColWidth = Integer.valueOf(txtColumnWidth.getText());;
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }//GEN-LAST:event_btnUpdateBtnActionPerformed

    private void txtEventFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEventFilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventFilePathActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        engine.cardList.clear();
        for (int i = 0; i < engine.cardList.size(); ++i) {
            PosButton posBtn = engine.cardList.get(i);
            resetPosButton(posBtn);
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveAs;
    private javax.swing.JButton btnUpdateBtn;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JComboBox comboButtonType;
    private javax.swing.JComboBox comboPageName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtButtonStyleFilePath;
    private javax.swing.JTextField txtColumnWidth;
    private javax.swing.JTextField txtEventFilePath;
    private javax.swing.JTextField txtFilePath;
    private javax.swing.JTextField txtPageFilePath;
    private javax.swing.JTextField txtRowHeight;
    // End of variables declaration//GEN-END:variables
}
