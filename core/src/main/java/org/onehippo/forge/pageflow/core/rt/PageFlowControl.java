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
package org.onehippo.forge.pageflow.core.rt;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.PageFlowNotFoundException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFlowControl {

    private static Logger log = LoggerFactory.getLogger(PageFlowControl.class);

    public static final String PAGE_FLOW_CONTROL_ATTR_NAME = PageFlowControl.class.getName() + ".pageflowcontrol";

    public static final String PAGE_FLOW_ID_PROP_NAME = "pageflowid";

    public static final String PAGE_FLOW_ID_ATTR_NAME = PageFlowControl.class.getName() + "." + PAGE_FLOW_ID_PROP_NAME;

    private static PageFlowControl DEFAULT_PAGE_FLOW_CONTROL = new PageFlowControl();

    public static PageFlowControl getDefault(ServletRequest request) {
        PageFlowControl control = (PageFlowControl) request.getAttribute(PAGE_FLOW_CONTROL_ATTR_NAME);

        if (control != null) {
            return control;
        }

        return DEFAULT_PAGE_FLOW_CONTROL;
    }

    private PageFlowDefinitionRegistry pageFlowDefinitionRegistry;
    private PageFlowFactory pageFlowFactory;
    private PageFlowStore pageFlowStore;

    protected PageFlowControl() {
    }

    public PageFlow getPageFlow(HttpServletRequest request) throws PageFlowNotFoundException, PageFlowException {
        String flowId = findPageFlowId(request);

        if (StringUtils.isEmpty(flowId)) {
            throw new PageFlowNotFoundException("Page flow ID not found from servlet request.");
        }

        return getPageFlow(request, flowId);
    }

    protected String findPageFlowId(HttpServletRequest request) {
        String flowId = StringUtils.trim((String) request.getAttribute(PAGE_FLOW_ID_ATTR_NAME));

        if (StringUtils.isEmpty(flowId)) {
            final HttpSession session = request.getSession(false);

            if (session != null) {
                flowId = StringUtils.trim((String) session.getAttribute(PAGE_FLOW_ID_ATTR_NAME));
            }
        }

        return flowId;
    }

    protected PageFlow getPageFlow(HttpServletRequest request, String flowId)
            throws PageFlowNotFoundException, PageFlowException {
        if (StringUtils.isBlank(flowId)) {
            throw new IllegalArgumentException("Blank page flow ID.");
        }

        PageFlow pageFlow = null;

        PageFlowStore store = getPageFlowStore();

        if (store != null) {
            pageFlow = store.getPageFlow(request, flowId);

            if (pageFlow != null) {
                return pageFlow;
            }
        }

        PageFlowDefinitionRegistry registry = getPageFlowDefinitionRegistry();
        PageFlowFactory factory = getPageFlowFactory();

        if (registry != null && factory != null) {
            PageFlowDefinition pageFlowDef = registry.getPageFlowDefinition(flowId);
            pageFlow = factory.createPageFlow(request, pageFlowDef);
            pageFlow.start();

            if (store != null) {
                store.savePageFlow(request, flowId, pageFlow);
            }

            return pageFlow;
        }

        throw new PageFlowNotFoundException("Page flow not found by ID: " + flowId);
    }

    protected PageFlowDefinitionRegistry getPageFlowDefinitionRegistry() {
        return pageFlowDefinitionRegistry;
    }

    protected void setPageFlowDefinitionRegistry(PageFlowDefinitionRegistry pageFlowDefinitionRegistry) {
        this.pageFlowDefinitionRegistry = pageFlowDefinitionRegistry;
    }

    protected PageFlowFactory getPageFlowFactory() {
        return pageFlowFactory;
    }

    protected void setPageFlowFactory(PageFlowFactory pageFlowFactory) {
        this.pageFlowFactory = pageFlowFactory;
    }

    protected PageFlowStore getPageFlowStore() {
        return pageFlowStore;
    }

    protected void setPageFlowStore(PageFlowStore pageFlowStore) {
        this.pageFlowStore = pageFlowStore;
    }

}
