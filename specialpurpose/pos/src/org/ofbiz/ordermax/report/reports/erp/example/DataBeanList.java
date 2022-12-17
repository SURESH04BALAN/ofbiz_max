/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports.erp.example;

import java.util.ArrayList;



public class DataBeanList {
   public ArrayList getDataBeanList() {
      ArrayList dataBeanList = new ArrayList();

      dataBeanList.add(produce("English", 58));
      dataBeanList.add(produce("SocialStudies", 68));
      dataBeanList.add(produce("Maths", 38));
      dataBeanList.add(produce("Hindi", 88));
      dataBeanList.add(produce("Scince", 78));
      return dataBeanList;
   }

   /*
    * This method returns a DataBean object, with subjectName ,
    * and marks set in it.
    */
   private DataBean produce(String subjectName, Integer marks) {
      DataBean dataBean = new DataBean();

      dataBean.setSubjectName(subjectName);
      dataBean.setMarks(marks);

      return dataBean;
   }
}
