/*
 * Copyright 2018 Hippo B.V. (http://www.onehippo.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onehippo.forge.pageflow.hst.sitemapitemhandler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.configuration.channel.ChannelInfo;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;
import org.onehippo.forge.pageflow.core.rt.DefaultPageFlowControl;
import org.onehippo.forge.pageflow.core.rt.PageFlowFactory;
import org.onehippo.forge.pageflow.core.rt.PageFlowStore;
import org.onehippo.forge.pageflow.hst.channel.PageFlowSiteInfo;

public class DefaultHstPageFlowControl extends DefaultPageFlowControl {

    private static final String MODULE_NAME = "org.onehippo.forge.pageflow";

    @Override
    protected String findPageFlowId(HttpServletRequest request) {
        String flowId = super.findPageFlowId(request);

        if (StringUtils.isEmpty(flowId)) {
            final HstRequestContext requestContext = RequestContextProvider.get();

            if (requestContext != null) {
                final ResolvedSiteMapItem resolvedSiteMapItem = requestContext.getResolvedSiteMapItem();

                if (resolvedSiteMapItem != null) {
                    flowId = StringUtils
                            .trim(resolvedSiteMapItem.getParameter(DefaultPageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                }

                if (StringUtils.isEmpty(flowId)) {
                    final Mount mount = requestContext.getResolvedMount().getMount();
                    final ChannelInfo channelInfo = mount.getChannelInfo();

                    if (channelInfo != null) {
                        if (channelInfo instanceof PageFlowSiteInfo) {
                            flowId = ((PageFlowSiteInfo) channelInfo).getDefaultPageFlowId();
                        } else {
                            final Map<String, Object> channelProps = channelInfo.getProperties();

                            if (channelProps != null) {
                                flowId = StringUtils.trim((String) channelInfo.getProperties()
                                        .get(DefaultPageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                            }
                        }
                    }

                    if (StringUtils.isEmpty(flowId)) {
                        flowId = StringUtils
                                .trim(mount.getMountProperties().get(DefaultPageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                    }

                    if (StringUtils.isEmpty(flowId)) {
                        flowId = StringUtils.trim(mount.getProperty(DefaultPageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                    }
                }
            }
        }

        return flowId;
    }

    @Override
    protected PageFlowDefinitionRegistry getPageFlowDefinitionRegistry() {
        return HstServices.getComponentManager().getComponent(PageFlowDefinitionRegistry.class.getName(), MODULE_NAME);
    }

    @Override
    protected PageFlowFactory getPageFlowFactory() {
        return HstServices.getComponentManager().getComponent(PageFlowFactory.class.getName(), MODULE_NAME);
    }

    @Override
    protected PageFlowStore getPageFlowStore() {
        return HstServices.getComponentManager().getComponent(PageFlowStore.class.getName(), MODULE_NAME);
    }
}
