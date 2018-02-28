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

import org.junit.Test;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageStateDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageStateMachineDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageStateTransitionDefinition;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.onehippo.forge.pageflow.core.rt.PageStateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultPageStateMachineTest {

    private static Logger log = LoggerFactory.getLogger(DefaultPageStateMachineTest.class);

    @Test
    public void testPageStateMachine() throws Exception {
        DefaultPageStateMachineDefinition psmDef = new DefaultPageStateMachineDefinition("flow1");

        DefaultPageStateDefinition state1 = new DefaultPageStateDefinition("P1", "/page1");
        DefaultPageStateDefinition state2 = new DefaultPageStateDefinition("P2", "/page2");
        DefaultPageStateDefinition state3 = new DefaultPageStateDefinition("P3", "/page3");

        DefaultPageStateTransitionDefinition transition1 = new DefaultPageStateTransitionDefinition();
        transition1.setEvent("P1-to-P2");
        transition1.setTargetPageStateDefinitionId("P2");
        state1.addPageStateTransitionDefinition(transition1);

        DefaultPageStateTransitionDefinition transition2 = new DefaultPageStateTransitionDefinition();
        transition2.setEvent("P2-to-P3");
        transition2.setTargetPageStateDefinitionId("P3");
        state2.addPageStateTransitionDefinition(transition2);

        psmDef.addPageStateDefinition(state1);
        psmDef.addPageStateDefinition(state2);
        psmDef.addPageStateDefinition(state3);

        DefaultPageStateMachineFactory factory = new DefaultPageStateMachineFactory();
        PageStateMachine psm = factory.createPageStateMachine(psmDef);
        log.debug("PageStateMachine instance: {}", psm);

        psm.start();
        assertFalse(psm.isComplete());

        PageState currentPageState = psm.getCurrentPageState();
        log.debug("currentPageState: {}", currentPageState);
        assertEquals("P1", currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.sendEvent("P1-to-P2");
        currentPageState = psm.getCurrentPageState();
        log.debug("currentPageState: {}", currentPageState);
        assertEquals("P2", currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.sendEvent("P2-to-P3");
        currentPageState = psm.getCurrentPageState();
        log.debug("currentPageState: {}", currentPageState);
        assertEquals("P3", currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.stop();
        assertTrue(psm.isComplete());
    }
}
