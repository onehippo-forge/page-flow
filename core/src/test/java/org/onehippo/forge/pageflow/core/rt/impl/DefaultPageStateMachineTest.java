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
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageStateDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageTransitionDefinition;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultPageStateMachineTest {

    private static Logger log = LoggerFactory.getLogger(DefaultPageStateMachineTest.class);

    @Test
    public void testPageStateMachine() throws Exception {
        DefaultPageFlowDefinition flowDef = new DefaultPageFlowDefinition("flow1");

        DefaultPageStateDefinition pageDef1 = new DefaultPageStateDefinition("P1", "/page1");
        DefaultPageStateDefinition pageDef2 = new DefaultPageStateDefinition("P2", "/page2");
        DefaultPageStateDefinition pageDef3 = new DefaultPageStateDefinition("P3", "/page3");

        DefaultPageTransitionDefinition transDef1 = new DefaultPageTransitionDefinition("P1-to-P2", "P2");
        pageDef1.addPageTransitionDefinition(transDef1);

        DefaultPageTransitionDefinition transDef2 = new DefaultPageTransitionDefinition("P2-to-P3", "P3");
        pageDef2.addPageTransitionDefinition(transDef2);

        flowDef.addPageStateDefinition(pageDef1);
        flowDef.addPageStateDefinition(pageDef2);
        flowDef.addPageStateDefinition(pageDef3);

        DefaultPageFlowFactory pageFlowFactory = new DefaultPageFlowFactory();
        PageFlow pageFlow = pageFlowFactory.createPageFlow(flowDef);
        log.debug("PageStateMachine instance: {}", pageFlow);

        pageFlow.start();
        assertFalse(pageFlow.isComplete());

        PageState pageState = pageFlow.getPageState();
        log.debug("currentPageState: {}", pageState);
        assertEquals("P1", pageState.getId());
        assertFalse(pageFlow.isComplete());

        pageFlow.sendEvent("P1-to-P2");
        pageState = pageFlow.getPageState();
        log.debug("currentPageState: {}", pageState);
        assertEquals("P2", pageState.getId());
        assertFalse(pageFlow.isComplete());

        pageFlow.sendEvent("P2-to-P3");
        pageState = pageFlow.getPageState();
        log.debug("currentPageState: {}", pageState);
        assertEquals("P3", pageState.getId());
        assertFalse(pageFlow.isComplete());

        pageFlow.stop();
        assertTrue(pageFlow.isComplete());
    }
}
