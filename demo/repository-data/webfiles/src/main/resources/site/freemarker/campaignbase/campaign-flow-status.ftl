<#include "../include/imports.ftl">

<#--
In this example template, it iterates all the page states from the page flow
and render the name of each page state to show the progress.

Note: You can use resource bundle by a key which combines a prefix and the ID
      (pageState.id) of each page state instead as another example idea.
-->

<#assign curPageState=pageFlow.pageState>
<#assign curPageStateIndex=curPageState.index>

<br/><br/>
<div class="text-left">
  <#if pageFlow??>
    <#list pageFlow.pageStates as pageState>
      <#if pageState.path?has_content>
        <#if pageState.index < curPageStateIndex>
          <span class="label label-success">${pageState.name}</span>
        <#elseif pageState.index == curPageStateIndex>
          <span class="label label-primary">${pageState.name}</span>
        <#else>
          <span class="label label-default">${pageState.name}</span>
        </#if>
      </#if>
    </#list>
  </#if>
</div>
