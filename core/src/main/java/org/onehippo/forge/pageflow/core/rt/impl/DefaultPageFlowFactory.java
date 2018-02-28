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
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageStateDefinition;
import org.onehippo.forge.pageflow.core.def.PageTransitionDefinition;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageFlowFactory;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.configurers.StateConfigurer;

public class DefaultPageFlowFactory implements PageFlowFactory {

    private static Logger log = LoggerFactory.getLogger(DefaultPageFlowFactory.class);

    @Override
    public PageFlow createPageFlow(PageFlowDefinition pageFlowDef) {
        PageFlow pageFlow = null;

        try {
            Builder<PageState, String> builder = StateMachineBuilder.builder();

            builder.configureConfiguration().withConfiguration().autoStartup(false)
                    .beanFactory(new StaticListableBeanFactory());

            Map<PageState, List<PageTransitionDefinition>> pageStateTranDefsMap = new LinkedHashMap<>();

            for (PageStateDefinition pageStateDef : pageFlowDef.getPageStateDefinitions()) {
                DefaultPageState pageState = new DefaultPageState(pageStateDef.getId(), pageStateDef.getPath());

                List<PageTransitionDefinition> pageTransList = new ArrayList<>();

                for (PageTransitionDefinition pageTransDef : pageStateDef
                        .getPageTransitionDefinitions()) {
                    pageTransList.add(pageTransDef);
                }

                pageStateTranDefsMap.put(pageState, pageTransList);
            }

            StateConfigurer<PageState, String> stateConfigurer = builder.configureStates().withStates();
            boolean initialSet = false;

            for (Map.Entry<PageState, List<PageTransitionDefinition>> entry : pageStateTranDefsMap.entrySet()) {
                PageState pageState = entry.getKey();

                if (!initialSet) {
                    stateConfigurer.initial(pageState);
                    initialSet = true;
                } else {
                    stateConfigurer.state(pageState);
                }
            }

            for (Map.Entry<PageState, List<PageTransitionDefinition>> entry : pageStateTranDefsMap.entrySet()) {
                PageState pageState = entry.getKey();

                List<PageTransitionDefinition> pageTransDefList = entry.getValue();

                for (PageTransitionDefinition pageTransDef : pageTransDefList) {
                    if (StringUtils.isBlank(pageTransDef.getEvent())) {
                        continue;
                    }

                    String targetStateDefId = pageTransDef.getTargetPageStateDefinitionId();

                    if (StringUtils.isBlank(targetStateDefId)) {
                        continue;
                    }

                    if (pageState == null) {
                        continue;
                    }

                    PageState targetPageState = findPageStateById(pageStateTranDefsMap.keySet(), targetStateDefId);

                    if (targetPageState == null) {
                        continue;
                    }

                    log.debug("Registering page transtion on '{}' from '{}' to '{}'.", pageTransDef.getEvent(),
                            pageState, targetPageState);
                    builder.configureTransitions().withExternal().event(pageTransDef.getEvent()).source(pageState)
                            .target(targetPageState);
                }
            }

            StateMachine<PageState, String> stateMachine = builder.build();

            pageFlow = new DefaultPageFlow(stateMachine);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page state machine.", e);
        }

        return pageFlow;
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
