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
package org.onehippo.forge.pageflow.hst.channel;

import org.hippoecm.hst.configuration.HstNodeTypes;
import org.hippoecm.hst.configuration.channel.ChannelInfo;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;

@FieldGroupList({
        @FieldGroup(
                titleKey = "fields.site",
                value = { "defaultPageFlowId", HstNodeTypes.GENERAL_PROPERTY_DEFAULT_RESOURCE_BUNDLE_ID }
                )
        }
)
public interface PageFlowSiteInfo extends ChannelInfo {

    @Parameter(name = "defaultPageFlowId", defaultValue = "")
    String getDefaultPageFlowId();

    @Parameter(name = HstNodeTypes.GENERAL_PROPERTY_DEFAULT_RESOURCE_BUNDLE_ID, defaultValue = "")
    String getDefaultResourceBundleIds();

}