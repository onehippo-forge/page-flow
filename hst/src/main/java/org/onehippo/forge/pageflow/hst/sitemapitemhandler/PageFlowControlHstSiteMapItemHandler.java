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
package org.onehippo.forge.pageflow.hst.sitemapitemhandler;

import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.configuration.channel.ChannelInfo;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemapitemhandler.AbstractFilterChainAwareHstSiteMapItemHandler;
import org.hippoecm.hst.core.sitemapitemhandler.HstSiteMapItemHandlerException;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;
import org.onehippo.forge.pageflow.core.rt.PageFlowControl;
import org.onehippo.forge.pageflow.core.rt.PageFlowFactory;
import org.onehippo.forge.pageflow.core.rt.PageFlowStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFlowControlHstSiteMapItemHandler extends AbstractFilterChainAwareHstSiteMapItemHandler {

    private static Logger log = LoggerFactory.getLogger(PageFlowControlHstSiteMapItemHandler.class);

    private HstPageFlowControl hstPageFlowControl = new HstPageFlowControl();

    @Override
    public ResolvedSiteMapItem process(ResolvedSiteMapItem resolvedSiteMapItem, HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws HstSiteMapItemHandlerException {
        request.setAttribute(PageFlowControl.PAGE_FLOW_CONTROL_ATTR_NAME, hstPageFlowControl);

        if (resolvedSiteMapItem == null) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.warn("Failed to filterChain.doFilter(...).", e);
            }
        }

        return resolvedSiteMapItem;
    }

    private static class HstPageFlowControl extends PageFlowControl {

        @Override
        protected String findPageFlowId(HttpServletRequest request) {
            String flowId = super.findPageFlowId(request);

            if (StringUtils.isEmpty(flowId)) {
                final HstRequestContext requestContext = RequestContextProvider.get();

                if (requestContext != null) {
                    final ResolvedSiteMapItem resolvedSiteMapItem = requestContext.getResolvedSiteMapItem();

                    if (resolvedSiteMapItem != null) {
                        flowId = StringUtils
                                .trim(resolvedSiteMapItem.getParameter(PageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                    }

                    if (StringUtils.isEmpty(flowId)) {
                        final Mount mount = requestContext.getResolvedMount().getMount();
                        final ChannelInfo channelInfo = mount.getChannelInfo();

                        if (channelInfo != null) {
                            final Map<String, Object> channelProps = channelInfo.getProperties();

                            if (channelProps != null) {
                                flowId = StringUtils.trim((String) channelInfo.getProperties()
                                        .get(PageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                            }
                        }

                        if (StringUtils.isEmpty(flowId)) {
                            flowId = StringUtils
                                    .trim(mount.getMountProperties().get(PageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                        }

                        if (StringUtils.isEmpty(flowId)) {
                            flowId = StringUtils.trim(mount.getProperty(PageFlowControl.PAGE_FLOW_ID_PROP_NAME));
                        }
                    }
                }
            }

            return flowId;
        }

        @Override
        protected PageFlowDefinitionRegistry getPageFlowDefinitionRegistry() {
            return HstServices.getComponentManager().getComponent(PageFlowDefinitionRegistry.class.getName());
        }

        @Override
        protected PageFlowFactory getPageFlowFactory() {
            return HstServices.getComponentManager().getComponent(PageFlowFactory.class.getName());
        }

        @Override
        protected PageFlowStore getPageFlowStore() {
            return HstServices.getComponentManager().getComponent(PageFlowStore.class.getName());
        }
    }
}
