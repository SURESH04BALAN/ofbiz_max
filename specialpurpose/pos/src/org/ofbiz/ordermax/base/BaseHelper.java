/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.base.util.Debug;
import org.apache.commons.io.FileUtils;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
//import org.ofbiz.ordermax.product.productloader.VariantProductDataLoader;
//import org.ofbiz.ordermax.product.productloader.VirtualProductDataLoader;

/**
 *
 * @author administrator
 */
public class BaseHelper {

    public static final String module = BaseHelper.class.getName();

    public static ProductDataTreeLoader getProductDataLoader() {
        BaseHelper.loadProductArray(XuiContainer.getSession(), false);
        return productListArray;
    }
    protected static ProductDataTreeLoader productListArray = null;
    
    //protected static ProductDataLoaderInterace variantProductListArray = null;
    //protected static ProductDataLoaderInterace virtualProductListArray = null;

    static public void loadProductArray(XuiSession session, boolean forceLoad) {        
        productListArray = getProductArray(session);
        
        if (forceLoad) {
            Debug.logInfo("loadProductArray 1", module);
            //productListArray = new ProductDataTreeLoader(session.getProductCategory());
            productListArray.loadList();
        }
    }
    
    static public ProductDataTreeLoader getProductArray(XuiSession session) {
        if (productListArray == null) {
            Debug.logInfo("loadProductArray 1", module);
            productListArray = new CatalogCategoryDataTree(session.getProductCategory());
            productListArray.setProductLoaded(false);            
        }
        return productListArray;
    }
    /*
    static public ProductDataLoaderInterace getVariantProductArray(XuiSession session) {
        if (variantProductListArray == null) {
            Debug.logInfo("loadProductArray 1", module);
            variantProductListArray = new VariantProductDataLoader();//new ProductDataTreeLoader(session.getProductCategory());
        }
        return variantProductListArray;
    }    
    
    static public ProductDataLoaderInterace getVirtualProductArray(XuiSession session) {
        if (virtualProductListArray == null) {
            Debug.logInfo("loadProductArray 1", module);
            virtualProductListArray = new VirtualProductDataLoader();//new ProductDataTreeLoader(session.getProductCategory());
        }
        return virtualProductListArray;
    } 
    
*/
    static protected Map<String, ImageIcon> imageStrore = new HashMap<String, ImageIcon>();

    static public ImageIcon getImage(String filePath) {
        if (filePath != null && filePath.isEmpty() == false) {
            if (imageStrore.containsKey(filePath)) {
//                Debug.logInfo("found path" + filePath, module);
                return imageStrore.get(filePath);
            } else {
                ImageIcon icon = loadImage(filePath);
                if (icon != null) {
                    imageStrore.put(filePath, icon);
                    return icon;
                }
            }
        }
        return null;
    }

    static public void clearImageStore() {
        imageStrore.clear();
    }

    static public ImageIcon loadImage(String filePath) {

        String path = null;
//        Debug.logInfo("is data file name" + filePath, module);
        File f = new File(filePath);
        if (f.isAbsolute() == false) {

            String s = getAbsoluteImagePath();
//            s = s.concat("\\framework\\images\\webapp");
//            Debug.logInfo("is data file name s: " + s, module);
            f = new File(s + filePath);

        }
        ImageIcon icon = null;
        BufferedImage img = null;

        try {
            if (f.exists()) { /* do something */

                //icon = new ImageIcon(OrderMaxUtility.getImage(fileName));//prod.getString("smallImageUrl")));

                URL url = f.toURI().toURL();
                img = ImageIO.read(url);
                icon = new ImageIcon(img);
            } else {
                //              Debug.logInfo("file doesb't exists to load Image file name" + f.getAbsolutePath(), module);
            }
        } catch (Exception e) {
            Debug.logInfo("unable to load Image file name" + f.getAbsolutePath(), module);
//            Debug.logError(e,"module");
        }

        return icon;
    }

