<#include "../include/imports.ftl">

<#assign planName=''>
<#if campaignModel??>
  <#assign planName=campaignModel.planName!>
</#if>

<div class="text-center">
  <form method="post" action="<@hst.actionURL/>">
    <table>
      <tr>
        <th colspan="2">
          <h3>Select a plan:</h3>
        </th>
      </tr>
      <tr>
        <th>
          <label>
            <input type="radio" name="plan" value="single" <#if planName=='single'>checked="true"</#if> />
            Single
          </label>
        </th>
        <td>
          &nbsp;&nbsp;
          (for the primary member only)
        </td>
      </tr>
      <tr>
        <th>
          <label>
            <input type="radio" name="plan" value="family" <#if planName=='family'>checked="true"</#if> />
            Family
          </label>
        </th>
        <td>
          &nbsp;&nbsp;
          (for the entire family)
        </td>
      </tr>
      <tr>
        <th colspan="2">
          <br/>
          <input type="submit" value="Next" />
        </th>
      </tr>
    </table>
  </form>
</div>
