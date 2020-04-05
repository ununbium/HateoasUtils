package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import org.springframework.hateoas.server.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.server.core.DummyInvocationUtils;
import org.springframework.hateoas.server.core.LastInvocationAware;
import org.springframework.hateoas.server.core.MappingDiscoverer;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Factory to produce ControllerBaseUri from an invocation value. Throws a runtime exception if the provided
 * invocationValue is not a controller
 *
 * @author Andrew Hill
 */
public class ControllerBaseUriResolverFactory {

    private final List<PathVariable> pathVariables;

    private final List<RequestParam> requestParams;
    private static final MappingDiscoverer DISCOVERER = new AnnotationMappingDiscoverer(RequestMapping.class);
    private final MethodInvocation invocation;

    public ControllerBaseUriResolverFactory(Object invocationValue) {
        Assert.isInstanceOf(LastInvocationAware.class, invocationValue);
        LastInvocationAware invocations = DummyInvocationUtils.getLastInvocationAware(invocationValue);
        org.springframework.hateoas.server.core.MethodInvocation  invocation = invocations.getLastInvocation();

        Class<?> targetClass = invocation.getTargetType();
        Method targetMethod = invocation.getMethod();
        Object[] arguments = invocation.getArguments();

        this.invocation = new MethodInvocation(targetClass, targetMethod, arguments);

        AnnotationExtractor ae = new AnnotationExtractor(targetMethod);
        pathVariables = ae.extract(PathVariable.class);
        requestParams = ae.extract(RequestParam.class);
    }

    /**
     * @return a new ControllerUriResolver representing the uri of the invocation value
     */
    public ControllerUriResolver build() {

        UriTemplate uriTemplate = new UriTemplate(DISCOVERER.getMapping(invocation.getTargetClass(), invocation.getTargetMethod()));

        return new ControllerUriResolver(uriTemplate, pathVariables, requestParams, invocation);
    }

}
