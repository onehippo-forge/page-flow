<#include "../include/imports.ftl">

<#assign curPageState=pageFlow.pageState>
<#assign curPageStateIndex=curPageState.index>

<br/><br/>
<div class="text-left">
  <#if pageFlow??>
    <#list pageFlow.pageStates as pageState>
      <#if pageState.path?has_content>
        <#if pageState.index < curPageStateIndex>
          <span class="label label-primary">${pageState.id}</span>
        <#elseif pageState.index == curPageStateIndex>
          <span class="label label-success">${pageState.id}</span>
        <#else>
          <span class="label label-default">${pageState.id}</span>
        </#if>
      </#if>
    </#list>
  </#if>
</div>
