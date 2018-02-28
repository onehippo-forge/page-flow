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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.onehippo.forge.pageflow.core.def.PageStateDefinition;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;

public class DefaultPageFlowDefinition implements PageFlowDefinition {

    private static final long serialVersionUID = 1L;

    private final String id;

    private List<PageStateDefinition> pageStateDefs;

    public DefaultPageFlowDefinition(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<PageStateDefinition> getPageStateDefinitions() {
        if (pageStateDefs == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(pageStateDefs);
    }

    public void addPageStateDefinition(PageStateDefinition pageStateDef) {
        if (pageStateDefs == null) {
            pageStateDefs = new LinkedList<>();
        }

        pageStateDefs.add(pageStateDef);
    }

    public boolean removePageStateDefinition(PageStateDefinition pageStateDef) {
        if (pageStateDefs == null) {
            return false;
        }

        return pageStateDefs.remove(pageStateDef);
    }

    public void removeAllPageStateDefinitions() {
        if (pageStateDefs != null) {
            pageStateDefs.clear();
        }
    }
}
