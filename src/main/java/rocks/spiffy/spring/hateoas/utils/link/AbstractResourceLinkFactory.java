package rocks.spiffy.spring.hateoas.utils.link;

import org.springframework.hateoas.Link;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * A generic interface for providing links to rest resources, with sensible reuse of defined methods.
 *
 * @param <R> the resource type that this link factory relates to (normally should be hard coded in implementing class)
 * @author Andrew Hill
 */
public abstract class AbstractResourceLinkFactory<R> implements ResourceLinkFactory<R> {

    public Link getLinkForIdentifier(String relation, String identifier) {
        return getLinkForIdentifier(identifier).withRel(relation);
    }

    public Optional<URI> getUriForIdentifier(String identifier) {
        Link self = getLinkForIdentifier(identifier);

        Optional<URI> optionalUri = Optional.empty();
        try {
            URI uri = new URI(self.getHref());
            optionalUri = Optional.of(uri);
        } catch (URISyntaxException e) {}

        return optionalUri;
    }

    public Optional<URI> getUriForEntity(R entity) {
        String identifier = getIdentifierForEntity(entity);
        return getUriForIdentifier(identifier);
    }

    public Optional<Link> getLinkForEntity(R entity) {
        String identifier = getIdentifierForEntity(entity);
        Optional<URI> uriForIdentifier = getUriForIdentifier(identifier);
        final Optional<Link> optionalLink;

        if(uriForIdentifier.isPresent()) {
            Link link = new Link(uriForIdentifier.get().toString());
            optionalLink = Optional.of(link);
        } else {
            optionalLink = Optional.empty();
        }

        return optionalLink;
    }
}
