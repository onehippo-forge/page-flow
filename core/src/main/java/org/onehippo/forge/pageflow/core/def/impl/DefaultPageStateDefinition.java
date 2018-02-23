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
import org.onehippo.forge.pageflow.core.def.PageStateTransitionDefinition;

public class DefaultPageStateDefinition implements PageStateDefinition {

    private static final long serialVersionUID = 1L;

    private final String id;

    private List<PageStateTransitionDefinition> pageStateTransitionDefinitions;

    public DefaultPageStateDefinition(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public List<PageStateTransitionDefinition> getPageStateTransitionDefinitions() {
        if (pageStateTransitionDefinitions == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(pageStateTransitionDefinitions);
    }

    public void addPageStateTransitionDefinition(PageStateTransitionDefinition pageStateTransitionDefinition) {
        if (pageStateTransitionDefinitions == null) {
            pageStateTransitionDefinitions = new LinkedList<>();
        }

        pageStateTransitionDefinitions.add(pageStateTransitionDefinition);
    }

    public boolean removePageStateTransitionDefinition(PageStateTransitionDefinition pageStateTransitionDefinition) {
        if (pageStateTransitionDefinitions == null) {
            return false;
        }

        return pageStateTransitionDefinitions.remove(pageStateTransitionDefinition);
    }

    public void removeAllPageStateTransitionDefinitions() {
        if (pageStateTransitionDefinitions != null) {
            pageStateTransitionDefinitions.clear();
        }
    }
}
