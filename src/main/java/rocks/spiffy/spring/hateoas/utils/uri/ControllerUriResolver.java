package rocks.spiffy.spring.hateoas.utils.uri;

import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.core.MappingDiscoverer;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.core.DummyInvocationUtils.*;

/**
 * Provides an easy way of getting request parameters out of a uri
 *
 * @author Andrew Hill
 */
public class ControllerUriResolver {

    private static final MappingDiscoverer DISCOVERER = new AnnotationMappingDiscoverer(RequestMapping.class);

    private final UriTemplate uriTemplate;
    private final List<RequestParam> requestParameters;

    /**
     * @param uriTemplate the template that this instance of ControllerUriResolver represents
     * @param requestParameters the request parameter annotations of the controller method this ControllerUriResolver represents
     */
    private ControllerUriResolver(UriTemplate uriTemplate, List<RequestParam> requestParameters) {
        this.uriTemplate = uriTemplate;
        this.requestParameters = requestParameters;
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
     * @return the list of request parameter annotations for the given controller
     */
    public List<RequestParam> getRequestParameters() {
        return requestParameters;
    }

    /**
     * @return the URI template for the given controller
     */
    public UriTemplate getUriTemplate() {
        return uriTemplate;
    }





    /**
     * Create a ControllerUriResolver from a methodOn(controller) pattern
     *
     * @param invocationValue a proxy referenced controller method
     * @return a ControllerUriResolver representing the referenced method
     */
    public static ControllerUriResolver on(Object invocationValue) {
        Assert.isInstanceOf(LastInvocationAware.class, invocationValue);
        MethodInvocation lastInvocation = ((LastInvocationAware) invocationValue).getLastInvocation();
        Class targetClass = lastInvocation.getTargetType();
        Method targetMethod = lastInvocation.getMethod();

        String mapping = DISCOVERER.getMapping(targetClass, targetMethod);
        UriTemplate template = new UriTemplate(mapping);

        Annotation[][] parameterAnnotations = targetMethod.getParameterAnnotations();
        List<RequestParam> reqParams = new ArrayList<>();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if(annotation instanceof RequestParam) {
                    reqParams.add((RequestParam) annotation);
                }
            }
        }

        return new ControllerUriResolver(template, reqParams);
    }
}