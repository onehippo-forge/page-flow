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

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.PageFlowNotFoundException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFlowControl {

    private static Logger log = LoggerFactory.getLogger(PageFlowControl.class);

    public static final String PAGE_FLOW_CONTROL_ATTRIBUTE = PageFlowControl.class.getName() + ".pageFlowControl";

    public static final String PAGE_FLOW_ID_ATTRIBUTE = PageFlowControl.class.getName() + ".pageFlowId";

    private static PageFlowControl DEFAULT_PAGE_FLOW_CONTROL = new PageFlowControl();

    public static PageFlowControl getDefault(ServletRequest request) {
        PageFlowControl control = (PageFlowControl) request.getAttribute(PAGE_FLOW_CONTROL_ATTRIBUTE);

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
        final String flowId = StringUtils.trim((String) request.getAttribute(PAGE_FLOW_ID_ATTRIBUTE));

        if (StringUtils.isEmpty(flowId)) {
            throw new PageFlowNotFoundException("Page flow ID not found from servlet request.");
        }

        return getPageFlow(request, flowId);
    }

    protected PageFlow getPageFlow(HttpServletRequest request, String flowId) throws PageFlowNotFoundException, PageFlowException {
        if (StringUtils.isBlank(flowId)) {
            throw new IllegalArgumentException("Blank page flow ID.");
        }

        PageFlow pageFlow = null;

        if (pageFlowStore != null) {
            pageFlow = pageFlowStore.getPageFlow(request, flowId);

            if (pageFlow != null) {
                return pageFlow;
            }
        }

        if (pageFlowDefinitionRegistry != null && pageFlowFactory != null) {
            PageFlowDefinition pageFlowDef = pageFlowDefinitionRegistry.getPageFlowDefinition(flowId);
            pageFlow = pageFlowFactory.createPageFlow(request, pageFlowDef);
            pageFlow.start();

            if (pageFlowStore != null) {
                pageFlowStore.savePageFlow(request, flowId, pageFlow);
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
