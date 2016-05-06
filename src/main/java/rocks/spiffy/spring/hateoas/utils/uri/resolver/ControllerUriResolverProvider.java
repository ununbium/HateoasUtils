package rocks.spiffy.spring.hateoas.utils.uri.resolver;

/**
 * Functional interface that allows the "on" static method to be decoupled from implementation
 *
 * @author Andrew Hill
 */
public interface ControllerUriResolverProvider {

    /**
     * given an invocationValue, return a ControllerUriResolver representing the reference made by invocationValue
     *
     * @param invocationValue the value to evaluate
     * @return a ControllerUriResolver representing the reference made by invocationValue
     */
    ControllerUriResolver on(Object invocationValue);
}
