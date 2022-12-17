package org.ofbiz.pos.report;

/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: CustomBean.java 5876 2013-01-07 19:05:05Z teodord $
 */
public class CustomBean {

    /**
     *
     */
    private String city;
    private Integer id;
    private String name;
    private String street;
    private String author = "Suresh";

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    /**
     *
     */
    public CustomBean(
            String pcity,
            Integer pid,
            String pname,
            String pstreet
    ) {
        city = pcity;
        id = pid;
        name = pname;
        street = pstreet;
    }

    /**
     *
     */
    public CustomBean getMe() {
        return this;
    }

    /**
     *
     */
    public String getCity() {
        return city;
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public String getStreet() {
        return street;
    }
  private java.lang.String orderName = "Hello World";

    public java.lang.String getorderName() {
        return orderName;
    }

    public void setorderName(java.lang.String orderName) {
        this.orderName = orderName;
    }
  
}
