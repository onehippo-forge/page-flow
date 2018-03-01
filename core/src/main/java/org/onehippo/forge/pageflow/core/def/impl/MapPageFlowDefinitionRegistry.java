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
package org.onehippo.forge.pageflow.core.def.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.PageFlowNotFoundException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;

public class MapPageFlowDefinitionRegistry implements PageFlowDefinitionRegistry {

    private final Map<String, PageFlowDefinition> pageFlowDefMap;

    public MapPageFlowDefinitionRegistry() {
        this(new LinkedHashMap<String, PageFlowDefinition>());
    }

    public MapPageFlowDefinitionRegistry(final Map<String, PageFlowDefinition> pageFlowDefMap) {
        this.pageFlowDefMap = pageFlowDefMap;
    }

    @Override
    public PageFlowDefinition getPageFlowDefinition(String flowId) throws PageFlowNotFoundException, PageFlowException {
        PageFlowDefinition pageFlowDef = pageFlowDefMap.get(flowId);

        if (pageFlowDef == null) {
            throw new PageFlowNotFoundException("Page flow definition not found by ID: " + flowId);
        }

        return pageFlowDef;
    }

    public void addPageFlowDefinition(String flowId, PageFlowDefinition pageFlowDef) throws PageFlowException {
        pageFlowDefMap.put(flowId, pageFlowDef);
    }

    public void removePageFlowDefinition(String flowId) throws PageFlowException {
        pageFlowDefMap.remove(flowId);
    }

    public void clearPageFlowDefinitions() throws PageFlowException {
        pageFlowDefMap.clear();
    }
}
