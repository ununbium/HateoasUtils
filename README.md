# HateoasUtils

Some utilities to make make life easier when using Spring Hateoas. Specifically around the handling, generation and
resolution of links.

## Dependency
Add the following to pom.xml to use this;

```xml
<dependency>
  <groupId>rocks.spiffy</groupId>
  <artifactId>HateoasUtils</artifactId>
  <version>2.0.0</version>
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

## AbstractResourceLinkFactory
The AbstractResourceLinkFactory is an opinionated helper service for handling links to Rest resources. By overriding two
 methods (one converting an entity to a link and the other the reverse operation) the application can then access some 
 useful utility functions for manipulating links. The two methods to implement are; 
  
```java
//given an identifier, get the link that would point to that resource's root
public Link getLinkForIdentifier(String identifier);
```
and
```java
//given an entity, get the identifier for the pet (assumed to be a string)
public String getIdentifierForEntity(Pet pet);
```

*AbstractResourceLinkFactory* makes the assumption that the resource is identified by a single string id. 
This is not ideal - it's perfectly legitimate for an identifier to have two or more components, and not to be a string. 
Please see improvement #9 (https://github.com/ununbium/HateoasUtils/issues/9) to see the CR for this. 

- getLinkForIdentifier - given a relation and an identifier, make a link for the entity
- getUriForIdentifier - try to construct a URI from the given identifier
- getUriForEntity - given an entity instance, try to construct its URI
- getLinkForEntity - given an entity instance, try to construct its self Link

The AbstractResourceLinkFactory implementation for a specific resource, e.g. PetLinkFactory can then be injected into
 other services that need to either extract or generate links.
 
For example, assuming there is a domain class Pet, which we expose on the rest API as a PetResource. The **get** for pet should 
 generate a "self" reference to allow clients to interact with this instance. The ResourceLinkFactory implementation 
 might look something like the following;
 
```java
public class PetResourceLinkFactory extends AbstractResourceLinkFactory<Pet> {
    @Override
    public Link getLinkForIdentifier(String identifier) {
        return linkTo(methodOn(PetController.class).findOne(identifier)).withRel("self");
    }

    @Override
    public String getIdentifierForEntity(Pet entity) {
        return entity.getPetNumericIdentifier();
    }
}
```
 
Within the converter *PetToPetResourceConverter* we might expect to see code like the following; 
 
 ```java
@Autowired
private PetResourceLinkFactory petResourceLinkFactory;

// ...

public PetResource convert(Pet pet) {
   PetResource petResource = new PetResource();
   petResource.setName(pet.getName());
   
   //... other conversion activities
   
   Link self = petResourceLinkFactory.getLinkForEntity(pet);
   petResource.addLink(self);
}
```
## ExtendedRepresentationModel
*ExtendedRepresentationModel* is an extension of RepresentationModel, with some improved support for Hateoas, 
specifically an _embedded getter/setter. For some more examples of how it is used please see *ExtendedResourceSupportTest*



