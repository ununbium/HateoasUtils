package rocks.spiffy.spring.hateoas.utils.uri;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;

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

        UriTemplate uriTemplate = resolver.getUriTemplate();
        String uriTemplateString = uriTemplate.toString() + "?";
        List<RequestParam> parameterAnnotations = resolver.getRequestParameters();

        Iterator<RequestParam> requestParamIterator = parameterAnnotations.iterator();

        while(requestParamIterator.hasNext()) {
            RequestParam parameterAnnotation = requestParamIterator.next();
            uriTemplateString += parameterAnnotation.value() + "={"+parameterAnnotation.value()+"}";

            if(requestParamIterator.hasNext()) {
                uriTemplateString += "&";
            }
        }

        return new Link(uriTemplateString);
    }
}
