<#include "../include/imports.ftl">

<#assign planName=''>
<#assign email=''>
<#assign firstName=''>
<#assign lastName=''>
<#assign phone=''>
<#assign address=''>
<#assign city=''>
<#assign state=''>
<#assign zip=''>

<#if campaignModel??>
  <#assign planName=campaignModel.planName!>
  <#assign email=campaignModel.email!>
  <#assign firstName=campaignModel.firstName!>
  <#assign lastName=campaignModel.lastName!>
  <#assign phone=campaignModel.phone!>
  <#assign address=campaignModel.address!>
  <#assign city=campaignModel.city!>
  <#assign state=campaignModel.state!>
  <#assign zip=campaignModel.zip!>
</#if>

<div class="text-center">
  <form method="post" action="<@hst.actionURL/>">
    <table>
      <tr>
        <th colspan="2">
          <h3>Application for <#if planName=='family'>Family<#else>Single</#if> Plan</h3>
        </th>
      </tr>
      <tr>
        <th>
          E-Mail:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="email" value="${email}" />
        </td>
      </tr>
      <tr>
        <th>
          First name:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="firstName" value="${firstName}" />
        </td>
      </tr>
      <tr>
        <th>
          Last name:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="lastName" value="${lastName}" />
        </td>
      </tr>
      <tr>
        <th>
          Phone:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="phone" value="${phone}" />
        </td>
      </tr>
      <tr>
        <th>
          Address:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="address" value="${address}" />
        </td>
      </tr>
      <tr>
        <th>
          City:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="city" value="${city}" />
        </td>
      </tr>
      <tr>
        <th>
          State:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="state" value="${state}" />
        </td>
      </tr>
      <tr>
        <th>
          ZIP:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="zip" value="${zip}" readonly="true" />
        </td>
      </tr>
      <tr>
        <th colspan="2">
          <br/>
          <input type="submit" name="action" value="Cancel" />
          <input type="submit" name="action" value="Next" />
        </th>
      </tr>
    </table>
  </form>
</div>
