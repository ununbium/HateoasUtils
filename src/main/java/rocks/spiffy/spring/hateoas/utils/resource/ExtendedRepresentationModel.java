package rocks.spiffy.spring.hateoas.utils.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Provides some common utilities over a plain resource support
 *
 * @author Andrew Hill
 */
public class ExtendedRepresentationModel extends RepresentationModel<ExtendedRepresentationModel> {
    private final Map<String, Object> _embedded = new HashMap<>();

    /**
     * Get a named link and from that a href if present. Otherwise will return optional empty.
     *
     * @param linkName the name of the link to try to get the href of
     * @return Optionally a href string, if present
     */
    public Optional<String> ofNullableLink(String linkName) {
        return getLink(linkName).map(Link::getHref);
    }

    public void addEmbedded(String name, Object embeddedValue) {
        _embedded.put(name, embeddedValue);
    }

    public Map<String, Object> get_embedded() {
        return _embedded;
    }
}
