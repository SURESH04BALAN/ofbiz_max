/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
/**
 *
 * @author siranjeev
 */
public class CatalogCategorySelectionTreePanel extends ProductSelectionTreePanel{
 /*   public CatalogCategorySelectionTreePanel(XuiSession sessionVal) {
        super(sessionVal);
    }
*/
    public CatalogCategorySelectionTreePanel(XuiSession sessionVal) {
        super(sessionVal);
    }

    @Override
    public void loadTree() {

        productListArray = new CatalogCategoryDataTree(categoryId);
        productListArray.setProductLoaded(false);
        productListArray.loadList();

        rootNode = productListArray.getRootNode();
        ProductTreeModel productTreeModel = new ProductTreeModel(rootNode);
        tree.setModel(productTreeModel);
        this.setProductListArray(productListArray);
        
    }
    
}
