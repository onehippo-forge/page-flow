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

import org.junit.Ignore;
import org.junit.Test;
import org.onehippo.forge.page.statemachine.core.def.impl.DefaultPageStateDefinition;
import org.onehippo.forge.page.statemachine.core.def.impl.DefaultPageStateMachineDefinition;
import org.onehippo.forge.page.statemachine.core.def.impl.DefaultPageStateTransitionDefinition;
import org.onehippo.forge.page.statemachine.core.rt.PageState;
import org.onehippo.forge.page.statemachine.core.rt.PageStateMachine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore
public class DefaultPageStateMachineTest {

    @Test
    public void testPageStateMachine() throws Exception {
        DefaultPageStateMachineDefinition psmDef = new DefaultPageStateMachineDefinition("flow1");

        DefaultPageStateDefinition psd1 = new DefaultPageStateDefinition("P1");
        DefaultPageStateDefinition psd2 = new DefaultPageStateDefinition("P2");
        DefaultPageStateDefinition psd3 = new DefaultPageStateDefinition("P3");

        DefaultPageStateTransitionDefinition pstd1 = new DefaultPageStateTransitionDefinition();
        pstd1.setEvent("P1-to-P2");
        pstd1.setTargetPageStateDefinitionId("P2");
        psd1.addPageStateTransitionDefinition(pstd1);

        DefaultPageStateTransitionDefinition pstd2 = new DefaultPageStateTransitionDefinition();
        pstd1.setEvent("P2-to-P3");
        pstd1.setTargetPageStateDefinitionId("P3");
        psd2.addPageStateTransitionDefinition(pstd2);

        psmDef.addPageStateDefinition(psd1);
        psmDef.addPageStateDefinition(psd2);
        psmDef.addPageStateDefinition(psd3);

        DefaultPageStateMachineFactory factory = new DefaultPageStateMachineFactory();
        PageStateMachine psm = factory.createPageStateMachine(psmDef);

        psm.start();
        assertFalse(psm.isComplete());

        PageState currentPageState = psm.getCurrentPageState();
        assertEquals(PageStateMachine.INITIAL_PAGE_STATE_ID, currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.sendEvent(PageStateMachine.EVENT_INITIALIZED);
        assertEquals("P1", currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.sendEvent("P1-to-P2");
        assertEquals("P2", currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.sendEvent("P2-to-P3");
        assertEquals("P3", currentPageState.getId());
        assertFalse(psm.isComplete());

        psm.sendEvent(PageStateMachine.EVENT_FINALIZING);
        assertEquals(PageStateMachine.FINAL_PAGE_STATE_ID, currentPageState.getId());
        assertTrue(psm.isComplete());

//        psm.stop();
//        assertTrue(psm.isComplete());
    }
}
