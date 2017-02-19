package soselab.mpg.app.monitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExecutionTime {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTime.class);

    @Around("execution(* soselab.mpg.graph.service.*Service*.*(..)) || " +
            "execution(* soselab.mpg.graph.repository.*.*(..)) || " +
            "execution(* soselab.mpg.graph.controller.*.*(..)) ||" +
            "execution(* soselab.mpg.graph.controller.dto.factory.GraphVisualizationFromGraphFactory.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        LOGGER.info("Method {}.{}() execution time : {} ms", className, methodName, elapsedTime);
        return proceed;
    }
}
