/*
 *  Copyright 2018 Hippo B.V. (http://www.onehippo.com)
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.onehippo.forge.pageflow.demo.campaign.components;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;

public class CampaignApplicationFormComponent extends AbstractCampaignComponent {

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final String action = request.getParameter("action");

        final PageFlow pageFlow = getPageFlow();
        final CampaignModel campaignModel = (CampaignModel) pageFlow.getAttribute(CampaignConstants.DEFAULT_MODEL_NAME);

        if (StringUtils.equalsIgnoreCase("cancel", action)) {
            pageFlow.sendEvent(CampaignConstants.EVENT_CANCEL_REQUESTED);
            return;
        }

        final boolean validated = validateInputs(request, campaignModel);

        if (validated) {
            final boolean submitted = submitApplication(campaignModel);

            if (submitted) {
                pageFlow.sendEvent(CampaignConstants.EVENT_APPLICATION_SUBMITTED);
            }
        }
    }

    private boolean validateInputs(HstRequest request, CampaignModel campaignModel) {
        final String email = StringUtils.trim(request.getParameter("email"));
        if (StringUtils.isEmpty(email) || !StringUtils.contains(email, "@")) {
            return false;
        }
        campaignModel.setEmail(email);

        final String firstName = StringUtils.trim(request.getParameter("firstName"));
        if (StringUtils.isEmpty(firstName)) {
            return false;
        }
        campaignModel.setFirstName(firstName);

        final String lastName = StringUtils.trim(request.getParameter("lastName"));
        if (StringUtils.isEmpty(lastName)) {
            return false;
        }
        campaignModel.setLastName(lastName);

        final String phone = StringUtils.trim(request.getParameter("phone"));
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        campaignModel.setPhone(phone);

        final String address = StringUtils.trim(request.getParameter("address"));
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        campaignModel.setAddress(address);

        final String city = StringUtils.trim(request.getParameter("city"));
        if (StringUtils.isEmpty(city)) {
            return false;
        }
        campaignModel.setCity(city);

        final String state = StringUtils.trim(request.getParameter("state"));
        if (StringUtils.isEmpty(state)) {
            return false;
        }
        campaignModel.setState(state);

        return true;
    }

    private boolean submitApplication(CampaignModel campaignModel) {
        // Suppose you submit this application to a backend service.
        return true;
    }
}
