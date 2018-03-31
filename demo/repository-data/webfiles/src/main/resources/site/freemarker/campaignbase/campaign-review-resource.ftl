{
<#if pageFlow?? && pageFlow.pageState??>
  <#assign pageState=pageFlow.pageState />
  "pageState": {
    "id": "${pageState.id}",
    "path": "${pageState.path}"
  }
<#else>
</#if>
}