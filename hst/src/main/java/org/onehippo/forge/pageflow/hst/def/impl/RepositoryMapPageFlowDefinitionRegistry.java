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
package org.onehippo.forge.pageflow.hst.def.impl;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.repository.util.JcrUtils;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageStateDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageStateDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageTransitionDefinition;
import org.onehippo.forge.pageflow.core.def.impl.MapPageFlowDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryMapPageFlowDefinitionRegistry extends MapPageFlowDefinitionRegistry {

    private static Logger log = LoggerFactory.getLogger(RepositoryMapPageFlowDefinitionRegistry.class);

    private static final String PAGE_FLOW_DEF_QUERY_STMT = "//element(*,pageflow:pageflow)[@pageflow:flowid='%s' "
            + "and @hippo:availability='live']";

    protected static final PageFlowDefinition NULL_PAGE_FLOW_DEF = new PageFlowDefinition() {

        private static final long serialVersionUID = 1L;

        @Override
        public String getId() {
            return null;
        }

        @Override
        public String getUuid() {
            return null;
        }

        @Override
        public List<PageStateDefinition> getPageStateDefinitions() {
            return null;
        }
    };

    public RepositoryMapPageFlowDefinitionRegistry() {
        super();
    }

    @Override
    public PageFlowDefinition getPageFlowDefinition(String flowId) throws PageFlowException {
        PageFlowDefinition flowDef = super.getPageFlowDefinition(flowId);

        if (flowDef == null) {
            try {
                flowDef = queryPageFlowDefinition(flowId);

                if (flowDef == null) {
                    flowDef = NULL_PAGE_FLOW_DEF;
                }
            } catch (RepositoryException e) {
                log.warn("Failed to retrieve page flow by ID, '{}'.", flowId, e);
            }

            if (flowDef != null) {
                addPageFlowDefinition(flowDef);
            }
        }

        if (flowDef == NULL_PAGE_FLOW_DEF) {
            return null;
        }

        return flowDef;
    }

    private PageFlowDefinition queryPageFlowDefinition(final String flowId) throws RepositoryException {
        final Session session = RequestContextProvider.get().getSession();
        final Query query = session.getWorkspace().getQueryManager()
                .createQuery(String.format(PAGE_FLOW_DEF_QUERY_STMT, flowId), Query.XPATH);
        final QueryResult result = query.execute();
        final NodeIterator nodeIt = result.getNodes();

        if (!nodeIt.hasNext()) {
            return null;
        }

        final Node node = nodeIt.nextNode();

        if (node == null) {
            return null;
        }

        return nodeToPageFlowDefinition(node);
    }

    private PageFlowDefinition nodeToPageFlowDefinition(final Node flowNode) throws RepositoryException {
        final String flowId = JcrUtils.getStringProperty(flowNode, "pageflow:flowid", null);

        if (StringUtils.isBlank(flowId)) {
            log.error("Blank page flow ID at '{}'.", flowNode.getPath());
            return null;
        }

        DefaultPageFlowDefinition flowDef = new DefaultPageFlowDefinition(flowId, flowNode.getIdentifier());

        for (NodeIterator stateNodeIt = flowNode.getNodes("pageflow:pagestate"); stateNodeIt.hasNext();) {
            final Node stateNode = stateNodeIt.nextNode();

            if (stateNode == null) {
                continue;
            }

            final String stateId = JcrUtils.getStringProperty(stateNode, "pageflow:stateid", null);

            if (StringUtils.isBlank(stateId)) {
                log.error("Blank page state ID at '{}'.", stateNode.getPath());
                return null;
            }

            final String statePath = JcrUtils.getStringProperty(stateNode, "pageflow:path", null);

            final DefaultPageStateDefinition stateDef = new DefaultPageStateDefinition(stateId, statePath);

            for (NodeIterator transNodeIt = stateNode.getNodes("pageflow:pagetransition"); transNodeIt.hasNext();) {
                final Node transNode = transNodeIt.nextNode();

                if (transNode == null) {
                    continue;
                }

                final String transEvent = JcrUtils.getStringProperty(transNode, "pageflow:event", null);
                final String transTarget = JcrUtils.getStringProperty(transNode, "pageflow:target", null);

                if (StringUtils.isBlank(transEvent)) {
                    log.warn("Blank page transition event at '{}'.", transNode.getPath());
                    continue;
                }

                if (StringUtils.isBlank(transTarget)) {
                    log.warn("Blank page transition target at '{}'.", transNode.getPath());
                    continue;
                }

                final DefaultPageTransitionDefinition transDef = new DefaultPageTransitionDefinition(transEvent,
                        transTarget);
                stateDef.addPageTransitionDefinition(transDef);
            }

            flowDef.addPageStateDefinition(stateDef);
        }

        return flowDef;
    }
}
