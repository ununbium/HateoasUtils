package rocks.spiffy.spring.hateoas.utils.uri.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationExtractor {
    private final Method targetMethod;

    public AnnotationExtractor(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public <T> List<T> extract(Class<T> annotationClass) {
        Annotation[][] parameterAnnotations = targetMethod.getParameterAnnotations();
        List<T> pathVariables = new ArrayList<>();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if(annotationClass.isInstance(annotation)) {
                    pathVariables.add((T) annotation);
                }
            }
        }
        return pathVariables;
    }
}