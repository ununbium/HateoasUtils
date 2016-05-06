package rocks.spiffy.spring.hateoas.utils.uri.builder;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;
import rocks.spiffy.spring.hateoas.utils.uri.resolver.ControllerUriResolver;
import rocks.spiffy.spring.hateoas.utils.uri.resolver.ControllerUriResolverProvider;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Provides a way of building templated links, specifically catering for templating @RequestParam(...) values
 *
 * @author Andrew Hill
 */
public class ControllerUriBuilder {

    //default controller provider is just the ControllerUriResolver::on static method
    static ControllerUriResolverProvider controllerProvider = ControllerUriResolver::on;

    /**
     * Provide a templated link to the referenced controller endpoint
     *
     * @param invocationValue a proxy referenced controller method
     * @return a templated link to the referenced controller endpoint
     */
    public static Link linkTo(Object invocationValue) {

        ControllerUriResolver resolver = controllerProvider.on(invocationValue);
        List<RequestParam> parameterAnnotations = resolver.getRequestParameters();

        Class<?> targetClass = resolver.getInvocation().getTargetClass();
        Method targetMethod = resolver.getInvocation().getTargetMethod();
        Object[] parameters = resolver.getInvocation().getParameters();

        String uriTemplateString = ControllerLinkBuilder.linkTo(targetClass, targetMethod, parameters).toUri().toString();

        Iterator<RequestParam> requestParamIterator = parameterAnnotations.iterator();

        if(!parameterAnnotations.isEmpty()) {

            uriTemplateString += "{?";
            while (requestParamIterator.hasNext()) {
                RequestParam parameterAnnotation = requestParamIterator.next();
                uriTemplateString += parameterAnnotation.value();

                if (requestParamIterator.hasNext()) {
                    uriTemplateString += ",";
                }
            }
            uriTemplateString += "}";

        }

        return new Link(uriTemplateString);
    }
}
