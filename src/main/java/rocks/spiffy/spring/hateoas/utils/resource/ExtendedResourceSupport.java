package rocks.spiffy.spring.hateoas.utils.resource;

import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Provides some common utilities over a plain resource support
 *
 * @author Andrew Hill
 */
public class ExtendedResourceSupport extends ResourceSupport {
    @Getter
    Map<String, Object> _embedded = new HashMap<>();

    /**
     * Get a named link and from that a href if present. Otherwise will return optional empty.
     *
     * @param linkName the name of the link to try to get the href of
     * @return Optionally a href string, if present
     */
    public Optional<String> ofNullableLink(String linkName) {
        final Optional<String> foundUri;
        Link locationLink = getLink(linkName);
        boolean locationLinkPresent = locationLink != null;

        if(locationLinkPresent) {
            foundUri = Optional.of(locationLink.getHref());
        } else {
            foundUri = Optional.empty();
        }

        return foundUri;
    }

    public void addEmbedded(String name, Object embeddedValue) {
        _embedded.put(name, embeddedValue);
    }
}
