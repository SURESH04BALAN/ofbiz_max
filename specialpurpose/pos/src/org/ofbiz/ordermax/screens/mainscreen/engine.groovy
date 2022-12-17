/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*
import java.sql.Timestamp
import org.ofbiz.base.util.*
import org.ofbiz.entity.*
import org.ofbiz.entity.condition.*
import org.ofbiz.entity.transaction.*
import org.ofbiz.entity.model.*
//import org.ofbiz.entity.model.ModelKeyMap
import org.ofbiz.entity.util.*
import org.ofbiz.product.inventory.*
import javax.servlet.*
import org.springframework.mock.web.*
def start(){
        prodView = new DynamicViewEntity();
    ServletRequest request = new MockHttpServletRequest();
    println "Start the engines!"
    facilityId = "hello"
    action = request.getParameter("action");
    statusId = request.getParameter("statusId");
    searchParameterString = "";
    searchParameterString = "action=Y&facilityId=" + facilityId;

    offsetQOH = -1;
    offsetATP = -1;
    hasOffsetQOH = false;
    hasOffsetATP = false;
    println searchParameterString
}
def stop(){
    println "Stop at once!"
}
this

