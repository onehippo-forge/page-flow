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
package org.onehippo.forge.pageflow.cms.editor.field;

import java.util.Iterator;
import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.repository.util.JcrUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceFacade;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransitionTargetPageStateServiceFacade implements ExternalDocumentServiceFacade<PageStateInfo> {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(TransitionTargetPageStateServiceFacade.class);

    @Override
    public ExternalDocumentCollection<PageStateInfo> searchExternalDocuments(ExternalDocumentServiceContext context,
            String queryString) {
        SimpleExternalDocumentCollection<PageStateInfo> collection = new SimpleExternalDocumentCollection<>();
        final boolean queryStringSet = StringUtils.isNotBlank(queryString);

        try {
            final Node pageTransNode = context.getContextModel().getNode();
            Node pageStateNode = pageTransNode.getParent();
            final Node variant = pageStateNode.getParent();

            if (variant.hasNode("pageflow:pagestate")) {
                for (NodeIterator nodeIt = variant.getNodes("pageflow:pagestate"); nodeIt.hasNext();) {
                    pageStateNode = nodeIt.nextNode();

                    if (pageStateNode != null) {
                        final String stateId = JcrUtils.getStringProperty(pageStateNode, "pageflow:stateid", null);
                        final String name = JcrUtils.getStringProperty(pageStateNode, "pageflow:name", null);
                        final String description = JcrUtils.getStringProperty(pageStateNode, "pageflow:description", null);

                        if (StringUtils.isNotBlank(stateId)) {
                            if (!queryStringSet || StringUtils.containsIgnoreCase(stateId, queryString)
                                    || StringUtils.containsIgnoreCase(name, queryString)) {
                                collection.add(new PageStateInfo(stateId, name, description));
                            }
                        }
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("Faield to search page states.", e);
        }

        return collection;
    }

    @Override
    public ExternalDocumentCollection<PageStateInfo> getFieldExternalDocuments(ExternalDocumentServiceContext context) {
        SimpleExternalDocumentCollection<PageStateInfo> collection = new SimpleExternalDocumentCollection<>();

        try {
            final Node transNode = context.getContextModel().getNode();
            final String targetStateId = JcrUtils.getStringProperty(transNode, "pageflow:target", null);

            if (StringUtils.isNotBlank(targetStateId)) {
                for (Iterator<? extends PageStateInfo> it = searchExternalDocuments(context, null).iterator(); it.hasNext(); ) {
                    PageStateInfo pageStateInfo = it.next();

                    if (StringUtils.equals(targetStateId, pageStateInfo.getStateId())) {
                        collection.add(pageStateInfo);
                        break;
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("Failed to get pageflow:event property.", e);
        }

        return collection;
    }

    @Override
    public void setFieldExternalDocuments(ExternalDocumentServiceContext context,
            ExternalDocumentCollection<PageStateInfo> pageStates) {
        if (pageStates.getSize() > 0) {
            PageStateInfo pageState = pageStates.iterator().next();

            try {
                final Node transNode = context.getContextModel().getNode();
                transNode.setProperty("pageflow:target", pageState.getStateId());
            } catch (RepositoryException e) {
                log.error("Failed to set pageflow:target property.", e);
            }
        }
    }

    @Override
    public String getDocumentDescription(ExternalDocumentServiceContext context, PageStateInfo pageState, Locale locale) {
        return pageState.getDescription();
    }

    @Override
    public String getDocumentIconLink(ExternalDocumentServiceContext context, PageStateInfo pageState, Locale locale) {
        return "";
    }

    @Override
    public String getDocumentTitle(ExternalDocumentServiceContext context, PageStateInfo pageState, Locale locale) {
        return StringUtils.defaultString(pageState.getName(), pageState.getStateId());
    }
}
