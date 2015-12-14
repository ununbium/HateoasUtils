package rocks.spiffy.spring.hateoas.utils;

import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.core.MappingDiscoverer;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.Method;
import java.util.Map;

import static org.springframework.hateoas.core.DummyInvocationUtils.*;

/**
 * Provides an easy way of getting request parameters out of a uri
 *
 * @author Andrew Hill
 */
public class ControllerUriResolver {

    private static final MappingDiscoverer DISCOVERER = new AnnotationMappingDiscoverer(RequestMapping.class);

    private final UriTemplate uriTemplate;

    private ControllerUriResolver(UriTemplate uriTemplate) {
        this.uriTemplate = uriTemplate;
    }

    public static ControllerUriResolver on(Object invocationValue) {
        Assert.isInstanceOf(LastInvocationAware.class, invocationValue);
        MethodInvocation lastInvocation = ((LastInvocationAware) invocationValue).getLastInvocation();
        Class targetClass = lastInvocation.getTargetType();
        Method targetMethod = lastInvocation.getMethod();

        String mapping = DISCOVERER.getMapping(targetClass, targetMethod);
        UriTemplate template = new UriTemplate(mapping);

        return new ControllerUriResolver(template);
    }

    public Map<String, String> resolve(String uri) {
        return uriTemplate.match(uri);
    }

    public String resolve(String uri, String parameterToResolve) {
        Map<String, String> match = uriTemplate.match(uri);

        if (!match.containsKey(parameterToResolve)) {
            throw new IllegalArgumentException("Could not find id in URI: " + uri);
        }

        return match.get(parameterToResolve);
    }
}