package com.diego.iatrainig.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut; // Añadido para mejor organización
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import java.util.Arrays; // Para ver los argumentos si quieres

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Definimos los puntos de corte por separado para que sea más legible
    @Pointcut("execution(* com.diego.iatrainig.service..*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.diego.iatrainig.controllers..*(..))")
    public void controllerLayer() {}

    // Unimos ambos Pointcuts
    @Around("serviceLayer() || controllerLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // En Spring 4, a veces getTarget() puede devolver el Proxy en lugar de la clase.
        // getSignature().getDeclaringTypeName() suele ser más fiable para el nombre de la clase.
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info(">>> [INICIO] {}.{} | Args: {}", className, methodName, Arrays.toString(args));

        StopWatch stopWatch = new StopWatch(className + "->" + methodName);
        stopWatch.start();

        Object result;
        try {
            result = joinPoint.proceed(); // Ejecución del método real
        } catch (Throwable e) {
            // Log de error detallado
            logger.error("!!! [ERROR] {}.{}: {} - Tipo: {}",
                    className, methodName, e.getMessage(), e.getClass().getName());
            throw e;
        } finally {
            // El finally asegura que el reloj se detenga aunque haya excepción
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
        }

        logger.info("<<< [FIN] {}.{} | Tiempo: {}ms | Retorno: {}",
                className, methodName, stopWatch.getTotalTimeMillis(), result);

        return result;
    }
}