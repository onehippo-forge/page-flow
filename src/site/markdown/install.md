
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

## Installation

Make sure you have the Forge Maven2 repository reference and dependency definition in the root pom.xml of your project.

```xml
  <repositories>

    <!-- SNIP -->

    <repository>
      <id>hippo-maven2</id>
      <name>Hippo Maven 2 Repository</name>
      <url>https://maven.onehippo.com/maven2-forge//</url>
    </repository>
 
    <!-- SNIP -->

  </repositories>

  <!-- SNIP -->

  <dependencyManagement>

    <!-- SNIP -->

    <dependencies>

      <!-- SNIP -->

      <!-- NOTE: You should set a property named 'forge.pageflow.version' to a version of this plugin! -->

      <dependency>
        <groupId>org.onehippo.forge.pageflow</groupId>
        <artifactId>pageflow-repository</artifactId>
        <version>${forge.pageflow.version}</version>
      </dependency>

      <dependency>
        <groupId>org.onehippo.forge.pageflow</groupId>
        <artifactId>pageflow-cms</artifactId>
        <version>${forge.pageflow.version}</version>
      </dependency>

      <dependency>
        <groupId>org.onehippo.forge.pageflow</groupId>
        <artifactId>pageflow-hst</artifactId>
        <version>${forge.pageflow.version}</version>
      </dependency>

      <!-- SNIP -->

    </dependencies>

    <!-- SNIP -->

  </dependencyManagement>
```

In site/pom.xml, add the following dependency:

```xml
    <dependency>
      <groupId>org.onehippo.forge.pageflow</groupId>
      <artifactId>pageflow-hst</artifactId>
    </dependency>
```

In cms/pom.xml, add the following dependency:

```xml
    <dependency>
      <groupId>org.onehippo.forge.pageflow</groupId>
      <artifactId>pageflow-repository</artifactId>
    </dependency>

    <dependency>
      <groupId>org.onehippo.forge.pageflow</groupId>
      <artifactId>pageflow-cms</artifactId>
    </dependency>
```

That's it!
