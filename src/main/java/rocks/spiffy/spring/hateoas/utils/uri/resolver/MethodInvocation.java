package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import java.lang.reflect.Method;

/**
 * @author Andrew Hill
 */
public class MethodInvocation {

    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] parameters;

    public MethodInvocation(Class<?> targetClass, Method targetMethod, Object[] parameters) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.parameters = parameters;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Method getTargetMethod() {
        return this.targetMethod;
    }

    public Object[] getParameters() {
        return this.parameters;
    }
}
