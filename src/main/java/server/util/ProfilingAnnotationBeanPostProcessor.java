package server.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Object> beans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        boolean match = Arrays.stream(bean.getClass().getMethods())
                .anyMatch(m -> m.isAnnotationPresent(Profiling.class));
        if (match) {
            beans.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beans.containsKey(beanName)) {
            Object original = beans.get(beanName);
            Object beanProxy = Proxy.newProxyInstance(
                    original.getClass().getClassLoader(),
                    original.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        long start = System.currentTimeMillis();
                        Object result = method.invoke(original, args);
                        long end = System.currentTimeMillis();
                        System.out.println(end - start);
                        return result;
                    }
            );

            return beanProxy;
        }
        return bean;
    }
}
