
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

### Bloomreach Forge Maven Repository Configuration

Make sure you have the Forge Maven2 repository reference in the root ```pom.xml``` of your project.

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
```

### Dependency Management

Add all the dependencies in the root ```pom.xml``` of your project.

You also need to add ```forge.pageflow.version``` property in the ```properties``` section.
Find the proper version in the [Release Notes](release-notes.html).

```
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

### Dependencies in Content Delivery Web Application

In ```site/pom.xml```, add the following dependency:

```xml
    <dependency>
      <groupId>org.onehippo.forge.pageflow</groupId>
      <artifactId>pageflow-hst</artifactId>
    </dependency>
```

### Dependencies in Content Authoring Web Application

In ```cms/pom.xml```, add the following dependencies:

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
