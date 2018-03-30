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

public class CampaignReviewComponent extends AbstractCampaignComponent {

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final String action = request.getParameter("action");

        final PageFlow pageFlow = getPageFlow();

        if (StringUtils.equalsIgnoreCase("cancel", action)) {
            pageFlow.sendEvent(CampaignConstants.EVENT_CANCEL_REQUESTED);
            return;
        }

        pageFlow.sendEvent(CampaignConstants.EVENT_APPLICATION_REVIEWED);
    }

}
