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
package org.onehippo.forge.page.statemachine.core.rt.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.page.statemachine.core.def.PageStateDefinition;
import org.onehippo.forge.page.statemachine.core.def.PageStateMachineDefinition;
import org.onehippo.forge.page.statemachine.core.def.PageStateTransitionDefinition;
import org.onehippo.forge.page.statemachine.core.rt.PageState;
import org.onehippo.forge.page.statemachine.core.rt.PageStateMachine;
import org.onehippo.forge.page.statemachine.core.rt.PageStateMachineFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.configurers.ExternalTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

public class DefaultPageStateMachineFactory implements PageStateMachineFactory {

    @Override
    public PageStateMachine createPageStateMachine(PageStateMachineDefinition pageStateMachineDefinition) {
        PageStateMachine pageStateMachine = null;

        try {
            Builder<PageState, String> builder = StateMachineBuilder.builder();

            builder.configureConfiguration().withConfiguration().autoStartup(false)
                    .beanFactory(new StaticListableBeanFactory());

            StateConfigurer<PageState, String> stateConfigurer = builder.configureStates().withStates();

            DefaultPageState initialState = new DefaultPageState(PageStateMachine.INITIAL_PAGE_STATE_ID);
            DefaultPageState finalState = new DefaultPageState(PageStateMachine.FINAL_PAGE_STATE_ID);
            stateConfigurer.initial(initialState).end(finalState);

            ExternalTransitionConfigurer<PageState, String> transConfigurer = builder.configureTransitions()
                    .withExternal();

            Map<String, PageState> pageStatesMap = new LinkedHashMap<>();
            Map<String, PageStateTransitionDefinition> pageStateTransDefMap = new LinkedHashMap<>();

            for (PageStateDefinition pageStateDef : pageStateMachineDefinition.getPageStateDefinitions()) {
                final String pageStateId = pageStateDef.getId();
                DefaultPageState pageState = new DefaultPageState(pageStateId);
                pageStatesMap.put(pageStateId, pageState);

                stateConfigurer.state(pageState);

                for (PageStateTransitionDefinition pageStateTransDef : pageStateDef
                        .getPageStateTransitionDefinitions()) {
                    pageStateTransDefMap.put(pageStateId, pageStateTransDef);
                }
            }

            List<PageState> pageStateList = new LinkedList<>(pageStatesMap.values());

            for (Map.Entry<String, PageStateTransitionDefinition> entry : pageStateTransDefMap.entrySet()) {
                String pageStateId = entry.getKey();
                PageState pageState = pageStatesMap.get(pageStateId);
                PageStateTransitionDefinition pageStateTransDef = entry.getValue();

                if (StringUtils.isBlank(pageStateTransDef.getEvent())) {
                    continue;
                }

                String targetPageStateDefId = pageStateTransDef.getTargetPageStateDefinitionId();

                if (StringUtils.isBlank(targetPageStateDefId)) {
                    continue;
                }

                if (pageState == null) {
                    continue;
                }

                PageState targetPageState = pageStatesMap.get(targetPageStateDefId);

                if (targetPageState == null) {
                    continue;
                }

                transConfigurer.event(pageStateTransDef.getEvent()).source(pageState).target(targetPageState);
            }

            System.out.println("$$$$$ " + pageStateList);
            transConfigurer.event(PageStateMachine.EVENT_INITIALIZED).source(initialState).target(pageStateList.get(0));
            transConfigurer.event(PageStateMachine.EVENT_FINALIZING).source(pageStateList.get(pageStateList.size() - 1))
                    .target(finalState);

            StateMachine<PageState, String> stateMachine = builder.build();

            pageStateMachine = new DefaultPageStateMachine(stateMachine);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page state machine.", e);
        }

        return pageStateMachine;
    }

}
