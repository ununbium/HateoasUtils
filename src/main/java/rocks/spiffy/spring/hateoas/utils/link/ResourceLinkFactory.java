package rocks.spiffy.spring.hateoas.utils.link;

import org.springframework.hateoas.Link;

import java.net.URI;
import java.util.Optional;

/**
 * A generic interface for providing links to rest resources.
 *
 * @param <R> the resource type that this link factory relates to (normally should be hard coded in implementing class)
 * @author Andrew Hill
 */
public interface ResourceLinkFactory<R> {

    /**
     * produces a link to the given resource with 'self' relation
     *
     * @param identifier the identifier to link to
     * @return the hateoas link to this resource
     */
    Link getLinkForIdentifier(String identifier);

    /**
     * given an entity, return it's string identifier
     *
     * @param entity the entity to the the identifier from
     * @return the resource identifier of the entity
     */
    String getIdentifierForEntity(R entity);

    /**
     * produces a ControllerLinkBuilder to the given resource
     *
     * @param relation the relation name
     * @param identifier the location code to link to
     * @return the hateoas link to this resource
     */
    Link getLinkForIdentifier(String relation, String identifier);

    /**
     * get the uri for an identifier
     *
     * @param identifier the identifier to convert to a uri
     * @return the uri, if generated
     */
    Optional<URI> getUriForIdentifier(String identifier);

    /**
     * given an entity, return it's URI
     *
     * @param entity the entity to the the identifier from
     * @return an optional URI, present if the entity value was not null, and was valid
     */
    Optional<URI> getUriForEntity(R entity);

    /**
     * given an entity, return it's Link
     *
     * @param entity the entity to the the link from
     * @return an optional self Link, present if the entity value was not null, and was valid
     */
    Optional<Link> getLinkForEntity(R entity);
}
