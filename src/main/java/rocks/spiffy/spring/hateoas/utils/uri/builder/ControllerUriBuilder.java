package rocks.spiffy.spring.hateoas.utils.uri.builder;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestParam;
import rocks.spiffy.spring.hateoas.utils.uri.resolver.ControllerUriResolver;
import rocks.spiffy.spring.hateoas.utils.uri.resolver.ControllerUriResolverProvider;

/**
 * Provides a way of building templated links, specifically catering for templating @RequestParam(...) values
 *
 * @author Andrew Hill
 */
public class ControllerUriBuilder {

    //default controller provider is just the ControllerUriResolver::on static method
    static ControllerUriResolverProvider controllerProvider = ControllerUriResolver::on;

    private static final Pattern TEMPLATE_REQUEST_PARAM_PATTERN = Pattern.compile("\\{\\?.+}");

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

        String uriTemplateString = TEMPLATE_REQUEST_PARAM_PATTERN.matcher(WebMvcLinkBuilder.linkTo(targetClass, targetMethod, parameters).withSelfRel().getHref()).replaceAll("");

        String queryPart;
        try {
            queryPart = new URL(uriTemplateString).getQuery();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (queryPart != null && !queryPart.isEmpty()) {
           uriTemplateString = uriTemplateString.replaceFirst(Pattern.quote("?" + queryPart), "");
        }

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

        return Link.of(uriTemplateString);
    }
}
