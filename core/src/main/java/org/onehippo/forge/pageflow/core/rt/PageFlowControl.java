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
package org.onehippo.forge.pageflow.core.rt;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.PageFlowNotFoundException;

public interface PageFlowControl {

    public static final String PAGE_FLOW_CONTROL_ATTR_NAME = PageFlowControl.class.getName() + ".pageflowcontrol";

    public static final String PAGE_FLOW_ID_PROP_NAME = "pageflowid";

    public static final String PAGE_FLOW_ID_ATTR_NAME = PageFlowControl.class.getName() + "." + PAGE_FLOW_ID_PROP_NAME;

    public static PageFlowControl getDefault(ServletRequest request) {
        PageFlowControl control = (PageFlowControl) request.getAttribute(PAGE_FLOW_CONTROL_ATTR_NAME);

        if (control != null) {
            return control;
        }

        control = (PageFlowControl) request.getServletContext().getAttribute(PAGE_FLOW_CONTROL_ATTR_NAME);

        if (control != null) {
            return control;
        }

        return null;
    }

    public PageFlow getPageFlow(HttpServletRequest request) throws PageFlowNotFoundException, PageFlowException;

    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, PageState pageState)
            throws PageFlowException, IOException, IllegalStateException;

}
