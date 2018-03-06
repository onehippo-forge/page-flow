<#include "../include/imports.ftl">

<#assign cardNumber=''>
<#assign cardType=''>

<#if campaignModel??>
  <#assign cardNumber=campaignModel.cardNumber!>
  <#assign cardType=campaignModel.cardType!>
</#if>

<div class="text-center">
  <form method="post" action="<@hst.actionURL/>">
    <table>
      <tr>
        <th colspan="2">
          <h3>Payment</h3>
        </th>
      </tr>
      <tr>
        <th>
          Credit Card Number:
        </th>
        <td>
          &nbsp;&nbsp;
          <input type="text" name="cardnum" value="${cardNumber}" />
          &nbsp;&nbsp;
          <select name="cardtype">
            <option value="visa" <#if cardType=='visa'>selected</#if>>Visa</option>
            <option value="master" <#if cardType=='master'>selected</#if>>Mastercard</option>
            <option value="amex" <#if cardType=='amex'>selected</#if>>American Express</option>
            <option value="discover" <#if cardType=='discover'>selected</#if>>Discover</option>
            <option value="diners" <#if cardType=='diners'>selected</#if>>Diners Club</option>
          </select>
        </td>
      </tr>
      <tr>
        <th colspan="2">
          <p>
            <input type="checkbox" name="acknowledged" value="on" />
            I acknowledge the Privacy Policy and have read and accepted the Terms and Conditions.
          </p>
          <br/>
          <input type="submit" value="Pay Now!" />
        </th>
      </tr>
    </table>
  </form>
</div>
