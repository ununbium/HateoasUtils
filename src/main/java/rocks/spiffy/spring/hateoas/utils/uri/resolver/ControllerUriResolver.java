package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import lombok.Getter;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.core.MappingDiscoverer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Provides an easy way of getting request parameters out of a uri
 *
 * @author Andrew Hill
 */
@Getter
public class ControllerUriResolver {
    private final UriTemplate uriTemplate;
    private final List<RequestParam> requestParameters;
    private final List<PathVariable> pathVariables;
    private final MethodInvocation invocation;

    /**
     * @param uriTemplate the template that this instance of ControllerUriResolver represents
     * @param pathVariables the path variable annotations of the controller method this ControllerUriResolver represents
     * @param requestParameters the request parameter annotations of the controller method this ControllerUriResolver represents
     * @param invocation the api endpoint call to resolve from
     */
    ControllerUriResolver(UriTemplate uriTemplate, List<PathVariable> pathVariables, List<RequestParam> requestParameters, MethodInvocation invocation) {
        this.uriTemplate = uriTemplate;
        this.requestParameters = requestParameters;
        this.pathVariables = pathVariables;
        this.invocation = invocation;
    }

    /**
     * Resolve the map of template variables
     *
     * @param uri the uri to parse
     * @return Resolve the map of template variables
     */
    public Map<String, String> resolve(String uri) {
        return uriTemplate.match(uri);
    }

    /**
     * Resolve a specific parameter in the uri. Optionally return any found results.
     *
     * @param uri the uri to parse
     * @param parameterToResolve the parameter name to find
     * @return optionally the named uri parameter value if found
     */
    public Optional<String> resolve(String uri, String parameterToResolve) {
        Map<String, String> match = uriTemplate.match(uri);

        final Optional<String> matchFound;

        if (match.containsKey(parameterToResolve)) {
            matchFound = Optional.of(match.get(parameterToResolve));
        } else {
            matchFound = Optional.empty();
        }

        return matchFound;
    }

    /**
     * Create a ControllerUriResolver from a methodOn(controller) pattern
     *
     * @param invocationValue a proxy referenced controller method
     * @return a ControllerUriResolver representing the referenced method
     */
    public static ControllerUriResolver on(Object invocationValue) {
        ControllerBaseUriResolverFactory factory = new ControllerBaseUriResolverFactory(invocationValue);
        return factory.build();
    }
}