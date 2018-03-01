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

import java.util.Locale;

import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceFacade;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFlowPageStateServiceFacade implements ExternalDocumentServiceFacade<PageStateInfo> {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(PageFlowPageStateServiceFacade.class);

    @Override
    public ExternalDocumentCollection<PageStateInfo> searchExternalDocuments(ExternalDocumentServiceContext context,
            String queryString) {
        SimpleExternalDocumentCollection<PageStateInfo> collection = new SimpleExternalDocumentCollection<>();
        return collection;
    }

    @Override
    public ExternalDocumentCollection<PageStateInfo> getFieldExternalDocuments(ExternalDocumentServiceContext context) {
        SimpleExternalDocumentCollection<PageStateInfo> collection = new SimpleExternalDocumentCollection<>();
        return collection;
    }

    @Override
    public void setFieldExternalDocuments(ExternalDocumentServiceContext context,
            ExternalDocumentCollection<PageStateInfo> exdocs) {
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
        return pageState.getName();
    }

}
