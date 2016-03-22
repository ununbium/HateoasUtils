# SpringHateoasUtils

Some utilities to make make life easier when using Spring Hateoas. Specifically around the handling, generation and
resolution of links.

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






