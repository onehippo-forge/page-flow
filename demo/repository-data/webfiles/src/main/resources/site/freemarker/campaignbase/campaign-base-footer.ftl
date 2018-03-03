<#include "../include/imports.ftl">

<@hst.setBundle basename="campaign.general"/>
<div>
  <@hst.include ref="container"/>
</div>
<hr/>
<div class="text-center">
  <sub><@fmt.message key="footer.text" var="footer"/>${footer?html}</sub>
</div>
