package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import lombok.Getter;
import java.lang.reflect.Method;

/**
 * @author Andrew Hill
 */
@Getter
public class MethodInvocation {

    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] parameters;

    public MethodInvocation(Class<?> targetClass, Method targetMethod, Object[] parameters) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.parameters = parameters;
    }
}
