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
package org.onehippo.forge.pageflow.core.rt.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.rt.ErrorItem;
import org.onehippo.forge.pageflow.core.rt.Errors;

public class DefaultErrors implements Errors {

    private static final long serialVersionUID = 1L;

    private final String name;
    private List<ErrorItem> errorItems;

    public DefaultErrors(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEmpty() {
        return (errorItems == null || errorItems.isEmpty());
    }

    @Override
    public boolean addErrorItem(ErrorItem errorItem) {
        if (errorItems == null) {
            errorItems = new LinkedList<>();
        }

        return errorItems.add(errorItem);
    }

    @Override
    public boolean removeErrorItem(ErrorItem errorItem) {
        if (errorItems == null) {
            return errorItems.remove(errorItem);
        }

        return false;
    }

    @Override
    public List<ErrorItem> getErrorItems() {
        if (errorItems == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(errorItems);
    }

    @Override
    public void clear() {
        if (errorItems != null) {
            errorItems.clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultErrors)) {
            return false;
        }

        DefaultErrors that = (DefaultErrors) o;

        if (!Objects.equals(name, that.name)) {
            return false;
        }

        if (!Objects.equals(errorItems, that.errorItems)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(errorItems).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("errorItems", errorItems).toString();
    }
}
