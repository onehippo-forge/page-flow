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
          <h3>Thank you!</h3>
          <h4>Application Confirmation for <#if planName=='family'>Family<#else>Single</#if> Plan</h4>
        </th>
      </tr>
      <tr>
        <th>
          E-Mail:
        </th>
        <td>
          &nbsp;&nbsp;
          ${email}
        </td>
      </tr>
      <tr>
        <th>
          First name:
        </th>
        <td>
          &nbsp;&nbsp;
          ${firstName}
        </td>
      </tr>
      <tr>
        <th>
          Last name:
        </th>
        <td>
          &nbsp;&nbsp;
          ${lastName}
        </td>
      </tr>
      <tr>
        <th>
          Phone:
        </th>
        <td>
          &nbsp;&nbsp;
          ${phone}
        </td>
      </tr>
      <tr>
        <th>
          Address:
        </th>
        <td>
          &nbsp;&nbsp;
          ${address}
        </td>
      </tr>
      <tr>
        <th>
          City:
        </th>
        <td>
          &nbsp;&nbsp;
          ${city}
        </td>
      </tr>
      <tr>
        <th>
          State:
        </th>
        <td>
          &nbsp;&nbsp;
          ${state}
        </td>
      </tr>
      <tr>
        <th>
          ZIP:
        </th>
        <td>
          &nbsp;&nbsp;
          ${zip}
        </td>
      </tr>
      <tr>
        <th colspan="2">
          <br/>
          <input type="submit" name="action" value="Back to Home!" />
        </th>
      </tr>
    </table>
  </form>
</div>