    static public BufferedImage loadBufferedImage(String filePath) {

        String path = null;
//        Debug.logInfo("is data file name" + filePath, module);
        File f = new File(filePath);
        if (f.isAbsolute() == false) {

            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
//            s = s.concat("\\framework\\images\\webapp");
//            Debug.logInfo("is data file name s: " + s, module);
            f = new File(s + filePath);

        }
        Debug.logInfo("loadBufferedImage load Image file name" + f.getAbsolutePath(), module);
        BufferedImage img = null;

        try {
            if (f.exists()) { /* do something */

                //icon = new ImageIcon(OrderMaxUtility.getImage(fileName));//prod.getString("smallImageUrl")));

                URL url = f.toURI().toURL();
                img = ImageIO.read(url);
            } else {
                Debug.logInfo("file doesb't exists to load Image file name" + f.getAbsolutePath(), module);
            }
        } catch (Exception e) {
            Debug.logInfo("unable to load Image file name" + f.getAbsolutePath(), module);
            Debug.logError(e, "module");
        }

        return img;
    }

    static public String getAbsoluteImagePath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        if (UtilValidate.isNotEmpty(ProductDataTreeLoader.BaseImagePath)) {
            s = ProductDataTreeLoader.BaseImagePath;
        } else {
            s = s.concat("\\framework\\images\\webapp");
        }

//            C:\backup\ofbiz-12.04.02\framework\images\webapp\images\products
        Debug.logInfo("is data file name s: " + s, module);
        return s;
    }

    static public String getAbsolutePath(String relPath) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        s = s.concat(relPath);

//            C:\backup\ofbiz-12.04.02\framework\images\webapp\images\products
        Debug.logInfo("is data file name s: " + s, module);
        return s;
    }

    static public String getRootPath() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

//            C:\backup\ofbiz-12.04.02\framework\images\webapp\images\products
        Debug.logInfo("is data file name getRootPath: " + s, module);
        return ProductDataTreeLoader.RootPath;//"C:\\ordermax\\apache-ofbiz-12.04.04\\apache-ofbiz-12.04.04";
    }
    
    static public String getJarRootPath() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

