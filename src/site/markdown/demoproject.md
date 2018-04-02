
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

## Demo Project

### Build and Run

You can build and install the module locally first in the project root folder.

```bash
$ mvn clean install
```

And you can build and run the Demo project:

```bash
$ cd demo
$ mvn clean verify && mvn -Pcargo.run
```

For testing, visit http://localhost:8080/site/campaign1.

### Demo Page Flow Definition

In the demo project, the Page Flow definition for the channel (http://localhost:8080/site/campaign1) is defined
in the following document:

![Demo Page Flow Definition 1](images/demoflowdef1.png "Demo Page Flow Definition 1")

The above Page Flow Definition document is equivalent to the following Finite State Machine diagram:

![Demo Finite State Machine 1](images/demoflowfsm1.png "Demo Finite State Machine 1")

### Example User Interactions in Demo Project

#### Page Step #: Landing Page

![Page Step 1](images/demostep1.png "Page Step 1")

#### Page Step #: Plan Selection

![Page Step 2](images/demostep2.png "Page Step 2")

#### Page Step #: Enter Dependents

![Page Step 3](images/demostep3.png "Page Step 3")

#### Page Step #: Application Form

![Page Step 4](images/demostep4.png "Page Step 4")

#### Page Step #: Review

![Page Step 5](images/demostep5.png "Page Step 5")

#### Page Step #: Payment Form

![Page Step 6](images/demostep6.png "Page Step 6")

#### Page Step #: Confirmation

![Page Step 7](images/demostep7.png "Page Step 7")
