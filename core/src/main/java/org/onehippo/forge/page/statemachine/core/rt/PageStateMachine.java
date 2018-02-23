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
package org.onehippo.forge.page.statemachine.core.rt;

import java.util.List;
import java.util.Map;

public interface PageStateMachine {

    public static final String INITIAL_PAGE_STATE_ID = "__state.initial__";

    public static final String FINAL_PAGE_STATE_ID = "__state.final__";

    public static final String EVENT_INITIALIZED = "__event.initialized__";

    public static final String EVENT_FINALIZING = "__event.finalizing__";

    public void start();

    public void stop();

    public boolean isComplete();

    public PageState getCurrentPageState();

    public void sendEvent(String event);

    public List<PageState> getPageStates();

    public Object getModel(String name);

    public void setModel(String name, Object model);

    public Map<String, Object> getModelMap();

}