//            C:\backup\ofbiz-12.04.02\framework\images\webapp\images\products
        Debug.logInfo("is data file name getRootPath: " + s, module);
        return s;//"C:\\ordermax\\apache-ofbiz-12.04.04\\apache-ofbiz-12.04.04";
    }
    
    static public String getOpenBravoIamgePath(String relativePath) {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString() +"/specialpurpose/pos/src" +relativePath;
             Debug.logInfo("getOpenBravoIamgePath: " + s, module);
        File file = new File(s);
        if (file.exists()) {
            try {
                s = file.getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(BaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            JMessageDialog.showMessage(null, new MessageInf(MessageInf.SGN_WARNING, "Image doesn't exists: " + s));
        }
//            C:\backup\ofbiz-12.04.02\framework\images\webapp\images\products
        Debug.logInfo("is data file name getRootPath: " + s, module);
        return s;//"C:\\ordermax\\apache-ofbiz-12.04.04\\apache-ofbiz-12.04.04";
    }
   
    static public boolean isFileExists(String filePath) {

        String path = null;
        Debug.logInfo("is data file name" + filePath, module);
        File f = new File(filePath);
        if (f.isAbsolute() == false) {
//            Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();//currentRelativePath.toAbsolutePath().toString();

            f = new File(s + filePath);

        }

        return f.exists();
    }

    static public boolean isDirExists(String dirPath) {

        String path = null;
        Debug.logInfo("is data dirPath name" + dirPath, module);
        File f = new File(dirPath);
        if (f.isAbsolute() == false) {
//            Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();//currentRelativePath.toAbsolutePath().toString();

            f = new File(s + dirPath);

        }

        return f.exists();
    }

    static public boolean isFileExists(String dirPath, String fileName) {

        String path = null;
        Debug.logInfo("is data dirPath name" + dirPath, module);
        File f = new File(fileName);
        if (f.isAbsolute() == false) {

            f = new File(dirPath + fileName);

        }

        return f.exists();
    }

    static public boolean deleteDirectoryContent(String dirPath) {

        String path = null;
        Debug.logInfo("is data dirPath name" + dirPath, module);
        File f = new File(dirPath);
        if (f.isAbsolute() == false) {
//            Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();//.toAbsolutePath().toString();

            f = new File(s + dirPath);
            try {
                FileUtils.cleanDirectory(f); //windows

            } catch (IOException e) {
                System.out.println(e);
            }
        }

        return f.exists();
    }

    static public String selectandSetFileName(String choosertitle, String field, int width, int height) {
        File filePath = getImageFilePath(choosertitle);
        String localPath = "";
        String fileName = "";
        if (filePath != null) {
            //Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();//.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);

            localPath = "/images/products" + "/" + field;

            String path = s + localPath;
            File dir = new File(path);
            dir.mkdirs();
//            path = path+ "\\" + 
            int spacePos = filePath.getName().indexOf(".");
            fileName = choosertitle + filePath.getName().substring(spacePos);

//            copyFile(filePath.getPath(), path);
            try {
                ImageIcon iconBase = BaseHelper.getImage(filePath.getPath());
                ImageIcon icon = null;
//                    ImageIcon icon = BaseHelper.getImage(field);
                if (iconBase != null) {
                    //set the width and heights
                    if (width == 0) {
                        width = iconBase.getIconWidth();
                    }

                    if (height == 0) {
                        height = iconBase.getIconHeight();
                    }

                    Image img = iconBase.getImage();
                    Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    // retrieve image
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics g = bi.createGraphics();
                    // paint the Icon to the BufferedImage.
                    icon.paintIcon(null, g, 0, 0);
                    File outputfile = new File(path, fileName);
                    ImageIO.write(bi, "png", outputfile);
                    g.dispose();
                }

            } catch (IOException e) {
            }
            System.out.println("Current localPath path is: " + localPath);
//            field.setText(localPath);//.getPath());
//            jButton1.setIcon(new ImageIcon(OrderMaxUtility.getImage(localPath)));

        }
        return localPath + "/" + fileName;
    }
//"/osafe_theme/images/catalog/categories/vest_1_100002-000007.jpg";

    static public String selectandSetCategoryFileName(String choosertitle, String field, int width, int height) {
        File filePath = getImageFilePath(choosertitle);
        String localPath = "";
        String fileName = "";
        if (filePath != null) {
//            Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();// currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);

            //localPath = "/images/categories" + "/" + field;
            localPath = "/osafe_theme/images/catalog/categories" + "/" + field;
            String path = s + localPath;
            File dir = new File(path);
            dir.mkdirs();
//            path = path+ "\\" + 
            int spacePos = filePath.getName().indexOf(".");
            fileName = choosertitle + filePath.getName().substring(spacePos);

//            copyFile(filePath.getPath(), path);
            try {
                ImageIcon iconBase = BaseHelper.getImage(filePath.getPath());
                ImageIcon icon = null;
//                    ImageIcon icon = BaseHelper.getImage(field);
                if (iconBase != null) {
                    //set the width and heights
                    if (width == 0) {
                        width = iconBase.getIconWidth();
                    }

                    if (height == 0) {
                        height = iconBase.getIconHeight();
                    }

                    Image img = iconBase.getImage();
                    Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    // retrieve image
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics g = bi.createGraphics();
                    // paint the Icon to the BufferedImage.
                    icon.paintIcon(null, g, 0, 0);
                    File outputfile = new File(path, fileName);
                    ImageIO.write(bi, "png", outputfile);
                    g.dispose();
                }

            } catch (IOException e) {
            }
            System.out.println("Current localPath path is: " + localPath);
//            field.setText(localPath);//.getPath());
//            jButton1.setIcon(new ImageIcon(OrderMaxUtility.getImage(localPath)));

        }
        return localPath + "/" + fileName;
    }

    static public String CopyImageSetFileName(String choosertitle, File filePath, String field, int width, int height) {
        String localPath = "";
        String fileName = "";
        if (filePath != null) {
//            Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();//currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);
            int spacePos = filePath.getName().lastIndexOf(".");
            localPath = "/images/product" + "/" + field;

            String path = s + localPath;
            File dir = new File(path);
            dir.mkdirs();
//            path = path+ "\\" + 
            fileName = choosertitle + filePath.getName().substring(spacePos);

            System.out.println("save path is: " + path);
            System.out.println("save filename is: " + fileName);
            System.out.println("source path is: " + filePath.getPath());
//            copyFile(filePath.getPath(), path);
            try {
                ImageIcon iconBase = BaseHelper.getImage(filePath.getPath());
                ImageIcon icon = null;
//                    ImageIcon icon = BaseHelper.getImage(field);
                if (iconBase != null) {
                    //set the width and heights
                    if (width == 0) {
                        width = iconBase.getIconWidth();
                    }

                    if (height == 0) {
                        height = iconBase.getIconHeight();
                    }

                    Image img = iconBase.getImage();
                    Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    // retrieve image
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics g = bi.createGraphics();
                    // paint the Icon to the BufferedImage.
                    icon.paintIcon(null, g, 0, 0);
                    File outputfile = new File(path, fileName);
                    ImageIO.write(bi, "png", outputfile);
                    g.dispose();
                }

            } catch (IOException e) {
            }
            System.out.println("Current localPath path is: " + localPath);
//            field.setText(localPath);//.getPath());
//            jButton1.setIcon(new ImageIcon(OrderMaxUtility.getImage(localPath)));

        }
        return localPath + "/" + fileName;
    }

    static public String CopyCategoryImageSetFileName(String choosertitle, File filePath, String field, int width, int height) {
        String localPath = "";
        String fileName = "";
        if (filePath != null) {
//            Path currentRelativePath = Paths.get("");
            String s = getAbsoluteImagePath();//currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);
            int spacePos = filePath.getName().indexOf(".");
            localPath = "/images/categories" + "/" + field;

            String path = s + localPath;
            File dir = new File(path);
            dir.mkdirs();
//            path = path+ "\\" + 
            fileName = choosertitle + filePath.getName().substring(spacePos);

            System.out.println("save path is: " + path);
            System.out.println("save filename is: " + fileName);
            System.out.println("source path is: " + filePath.getPath());
//            copyFile(filePath.getPath(), path);
            try {
                ImageIcon iconBase = BaseHelper.getImage(filePath.getPath());
                ImageIcon icon = null;
//                    ImageIcon icon = BaseHelper.getImage(field);
                if (iconBase != null) {
                    //set the width and heights
                    if (width == 0) {
                        width = iconBase.getIconWidth();
                    }

                    if (height == 0) {
                        height = iconBase.getIconHeight();
                    }

                    Image img = iconBase.getImage();
                    Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    // retrieve image
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics g = bi.createGraphics();
                    // paint the Icon to the BufferedImage.
                    icon.paintIcon(null, g, 0, 0);
                    File outputfile = new File(path, fileName);
                    ImageIO.write(bi, "png", outputfile);
                    g.dispose();
                }

            } catch (IOException e) {
            }
            System.out.println("Current localPath path is: " + localPath);
//            field.setText(localPath);//.getPath());
//            jButton1.setIcon(new ImageIcon(OrderMaxUtility.getImage(localPath)));

        }
        return localPath + "/" + fileName;
    }

    static public File getImageFilePath(String choosertitle) {
        File fileName = null;

//        Path currentRelativePath = Paths.get("");
        String s = getAbsoluteImagePath();//currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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

    static public File getDirPath(String choosertitle) {
        File fileName = null;

//        Path currentRelativePath = Paths.get("");
        String s = getAbsoluteImagePath();//currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
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

    static public boolean copyFile(String source1, String desc1) {
        boolean result = true;
        File source = new File(source1);//"H:\\work-temp\\file");
        File desc = new File(desc1);//"H:\\work-temp\\file2");
        try {
            FileUtils.copyFile(source, desc);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    static public String CopyDataResourceImageSetFileName(String choosertitle, File filePath, String field, int width, int height) {
        String localPath = "";
        String javaLocalPath = "";
        String fileName = "";
        if (filePath != null) {
//            Path currentRelativePath = Paths.get("");
            String root = getRootPath();//currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + root);
            int spacePos = filePath.getName().lastIndexOf(".");
            localPath = "\\osafe_theme\\images\\catalog\\products" + "\\" + field;
            javaLocalPath = "/osafe_theme/images/catalog/products" + "/" + field;

            String path = root + "\\themes\\osafe_theme\\webapp" + localPath;
            File dir = new File(path);
            dir.mkdirs();
//            path = path+ "\\" + 
            fileName = choosertitle + filePath.getName().substring(spacePos);

            System.out.println("source path is: " + filePath.getPath());
//            copyFile(filePath.getPath(), path);
            try {
                ImageIcon iconBase = BaseHelper.getImage(filePath.getPath());
                ImageIcon icon = null;
//                    ImageIcon icon = BaseHelper.getImage(field);
                if (iconBase != null) {
                    //set the width and heights
                    if (width == 0) {
                        width = iconBase.getIconWidth();
                    }

                    if (height == 0) {
                        height = iconBase.getIconHeight();
                    }

                    Image img = iconBase.getImage();
                    Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    // retrieve image
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics g = bi.createGraphics();
                    // paint the Icon to the BufferedImage.
                    icon.paintIcon(null, g, 0, 0);
                    System.out.println("save path is: " + path);
                    System.out.println("save filename is: " + fileName);
                    File outputfile = new File(path, fileName);
                    ImageIO.write(bi, "jpg", outputfile);
                    g.dispose();
                }

            } catch (IOException e) {
            }
            System.out.println("Current localPath path is: " + localPath);
//            field.setText(localPath);//.getPath());
//            jButton1.setIcon(new ImageIcon(OrderMaxUtility.getImage(localPath)));

        }
        return javaLocalPath + "/" + fileName;
    }

    static public boolean autoCropAndResizeImage(String sourcefilePath, String distfilePath, int width, int height, double tolerance) {

        if (UtilValidate.isNotEmpty(sourcefilePath) && UtilValidate.isNotEmpty(distfilePath)) {
            try {
                File f = new File(sourcefilePath);
                URL url = f.toURI().toURL();
                //img = ImageIO.read(url);
                BufferedImage img = ImageIO.read(url);
                int width1 = img.getWidth();
                int height1 = img.getHeight();

                System.out.println("Image width1: " + width1);
                System.out.println("Image height1: " + height1);
                BufferedImage croppedBi = getCroppedImage(img, tolerance);
                File outputfile = new File(distfilePath);
                outputfile.mkdirs();
                ImageIO.write(croppedBi, "jpg", outputfile);
                resizeImage(distfilePath, distfilePath, width, height);
                img.flush();
                croppedBi.flush();
                System.gc();
            } catch (MalformedURLException ex) {
                Logger.getLogger(BaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    static public boolean resizeImage(String sourcefilePath, String distfilePath, int width, int height) {

        if (UtilValidate.isNotEmpty(sourcefilePath) && UtilValidate.isNotEmpty(distfilePath)) {

            System.out.println("source path is: " + sourcefilePath);
            System.out.println("distfilePath path is: " + distfilePath);
            //            copyFile(filePath.getPath(), path);
            try {
                ImageIcon iconBase = BaseHelper.loadImage(sourcefilePath); //BaseHelper.getImage(sourcefilePath);
                ImageIcon icon = null;
                //                    ImageIcon icon = BaseHelper.getImage(field);
                if (iconBase != null) {
                    //set the width and heights
                    if (width == 0) {
                        width = iconBase.getIconWidth();
                    }

                    if (height == 0) {
                        height = iconBase.getIconHeight();
                    }

                    Image img = iconBase.getImage();
                    Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    // retrieve image
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics g = bi.createGraphics();
                    // paint the Icon to the BufferedImage.
                    icon.paintIcon(null, g, 0, 0);

                    //now crop the image
                    File outputfile = new File(distfilePath);
                    outputfile.mkdirs();
                    ImageIO.write(bi, "jpg", outputfile);
                    g.dispose();                    
                }

            } catch (IOException e) {
            }

        }
        return true;
    }

    static public BufferedImage getCroppedImage(BufferedImage source, double tolerance) {
        // Get our top-left pixel color as our "baseline" for cropping
        int baseColor = source.getRGB(0, 0);

        int width = source.getWidth();
        int height = source.getHeight();

        int topY = Integer.MAX_VALUE, topX = Integer.MAX_VALUE;
        int bottomY = -1, bottomX = -1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)) {
                    if (x < topX) {
                        topX = x;
                    }
                    if (y < topY) {
                        topY = y;
                    }
                    if (x > bottomX) {
                        bottomX = x;
                    }
                    if (y > bottomY) {
                        bottomY = y;
                    }
                }
            }
        }

        BufferedImage destination = new BufferedImage((bottomX - topX + 1),
                (bottomY - topY + 1), BufferedImage.TYPE_INT_RGB);

        destination.getGraphics().drawImage(source, 0, 0,
                destination.getWidth(), destination.getHeight(),
                topX, topY, bottomX, bottomY, null);

        return destination;
    }

    static private boolean colorWithinTolerance(int a, int b, double tolerance) {
        int aAlpha = (int) ((a & 0xFF000000) >>> 24);   // Alpha level
        int aRed = (int) ((a & 0x00FF0000) >>> 16);   // Red level
        int aGreen = (int) ((a & 0x0000FF00) >>> 8);    // Green level
        int aBlue = (int) (a & 0x000000FF);            // Blue level

        int bAlpha = (int) ((b & 0xFF000000) >>> 24);   // Alpha level
        int bRed = (int) ((b & 0x00FF0000) >>> 16);   // Red level
        int bGreen = (int) ((b & 0x0000FF00) >>> 8);    // Green level
        int bBlue = (int) (b & 0x000000FF);            // Blue level

        double distance = Math.sqrt((aAlpha - bAlpha) * (aAlpha - bAlpha)
                + (aRed - bRed) * (aRed - bRed)
                + (aGreen - bGreen) * (aGreen - bGreen)
                + (aBlue - bBlue) * (aBlue - bBlue));

        // 510.0 is the maximum distance between two colors 
        // (0,0,0,0 -> 255,255,255,255)
        double percentAway = distance / 510.0d;

        return (percentAway > tolerance);
    }

    static public List<File> getFiles(String path) {
        List<File> results = new ArrayList<File>();
        System.out.println("File " + path);
        File folder = new File(path);
        System.out.println("File getParent" + folder.getParent());
        File folder1 = new File(folder.getParent());
        File[] listOfFiles = folder1.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());
                    results.add(listOfFiles[i]);
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
        }

        /* File fileDir = new File(path);
         if (fileDir != null) {
         File[] files = new File(fileDir.getPath()).listFiles();
         //If this pathname does not denote a directory, then listFiles() returns null. 
         if (files != null) {
         for (File file : files) {
         if (file.isFile()) {
         results.add(file);
         }
         }
         }
         }*/
        return results;
    }

    static public List<File> getSubDirectories(String path) {
        List<File> results = new ArrayList<File>();
        System.out.println("File " + path);
        File folder = new File(path);
        System.out.println("File getParent" + folder.getParent());
        File folder1 = new File(folder.getParent());
        File[] listOfFiles = folder1.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());
                    //results.add(listOfFiles[i]);
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                    results.add(listOfFiles[i]);
                }
            }
        }

        /* File fileDir = new File(path);
         if (fileDir != null) {
         File[] files = new File(fileDir.getPath()).listFiles();
         //If this pathname does not denote a directory, then listFiles() returns null. 
         if (files != null) {
         for (File file : files) {
         if (file.isFile()) {
         results.add(file);
         }
         }
         }
         }*/
        return results;
    }
}
