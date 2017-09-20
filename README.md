# HateoasUtils

Some utilities to make make life easier when using Spring Hateoas. Specifically around the handling, generation and
resolution of links.

## Dependency
Add the following to pom.xml to use this;

```xml
<dependency>
  <groupId>rocks.spiffy</groupId>
  <artifactId>HateoasUtils</artifactId>
  <version>1.1.0</version>
</dependency>
```

## Link resolution
Links can be pushed to the server as a way of changing the relationships between resources. 
Using the following it's possible to easily grab identifiers out of links;

```java
ControllerUriResolver cr = ControllerUriResolver.on(methodOn(PetController.class).findPetByName(null));
String identifier = cr.resolve("http://example.com/pet/bobbyTheGoldfish", "petName");
assert(identifier.equals("bobbyTheGoldfish"));
```

## Link Templating
Link templating is a convenient way of allowing variable parts of a URL, it is especially useful for searching.
Using the following it's possible to build curly brace style URL templates;
 
```java
Link petsByOwnerLink = ControllerUriBuilder.linkTo(methodOn(PetController.class).findPetsByOwner(null));
assert(petsByOwner.equals("http://example.com/pet/search/petByOwner?ownerName={ownerName}));
```

## Licence

Copyright 2017 Andrew Hill

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.




