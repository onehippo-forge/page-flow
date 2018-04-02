
[//]: # (  Copyright 2018 Hippo B.V. (http://www.onehippo.com)  )
[//]: # (  )
[//]: # (  Licensed under the Apache License, Version 2.0 (the "License");  )
[//]: # (  you may not use this file except in compliance with the License.  )
[//]: # (  You may obtain a copy of the License at  )
[//]: # (  )
[//]: # (       http://www.apache.org/licenses/LICENSE-2.0  )
[//]: # (  )
[//]: # (  Unless required by applicable law or agreed to in writing, software  )
[//]: # (  distributed under the License is distributed on an "AS IS" BASIS,  )
[//]: # (  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  )
[//]: # (  See the License for the specific language governing permissions and  )
[//]: # (  limitations under the License.  )

## Page Flow Module

### Introduction

**Page Flow Module** provides a generic way to define and manage states of pages through Page Flow Documents
in Hippo CMS authoring application, and a flexible way to implement *Page Flow Interactions* in Hippo CMS delivery-tier
application.

What does it mean by *Page Flow Interactions*?

It is an application interaction style that comprises multiple steps for users to navigate, view or fill in the forms
to complete a business-meaningful action.

Suppose a visitor is coming from a link in a marketing campagin E-Mail message. The link can possibly lead the visitor
to the landing page first. If the visitor feels okay with the landing page content, then the visitor might want to
follow the next page link. The navigation might lead to multiple step pages such as selecting a product, entering
visitor's information, and so on.

**Page Flow Module** provides a *solution* to manage and implement this kind of *multi-step-pages* user interactions
very easily in Hipppo CMS.

### Wait... Why Page Flow Module?

Why should Page Flow Module be introduced then? Can developers not use
[Enterprise Forms](https://www.onehippo.org/library/enterprise/enterprise-features/enterprise-forms/enterprise-forms.html)
or something similar?

If you asked this as well, that's really a good question!

You can use [Enterprise Forms](https://www.onehippo.org/library/enterprise/enterprise-features/enterprise-forms/enterprise-forms.html)
or something similar for that kind of *multi-step* interactions, but that's not *multi-step-pages* actually
because that kind of *multi-step* interaction solutions are working in an <code>HstComponent</code> window level,
not in multiple, totally-separated HST pages.

The *multi-step* interaction solution in an <code>HstComponent</code> window level has the following disadvantages:

- It is not possible to use a different HST page layout in a step page because the solution works in an <code>HstComponent</code> window level.
- Business users cannot configure different
[Relevance](https://www.onehippo.org/library/enterprise/enterprise-features/targeting/targeting.html)
personalization on each step HST page separately (e.g, setting a different banner above the form in a step)
because the solution resides only in single <code>HstComponent</code> window.
- Business users cannot configure a conversion goal in a specific step HST page (e.g, final application form page step)
for an [Experiment](https://www.onehippo.org/library/end-user-manual/experiments/experiments.html)
because the solution resides only in single <code>HstComponent</code> window.

**Page Flow Module** provides solutions for all those problems listed above.

### Module Overview

#### **pageflow-core** JAR module

- Generic APIs for both Page Flow definitions and web application runtime.

#### **pageflow-hst** JAR module

- HST-2 specific <code>PageFlowControl</code> implementation
- HST-2 specific <code>SiteMapItemHandler</code> implementation,
<code>org.onehippo.forge.pageflow.hst.sitemapitemhandler.PageFlowControlHstSiteMapItemHandler</code>,
handling active <code>PageFlow</code> and automatic redirection based on the states of <code>PageFlow</code>.
- Default <code>ChannelInfo</code> interface for Channel Manager.

#### **pageflow-repository** JAR module

- Page Flow Module specific JCR namespace definition
- Page Flow Definition document namespace definition

#### **pageflow-cms** JAR module

- Page Flow Definition document editor plugins
