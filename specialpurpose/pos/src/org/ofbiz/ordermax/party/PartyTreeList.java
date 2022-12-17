/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;

/**
 *
 * @author siranjeev
 */
public class PartyTreeList {

    public static final String module = PartyTreeList.class.getName();
    static Integer rootId = 0;
    static protected TreeNode partyTree = new PartyRootTreeNode("party_role", "Party Roles", false,null);
    static protected HashMap<String, Key> partyIdMap = new HashMap<String, Key>();
    protected static boolean isTreeLoaded = false;
    XuiSession session = null;
    public static String AllString = "All";
    private String roleTypeId = null;
    
    public TreeNode getRootNode() {
        return partyTree;
    }

    static public boolean isTreeLoaded() {
        return isTreeLoaded;
    }

    public void setTreeLoaded(boolean loaded) {
        isTreeLoaded = loaded;
    }

    public PartyTreeList( XuiSession page) {
        this.session = page;

    }

    public void loadList(String roleTypeId) {
        
        this.roleTypeId = roleTypeId;
        
        //if product is not loaded or force load 
        if (isTreeLoaded == false) {
            buildTree();
            isTreeLoaded = true;
        }
    }

    protected void buildTree() {

        try {
            partyTree.listNodes().clear();
            Debug.logInfo("buildTree ", module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            List<GenericValue> roleList = PosProductHelper.getGenericValueLists("RoleType", session.getDelegator());

            //add all 
            for (GenericValue entry : roleList) {

                String id = entry.getString("roleTypeId");
                String name = entry.getString("description");
                //check to see if role type has been seet
                if(roleTypeId!=null && id.equals(roleTypeId) == false){
                    continue;
                }
                
                //add only parent nodes
                if (entry.getString("parentTypeId") == null) {
                    RoleTypeTreeNode newNode = new RoleTypeTreeNode(id, name, false, entry);
                    partyTree.addNodes(new Key(id, name), newNode);
                    newNode.setGenericValue(entry);
                    addChildtTreeNodes(newNode);
                }
            }

            partyIdMap.clear();
            List<Key> partyList = getAllParty();
//            Debug.logInfo(" Department[" + departmentId + "," + departmentName + "]" + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            for (Key product : partyList) {
                partyIdMap.put(product._id, product);
//                Debug.logInfo(" productIdMap[" + product._id + "," + product._name + "]", "module");
            }
        } finally {
            isTreeLoaded = true;
        }
    }

    public List<Key> getAllParty() {
        List<Key> partyList = new ArrayList<Key>();
        return partyList;
    }

    void addChildtTreeNodes(TreeNode parentNode) {
        List<GenericValue> roleList = PosProductHelper.getGenericValueLists("RoleType", "parentTypeId", parentNode._id, session.getDelegator());
        for (GenericValue entry : roleList) {
            String id = entry.getString("roleTypeId");
            String name = entry.getString("description");
            RoleTypeTreeNode newNode = new RoleTypeTreeNode(id, name, false, entry);
            parentNode.addNodes(new Key(id, name), newNode);
            newNode.setGenericValue(entry);
            addChildtTreeNodes(newNode);
            addPartyTreeNodes(newNode);
//            newNode.snewNode.getChildCount()!=0
            /*            BrandTreeNode childNode = new BrandTreeNode(keyBrand, name, false);
             newNode.addNodes(new Key(keyBrand, name), childNode);
             addProductTreeNodes(childNode);
             List<Key> productList = loadProductList(keyBrand);
             for (Key product : productList) {
             String keyProd = product._id;
             String nameProd = product._name;

             ProductTreeNode newProdNode = new ProductTreeNode(keyProd, nameProd, true);
             childNode.addNodes(new Key(keyProd, nameProd), newProdNode);
             //                    Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
             }
             * */
        }
    }

    void addPartyTreeNodes(TreeNode parentNode) {
        List<GenericValue> roleList = PosProductHelper.getGenericValueLists("PartyRole", "roleTypeId", parentNode._id, session.getDelegator());
        for (GenericValue entry : roleList) {
//            try {
            GenericValue party = PosProductHelper.getParty(entry.getString("partyId"), session.getDelegator());
            String id = entry.getString("partyId");
            String desc = party.getString("partyTypeId");
            TreeNode newNode = null;
            if (party != null) {
                if (party.getString("partyTypeId").equals("PARTY_GROUP")) {
                    GenericValue partyGroup = PosProductHelper.getPartyGroup(entry.getString("partyId"), session.getDelegator());
                    if (partyGroup != null) {
                        desc = partyGroup.getString("groupName");
                        newNode = new PartyGroupTreeNode(id, desc, true, partyGroup);
                        newNode.setGenericValue(partyGroup);
                    }
                } else {
                    GenericValue person = PosProductHelper.getPerson(entry.getString("partyId"), session.getDelegator());
                    if (person != null) {
                        if (person.getString("firstName") != null && person.getString("lastName") != null) {
                            desc = person.getString("lastName") + "," + person.getString("firstName");
                        }
                        else{
                            desc = entry.getString("partyId");
                        }
                    }
                    newNode = new PersonTreeNode(id, desc, true, person);
                    newNode.setGenericValue(person);
                }
            }
            
            String name = desc;
           
            parentNode.addNodes(new Key(id, name), newNode);
//            } catch (GenericEntityException ex) {
//                Logger.getLogger(PartyTreeList.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }
}
