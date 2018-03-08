<#include "../include/imports.ftl">

<p>&nbsp;</p>

<#assign zipCode=''>
<#if campaignModel??>
  <#assign zipCode=campaignModel.zip!>
</#if>

<div class="text-center">
  <form method="post" action="<@hst.actionURL/>">
    <div>
      <label>Zip Code <span>(primary home)</span>: </label>
      <input maxlength="5" id="zip" name="zip" value="${zipCode}" type="tel" size="5" />
      <input type="submit" name="action" value="Start!" />
    </div>
  </form>
</div>
