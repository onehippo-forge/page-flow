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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.pageflow.core.def.PageStateDefinition;
import org.onehippo.forge.pageflow.core.def.PageStateMachineDefinition;
import org.onehippo.forge.pageflow.core.def.PageStateTransitionDefinition;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.onehippo.forge.pageflow.core.rt.PageStateMachine;
import org.onehippo.forge.pageflow.core.rt.PageStateMachineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.configurers.StateConfigurer;

public class DefaultPageStateMachineFactory implements PageStateMachineFactory {

    private static Logger log = LoggerFactory.getLogger(DefaultPageStateMachineFactory.class);

    @Override
    public PageStateMachine createPageStateMachine(PageStateMachineDefinition pageStateMachineDefinition) {
        PageStateMachine pageStateMachine = null;

        try {
            Builder<PageState, String> builder = StateMachineBuilder.builder();

            builder.configureConfiguration().withConfiguration().autoStartup(false)
                    .beanFactory(new StaticListableBeanFactory());

            Map<PageState, List<PageStateTransitionDefinition>> pageStateTransDefMap = new LinkedHashMap<>();

            for (PageStateDefinition pageStateDef : pageStateMachineDefinition.getPageStateDefinitions()) {
                DefaultPageState pageState = new DefaultPageState(pageStateDef.getId(), pageStateDef.getPath());

                List<PageStateTransitionDefinition> pageStateTransList = new ArrayList<>();

                for (PageStateTransitionDefinition pageStateTransDef : pageStateDef
                        .getPageStateTransitionDefinitions()) {
                    pageStateTransList.add(pageStateTransDef);
                }

                pageStateTransDefMap.put(pageState, pageStateTransList);
            }

            StateConfigurer<PageState, String> stateConfigurer = builder.configureStates().withStates();
            boolean initialSet = false;

            for (Map.Entry<PageState, List<PageStateTransitionDefinition>> entry : pageStateTransDefMap.entrySet()) {
                PageState pageState = entry.getKey();

                if (!initialSet) {
                    stateConfigurer.initial(pageState);
                    initialSet = true;
                } else {
                    stateConfigurer.state(pageState);
                }
            }

            for (Map.Entry<PageState, List<PageStateTransitionDefinition>> entry : pageStateTransDefMap.entrySet()) {
                PageState pageState = entry.getKey();

                List<PageStateTransitionDefinition> pageStateTransDefList = entry.getValue();

                for (PageStateTransitionDefinition pageStateTransDef : pageStateTransDefList) {
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

                    PageState targetPageState = findPageStateById(pageStateTransDefMap.keySet(), targetPageStateDefId);

                    if (targetPageState == null) {
                        continue;
                    }

                    log.debug("Registering page transtion on '{}' from '{}' to '{}'.", pageStateTransDef.getEvent(),
                            pageState, targetPageState);
                    builder.configureTransitions().withExternal().event(pageStateTransDef.getEvent()).source(pageState)
                            .target(targetPageState);
                }
            }

            StateMachine<PageState, String> stateMachine = builder.build();

            pageStateMachine = new DefaultPageStateMachine(stateMachine);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page state machine.", e);
        }

        return pageStateMachine;
    }

    private PageState findPageStateById(final Collection<PageState> pageStates, final String id) {
        for (PageState pageState : pageStates) {
            if (StringUtils.equals(id, pageState.getId())) {
                return pageState;
            }
        }

        return null;
    }
}
