/*
 * Copyright (c) 2010, Oracle.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.ofbiz.ordermax.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;

/**
 * Information about one clinet.
 * 
 * @author Jiri Vagner, Jan Stola
 */
public class Client {
    public static final String module = Client.class.getName();
    /** First name of the client. */
    private String firstName;
    /** Surname of the client. */
    private String surname;
    /** Nickname of the client. */
    private String nickname;
    /** Age of the client. */
    private int age;
    /** Sex of the client (0 - female, 1 - male). */
    private int sex;
    /** Marital status of the client (0 - single, 1 - married, 2 - separated, 3 - divorced) */
    private int maritalStatus;
    /** E-mail of the client. */
    private String email;
    /** Home web page of the client. */
    private String web;
    /** Instant messenger of the client. */
    private String im;

    public Person person = new Person();
//    PartyGroup partyGroup = new PartyGroup();
    
    // <editor-fold defaultstate="collapsed" desc="PropertyChange Stuff">
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Get Methods">
    public String getFirstName() {
        return firstName;
    }
    
    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }
    
    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getWeb() {
        return web;
    }

    public String getIm() {
        return im;
    }

    public int getSex() {
        return sex;
    }
    
    public int getMaritalStatus() {
        return maritalStatus;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Methods">
    public void setFirstName(String firstName) {
        String oldFirstName = this.firstName;
        this.firstName = firstName;
        changeSupport.firePropertyChange("firstName", oldFirstName, firstName);
    }
    
    public void setSurname(String surname) {
        String oldSurname = this.surname;
        this.surname = surname;
        changeSupport.firePropertyChange("surname", oldSurname, surname);
    }

    public void setNickname(String nickname) {
        String oldNickname = this.nickname;
        this.nickname = nickname;
        changeSupport.firePropertyChange("nickname", oldNickname, nickname);
    }

    public void setAge(int age) {
        int oldAge = this.age;
        this.age = age;
        changeSupport.firePropertyChange("age", oldAge, age);
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    public void setWeb(String web) {
        String oldWeb = this.web;
        this.web = web;
        changeSupport.firePropertyChange("web", oldWeb, web);
    }

    public void setIm(String im) {
        String oldIm = this.im;
        this.im = im;
        changeSupport.firePropertyChange("im", oldIm, im);
    }

    public void setSex(int sex) {
        int oldSex = this.sex;
        this.sex = sex;
        changeSupport.firePropertyChange("sex", oldSex, sex);
    }
    
    public void setMaritalStatus(int maritalStatus) {
        int oldMaritalStatus = this.maritalStatus;
        this.maritalStatus = maritalStatus;
        changeSupport.firePropertyChange("maritalStatus", oldMaritalStatus, maritalStatus);
    }

    // </editor-fold>
    
  /*  public static Client createTestClient(XuiSession session) {
        Client client = new Client();
        Map<String, String> mapList = getSuppliers(session);

		for (Map.Entry<String, String> entry : mapList.entrySet()) {
			String val = entry.getValue().toString();
        

        client.setFirstName(val);
        client.setSurname(entry.getKey());
        client.setNickname("Badada");
        client.setAge(7);
        
        client.setEmail("g.foo@foo.org");
        client.setWeb("https://beansbinding.dev.java.net");
        client.setIm("ICQ: 53 25 89 76");
        
        client.setSex(1);
        client.setMaritalStatus(2);
        break;
                }
        return client;
    }
*/
    public static Client createPersonClient(GenericValue person) {
        Client client = new Client();
                        client.setFirstName("Person");
  //      Map<String, String> mapList = getSuppliers(session);
/*
		for (Map.Entry<String, String> entry : mapList.entrySet()) {
			String val = entry.getValue().toString();
        

        client.setFirstName(val);
        client.setSurname(entry.getKey());
        client.setNickname("Badada");
        client.setAge(7);
        
        client.setEmail("g.foo@foo.org");
        client.setWeb("https://beansbinding.dev.java.net");
        client.setIm("ICQ: 53 25 89 76");
        
        client.setSex(1);
        client.setMaritalStatus(2);
        break;
                }
                */
        return client;
    }
    
public static Client createPartyGroupClient(GenericValue person) {
        Client client = new Client();
                client.setFirstName("Party Group");
//        Map<String, String> mapList = getSuppliers(session);
/*
		for (Map.Entry<String, String> entry : mapList.entrySet()) {
			String val = entry.getValue().toString();
        

        client.setFirstName(val);
        client.setSurname(entry.getKey());
        client.setNickname("Badada");
        client.setAge(7);
        
        client.setEmail("g.foo@foo.org");
        client.setWeb("https://beansbinding.dev.java.net");
        client.setIm("ICQ: 53 25 89 76");
        
        client.setSex(1);
        client.setMaritalStatus(2);
        break;
                }
                */
        return client;
    }
        
public static Map<String, String>  getSuppliers(XuiSession session) {
    	Delegator delegator = session.getDelegator();

    	List<GenericValue> partyList = null;
    	Map<String, String> resultList = null;

    	// create the dynamic view entity
    	DynamicViewEntity dynamicView = new DynamicViewEntity();

    	// Person (name + card)
    	dynamicView.addMemberEntity("PT", "Party");
    	dynamicView.addAlias("PT", "partyId");
    	dynamicView.addAlias("PT", "statusId");
    	dynamicView.addAlias("PT", "partyTypeId");

    	dynamicView.addMemberEntity("PR", "PartyRole");
    	dynamicView.addAlias("PR", "roleTypeId");
    	dynamicView.addViewLink("PT", "PR", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId"));

    	// define the main condition & expression list
    	List<EntityCondition> andExprs = FastList.newInstance();
    	EntityCondition mainCond = null;

    	List<String> orderBy = FastList.newInstance();
    	List<String> fieldsToSelect = FastList.newInstance();

    	// fields we need to select; will be used to set distinct
    	fieldsToSelect.add("partyId");
    	fieldsToSelect.add("partyTypeId");        
    	fieldsToSelect.add("roleTypeId");

    	// NOTE: _must_ explicitly allow null as it is not included in a not equal in many databases... odd but true
    	// This allows to get all clients when any informations has been entered
    	andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PARTY_DISABLED")));
    	andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "SUPPLIER")); // Only persons for now...

    	mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
    	orderBy.add("partyId");

    	Debug.logInfo("In searchSupplierParty mainCond=" + mainCond, "hello");

    	//thoushand suppliers enough for me
    	Integer maxRows = 100000;
    	// attempt to start a transaction
    	boolean beganTransaction = false;
    	try {
    		beganTransaction = TransactionUtil.begin();

    		try {
    			// set distinct on so we only get one row per person
    			EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
    			// using list iterator
    			EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);

    			// get the partial list for this page
    			partyList = pli.getPartialList(1, maxRows);

    			// close the list iterator
    			pli.close();
    		} catch (GenericEntityException e) {
    			Debug.logError(e, module);
    			OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
    		}
    	} catch (GenericTransactionException e) {
    		Debug.logError(e, module);
    		try {
    			TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
    		} catch (GenericTransactionException e2) {
    			Debug.logError(e2, "Unable to rollback transaction", module);
    			OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
    		}
    		OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
    	} finally {
    		try {
    			TransactionUtil.commit(beganTransaction);
    		} catch (GenericTransactionException e) {
    			Debug.logError(e, "Unable to commit transaction", module);
    			OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
    		}
    	}

    	if (partyList != null) {
    		resultList = FastMap.newInstance();
    		for (GenericValue party : partyList) {
    			/*                    Map<String, String> partyMap = FastMap.newInstance();
                    partyMap.put("partyId", party.getString("partyId"));
                    partyMap.put("roleTypeId", party.getString("roleTypeId"));                    
    			 */

    			GenericValue partyVal = null;                	
    			String partyTypeId = party.getString("partyTypeId");
    			String supplierName = null;
    			if ("PERSON".equals(partyTypeId)) {
    				try {
    					partyVal = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", party.getString("partyId")));
    					if(partyVal!=null){
    						if(partyVal.getString("lastName")!=null){
    							supplierName = partyVal.getString("lastName");
    						}

    						if(partyVal.getString("firstName")!=null){
    							supplierName = partyVal.getString("lastName") + " " + partyVal.getString("firstName");
    						}
    					}
    				} catch (GenericEntityException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			} else if ("PARTY_GROUP".equals(partyTypeId)) {
    				try {
    					partyVal = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap("partyId", party.getString("partyId")));
    					if(partyVal!=null){
    						if(partyVal.getString("groupName")!=null){
    							supplierName = partyVal.getString("groupName");
    						}
    					}

    				} catch (GenericEntityException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			} else {
    				continue;
    			}

    			Debug.logInfo("partyid: " + party.getString("partyId")+ " name " + supplierName, module);

    			if(partyVal!=null){                   
    				resultList.put(party.getString("partyId"), supplierName);
    			}
    		}

    	} else {
    		resultList = FastMap.newInstance();
    	}
    	return resultList;
    }
    
    public Client(){
        
    }
    
    public void setPerson(GenericValue val){
//        person.setGenericValue(val);
//        changeSupport.firePropertyChange("surname", "", person.getlastName());                       
 //       changeSupport.firePropertyChange("firstName", "", person.getfirstName());        
    }
    
//    void setPartyGroup(GenericValue val){
//        partyGroup.setGenericValue(val);        
//    }
    
}
