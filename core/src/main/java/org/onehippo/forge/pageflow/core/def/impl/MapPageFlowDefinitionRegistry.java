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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;

public class MapPageFlowDefinitionRegistry implements PageFlowDefinitionRegistry {

    private Map<String, String> uuidToFlowIdMap = new ConcurrentHashMap<>();
    private Map<String, PageFlowDefinition> pageFlowDefMap;

    public MapPageFlowDefinitionRegistry() {
        this(null);
    }

    public MapPageFlowDefinitionRegistry(final Map<String, PageFlowDefinition> pageFlowDefMap) {
        if (pageFlowDefMap != null) {
            this.pageFlowDefMap = new ConcurrentHashMap<>(pageFlowDefMap);
        }
    }

    @Override
    public PageFlowDefinition getPageFlowDefinition(String flowId) throws PageFlowException {
        if (StringUtils.isBlank(flowId)) {
            throw new IllegalArgumentException("Blank page flow ID.");
        }

        PageFlowDefinition pageFlowDef = null;

        if (pageFlowDefMap != null) {
            pageFlowDef = pageFlowDefMap.get(flowId);
        }

        return pageFlowDef;
    }

    public void addPageFlowDefinition(PageFlowDefinition pageFlowDef) throws PageFlowException {
        if (pageFlowDefMap == null) {
            pageFlowDefMap = new ConcurrentHashMap<>();
        }

        pageFlowDefMap.put(pageFlowDef.getId(), pageFlowDef);

        if (StringUtils.isNotBlank(pageFlowDef.getUuid())) {
            uuidToFlowIdMap.put(pageFlowDef.getUuid(), pageFlowDef.getId());
        }
    }

    @Override
    public void removePageFlowDefinitionByUuid(String uuid) throws PageFlowException {
        
    }

    @Override
    public void removePageFlowDefinition(String flowId) throws PageFlowException {
        if (pageFlowDefMap != null) {
            PageFlowDefinition flowDef = pageFlowDefMap.remove(flowId);

            if (flowDef != null && flowDef.getUuid() != null) {
                uuidToFlowIdMap.remove(flowDef.getUuid());
            }
        }
    }

    @Override
    public void clearPageFlowDefinitions() throws PageFlowException {
        if (pageFlowDefMap != null) {
            pageFlowDefMap.clear();
            uuidToFlowIdMap.clear();
        }
    }
}
