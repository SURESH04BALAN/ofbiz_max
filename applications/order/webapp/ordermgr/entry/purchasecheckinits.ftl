<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<#assign shoppingCartOrderType = "">
<#assign shoppingCartProductStore = "NA">
<#assign shoppingCartChannelType = "">
<#if shoppingCart?exists>
  <#assign shoppingCartOrderType = shoppingCart.getOrderType()>
  <#assign shoppingCartProductStore = shoppingCart.getProductStoreId()?default("NA")>
  <#assign shoppingCartChannelType = shoppingCart.getChannelType()?default("")>
<#else>
<#-- allow the order type to be set in parameter, so only the appropriate section (Sales or Purchase Order) shows up -->
  <#if parameters.orderTypeId?has_content>
    <#assign shoppingCartOrderType = parameters.orderTypeId>
  </#if>
</#if>
<!-- Purchase Order Entry -->
<#if security.hasEntityPermission("ORDERMGR", "_PURCHASE_CREATE", session)>
  <#if shoppingCartOrderType != "SALES_ORDER">
  <div class="screenlet">
    <div class="screenlet-title-bar">
      <ul>
        <li class="h3">${uiLabelMap.OrderPurchaseOrder}<#if shoppingCart?exists>&nbsp;${uiLabelMap.OrderInProgress}</#if></li>
        <li><a href="javascript:document.poentryform.submit();">${uiLabelMap.CommonContinue}</a></li>
        <li><a href="/partymgr/control/findparty?externalLoginKey=${externalLoginKey}">${uiLabelMap.PartyFindParty}</a></li>
      </ul>
      <br class="clear"/>
    </div>
    <div class="screenlet-body">
      <form method="post" name="poentryform" action="<@ofbizUrl>initorderentry</@ofbizUrl>">
      <input type='hidden' name='finalizeMode' value='type'/>
      <input type='hidden' name='orderMode' value='PURCHASE_ORDER'/>
      <table width="100%" border='0' cellspacing='0' cellpadding='0'>
        <#if partyId?exists>
          <#assign thisPartyId = partyId>
        <#else>
          <#assign thisPartyId = requestParameters.partyId?if_exists>
        </#if>
        <tr>
          <td>&nbsp;</td>
          <td width="300" align='right' valign='middle' nowrap="nowrap"><div class='tableheadtext'>${uiLabelMap.OrderOrderEntryInternalOrganization}</div></td>
          <td>&nbsp;</td>
          <td valign='middle'>
            <div class='tabletext'>
              <select name="billToCustomerPartyId"<#if sessionAttributes.orderMode?default("") == "SALES_ORDER"> disabled</#if>>
                <#list organizations as organization>
                  <#assign organizationName = Static["org.ofbiz.party.party.PartyHelper"].getPartyName(organization, true)/>
                    <#if (organizationName.length() != 0)>
                      <option value="${organization.partyId}">${organization.partyId} - ${organizationName}</option>
                    </#if>
                </#list>
              </select>
            </div>
          </td>
        </tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr>
          <td>&nbsp;</td>
          <td align='right' valign='middle' nowrap="nowrap"><div class='tableheadtext'>${uiLabelMap.CommonUserLoginId}</div></td>
          <td>&nbsp;</td>
          <td valign='middle'>
            <div class='tabletext'>
              <@htmlTemplate.lookupField value='${parameters.userLogin.userLoginId}'formName="poentryform" name="userLoginId" id="userLoginId_purchase" fieldFormName="LookupUserLoginAndPartyDetails"/>
            </div>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td align='right' valign='middle' nowrap="nowrap"><div class='tableheadtext'>${uiLabelMap.PartySupplier}</div></td>
          <td>&nbsp;</td>
          <td valign='middle'>
            <div class='tabletext'>
              <select name="supplierPartyId"<#if sessionAttributes.orderMode?default("") == "SALES_ORDER"> disabled</#if>>
                <option value="">${uiLabelMap.OrderSelectSupplier}</option>
                <#list suppliers as supplier>
                  <option value="${supplier.partyId}"<#if supplier.partyId == thisPartyId> selected="selected"</#if>>[${supplier.partyId}] - ${Static["org.ofbiz.party.party.PartyHelper"].getPartyName(supplier, true)}</option>
                </#list>
              </select>
            </div>
          </td>
        </tr>
      </table>
      </form>
    </div>
  </div>
  </#if>
</#if>
