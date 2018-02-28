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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.onehippo.forge.pageflow.core.rt.PageStateMachine;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

public class DefaultPageStateMachine implements PageStateMachine {

    private static final String MODEL_MAP_VAR_NAME = DefaultPageStateMachine.class.getName() + ".modelMap";

    private final StateMachine<PageState, String> stateMachine;

    public DefaultPageStateMachine(final StateMachine<PageState, String> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void start() {
        stateMachine.start();
    }

    @Override
    public void stop() {
        stateMachine.stop();
    }

    @Override
    public boolean isComplete() {
        return stateMachine.isComplete();
    }

    @Override
    public PageState getCurrentPageState() {
        return stateMachine.getState().getId();
    }

    @Override
    public void sendEvent(String event) {
        stateMachine.sendEvent(event);
    }

    @Override
    public List<PageState> getPageStates() {
        Collection<State<PageState, String>> states = stateMachine.getStates();
        List<PageState> pageStates = new LinkedList<>();
        states.forEach(s -> pageStates.add(s.getId()));
        return pageStates;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getModel(String name) {
        Map<String, Object> modelMap = (Map<String, Object>) stateMachine.getExtendedState().getVariables()
                .get(MODEL_MAP_VAR_NAME);

        if (modelMap != null) {
            return modelMap.get(name);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setModel(String name, Object model) {
        Map<String, Object> modelMap = (Map<String, Object>) stateMachine.getExtendedState().getVariables()
                .get(MODEL_MAP_VAR_NAME);

        if (modelMap == null) {
            modelMap = new LinkedHashMap<>();
            stateMachine.getExtendedState().getVariables().put(MODEL_MAP_VAR_NAME, modelMap);
        }

        modelMap.put(name, model);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getModelMap() {
        Map<String, Object> modelMap = (Map<String, Object>) stateMachine.getExtendedState().getVariables()
                .get(MODEL_MAP_VAR_NAME);

        if (modelMap == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(modelMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("stateMachine", stateMachine).toString();
    }
}
