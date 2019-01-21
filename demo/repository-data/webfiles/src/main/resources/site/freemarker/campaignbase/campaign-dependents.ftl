<#include "../include/imports.ftl">

<div class="text-center">
  <form method="post" action="<@hst.actionURL/>">
    <table>
      <tr>
        <th colspan="3">
          <h3>Enter Dependents (up to ${maxDependents}):</h3>
        </th>
      </tr>
      <tr>
        <th></th>
        <th>
          First name
        </th>
        <th>
          Last name
        </th>
      </tr>
      <#assign count=0 />
      <#if campaignModel??>
        <#list campaignModel.dependents as dependent>
          <#assign count=count+1 />
          <tr>
            <th>#${count}&nbsp;</th>
            <td>
              <input type="text" name="dependentFirstName${count}" value="${dependent.firstName!}" />
            </td>
            <td>
              <input type="text" name="dependentLastName${count}" value="${dependent.lastName!}" />
            </td>
          </tr>
        </#list>
      </#if>
      <#list count..maxDependents-1 as i>
        <#assign count=count+1 />
        <tr>
          <th>#${count}&nbsp;</th>
          <td>
            <input type="text" name="dependentFirstName${count}" value="" />
          </td>
          <td>
            <input type="text" name="dependentLastName${count}" value="" />
          </td>
        </tr>
      </#list>
      <tr>
        <th colspan="3">
          <br/>
          <input type="submit" name="action" value="Next" />
        </th>
      </tr>
    </table>
  </form>
</div>
