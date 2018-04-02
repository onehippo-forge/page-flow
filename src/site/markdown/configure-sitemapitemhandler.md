
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

## Register PageFlowControlHstSiteMapItemHandler in HST Configuration

**PageFlowControlHstSiteMapItemHandler** is a generic [custom HstSiteMapItemHandler](https://www.onehippo.org/library/concepts/hst-configuration-model/advanced/sitemapitem-handlers.html)
implementation to control the page flow based on the page flow definition document.

Register the HstSiteMapItemHandler in an HST Configuration node of your channel
(e.g, ```/hst:hst/hst:configurations/campaign1/hst:sitemapitemhandlers```)
or in the base HST Configuration node (e.g, ```/hst:hst/hst:configurations/campaignbase/hst:sitemapitemhandlers```)
or even higher level of the ancestor HST Configuration node if needed (e.g, ```hst:hst/hst:configurations/hst:default/hst:sitemapitemhandlers```)
like the following:

```
/pageflowcontrolhandler:
  jcr:primaryType: hst:sitemapitemhandler
  auto.redirection.enabled: true
  enabled: true
  hst:sitemapitemhandlerclassname: org.onehippo.forge.pageflow.hst.sitemapitemhandler.PageFlowControlHstSiteMapItemHandler
```

## Configure the HstSiteMapItemHandler for a Channel

Suppose you have an HST Mount configuration at ```/hst:hst/hst:hosts/dev-localhost/localhost/hst:root/campaign1```
which is associated with a channel called "Campaign 1".

If you want the channel to be controlled by the Page Flow module, then simply add
the registered HstSiteMapItemHandler name to the ```@hst:defaultsitemapitemhandlerids``` of the mount configuration like the following:

```
/campaign1:
  jcr:primaryType: hst:mount
  hst:defaultsitemapitemhandlerids: [pageflowcontrolhandler]
  ...
```

Now, every request coming along to the mount will be controlled by the **PageFlowControlHstSiteMapItemHandler**.

## Configure the HstSiteMapItemHandler only for some sitemap items in a Channel

Of course, it is more convenient to configure the HstSiteMapItemHandler in the mount level
as you don't need to configure it in each sitemap item level.

However, sometimes you might want to have some paths in the same channel controlled by the
Page Flow module, but some others not controlled by the module, for some reasons.

In that case, you can configure the ```@hst:sitemapitemhandlerids``` property on each sitemap item separately. If you set the property on a specific sitemap item, any requests onto the sitemap item will be controlled by the Page Flow module. Otherwise, Page Flow module wouldn't do anything for the others.

This might be helpful if you want to have kind of "hybrid" sitemap items in single channel.

