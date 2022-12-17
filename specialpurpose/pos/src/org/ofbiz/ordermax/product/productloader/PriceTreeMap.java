/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productloader;

import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BrandTreeNode;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.product.ProductCategoryTreeNode;

/**
 *
 * @author administrator
 */
public class PriceTreeMap extends ProductDataTreeLoader {

    public static final String module = PriceTreeMap.class.getName();

    public PriceTreeMap(XuiSession page) {
        super();
//        rootTreeeNode = new ProductRootTreeNode(ProductDataTreeLoader.CategoryRoot, "Price Root", false);
    }

    public void addDepartTreeNodes(TreeNode deptNode) {
        Debug.logInfo(deptNode._key, module);
        TreeNode newDeptNode = findProductTreeNodes(rootTreeeNode, new Key(deptNode._id, deptNode._name));
        if (newDeptNode != null) {
            addBrandTreeNodes(deptNode, newDeptNode);
        } else {
            newDeptNode = new DepartmentTreeNode(deptNode._id, deptNode._name, false, deptNode.getGenericValue());
            rootTreeeNode.addNodes(new Key(deptNode._id, deptNode._name), newDeptNode);
            addBrandTreeNodes(deptNode, newDeptNode);
        }
    }

    public void addCategoryTreeNodes(TreeNode deptNode) {
        Debug.logInfo(deptNode._key, module);
        TreeNode newDeptNode = findProductTreeNodes(rootTreeeNode, new Key(deptNode._id, deptNode._name));
        if (newDeptNode != null) {
            addCategoryTreeNodes(deptNode, newDeptNode);
        } else {
            newDeptNode = new ProductCategoryTreeNode(deptNode._id, deptNode._name, false, deptNode.getGenericValue());
            rootTreeeNode.addNodes(new Key(deptNode._id, deptNode._name), newDeptNode);
            addCategoryTreeNodes(deptNode, newDeptNode);
        }
    }

    @Override
    public void loadList() {
    }

    public void addCategoryTreeNodes(TreeNode catNode, TreeNode newCatNode) {
        int count = catNode.getChildCount();

        for (int i = 0; i < count; ++i) {
            TreeNode brandNode = (TreeNode) catNode.getChild(i);
            TreeNode newNode = findProductTreeNodes(newCatNode, new Key(brandNode._id, brandNode._name));

            if (brandNode.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {
                if (newNode != null) {
                    //                addProductTreeNodes(brandNode, newBrandNode);
                    addCategoryTreeNodes(brandNode, newNode);
                } else {
                    newNode = new ProductCategoryTreeNode(brandNode._id, brandNode._name, false, brandNode.getGenericValue());
                    newCatNode.addNodes(new Key(brandNode._id, brandNode._name), newNode);
                    addCategoryTreeNodes(brandNode, newNode);
                }
            } else {
//                TreeNode newProdNode = findProductTreeNodes(newCatNode, new Key(prodNode._id, prodNode._name));
                if (newNode == null) {
                    ProductTreeNode newProdNode = new ProductTreeNode(brandNode._id, brandNode._name, false, brandNode.getGenericValue());
                    newCatNode.addNodes(new Key(brandNode._id, brandNode._name), newProdNode);
                }
            }

        }
    }

    public void addBrandTreeNodes(TreeNode deptNode, TreeNode newDeptNode) {
        int count = deptNode.getChildCount();
        for (int i = 0; i < count; ++i) {
            TreeNode brandNode = (TreeNode) deptNode.getChild(i);
            TreeNode newBrandNode = findProductTreeNodes(newDeptNode, new Key(brandNode._id, brandNode._name));
            if (newBrandNode != null) {
                addProductTreeNodes(brandNode, newBrandNode);
            } else {
                newBrandNode = new BrandTreeNode(brandNode._id, brandNode._name, false, brandNode.getGenericValue());
                newDeptNode.addNodes(new Key(brandNode._id, brandNode._name), newBrandNode);
                addProductTreeNodes(brandNode, newBrandNode);
            }
        }
    }

    public void addProductTreeNodes(TreeNode brandNode, TreeNode newBrandNode) {
        Debug.logInfo(" newBrandNode: " + newBrandNode._key, module);
        int count = brandNode.getChildCount();
        for (int i = 0; i < count; ++i) {
            TreeNode prodNode = (TreeNode) brandNode.getChild(i);
            TreeNode newProdNode = findProductTreeNodes(newBrandNode, new Key(prodNode._id, prodNode._name));
            if (newProdNode == null) {
                newProdNode = new ProductTreeNode(prodNode._id, prodNode._name, false, prodNode.getGenericValue());
                newBrandNode.addNodes(new Key(prodNode._id, prodNode._name), newProdNode);
            }
        }
    }

    public void addABrandTreeNode(TreeNode deptNode, TreeNode brandNode) {
        TreeNode newDeptNode = findProductTreeNodes(rootTreeeNode, new Key(deptNode._id, deptNode._name));
        if (newDeptNode == null) {
            newDeptNode = new DepartmentTreeNode(deptNode._id, deptNode._name, false, deptNode.getGenericValue());
            rootTreeeNode.addNodes(new Key(deptNode._id, deptNode._name), newDeptNode);
//            addBrandTreeNodes(deptNode, newDeptNode);
        }
        TreeNode newBrandNode = findProductTreeNodes(newDeptNode, new Key(brandNode._id, brandNode._name));
        if (newBrandNode == null) {
            newBrandNode = new BrandTreeNode(brandNode._id, brandNode._name, false, brandNode.getGenericValue());
            newDeptNode.addNodes(new Key(brandNode._id, brandNode._name), newBrandNode);
        }

        addProductTreeNodes(brandNode, newBrandNode);
    }

    public void addAProductTreeNode(TreeNode deptNode, TreeNode brandNode, TreeNode prodNode) {
        TreeNode newDeptNode = findProductTreeNodes(rootTreeeNode, new Key(deptNode._id, deptNode._name));
        if (newDeptNode == null) {
            newDeptNode = new DepartmentTreeNode(deptNode._id, deptNode._name, false, deptNode.getGenericValue());
            rootTreeeNode.addNodes(new Key(deptNode._id, deptNode._name), newDeptNode);
//            addBrandTreeNodes(deptNode, newDeptNode);
            Debug.logInfo(" newDeptNode: " + newDeptNode._key, module);
        }
        TreeNode newBrandNode = findProductTreeNodes(newDeptNode, new Key(brandNode._id, brandNode._name));
        if (newBrandNode == null) {
            newBrandNode = new BrandTreeNode(brandNode._id, brandNode._name, false, brandNode.getGenericValue());
            newDeptNode.addNodes(new Key(brandNode._id, brandNode._name), newBrandNode);

            Debug.logInfo(" newBrandNode: " + newBrandNode._key, module);
        }

        TreeNode newProdNode = findProductTreeNodes(newBrandNode, new Key(prodNode._id, prodNode._name));
        if (newProdNode == null) {
            newProdNode = new ProductTreeNode(prodNode._id, prodNode._name, false, prodNode.getGenericValue());
            newBrandNode.addNodes(new Key(prodNode._id, prodNode._name), newProdNode);
            Debug.logInfo(" newProdNode: " + newProdNode._key, module);
        }
    }
}
