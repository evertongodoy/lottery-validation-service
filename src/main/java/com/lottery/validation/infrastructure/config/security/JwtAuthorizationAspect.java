package com.lottery.validation.infrastructure.config.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.lottery.validation.domain.enums.UserRole;
import com.lottery.validation.domain.exceptions.UnauthorizedException;
import com.lottery.validation.infrastructure.adapters.input.rest.annotations.RequireRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class JwtAuthorizationAspect {

    private final JwtService jwtService;

    public JwtAuthorizationAspect(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Around("@annotation(com.lottery.validation.infrastructure.adapters.input.rest.annotations.RequireRole)")
    public Object validateJwtToken(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[validateJwtToken] Validando autorização para método: {}", joinPoint.getSignature().getName());

        // Obter a anotação do método
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequireRole requireRole = signature.getMethod().getAnnotation(RequireRole.class);
        UserRole[] allowedRoles = requireRole.value();

        // Buscar o ServerWebExchange nos argumentos do método
        ServerWebExchange exchange = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof ServerWebExchange) {
                exchange = (ServerWebExchange) arg;
                break;
            }
        }

        if (exchange == null) {
            throw new UnauthorizedException("ServerWebExchange não encontrado nos argumentos do método");
        }

        // Extrair o token do header Authorization
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        String token = jwtService.extractTokenFromHeader(authorizationHeader);

        // Validar o token e verificar a role
        if (!jwtService.hasRole(token, allowedRoles)) {
            UserRole tokenRole = jwtService.getRoleFromToken(token);
            log.warn("[validateJwtToken] Acesso negado. Role do token: {}, Roles permitidas: {}", 
                    tokenRole, allowedRoles);
            throw new UnauthorizedException("Você não tem permissão para acessar este recurso");
        }

        log.info("[validateJwtToken] Autorização concedida para método: {}", joinPoint.getSignature().getName());
        
        // Continuar com a execução do método
        return joinPoint.proceed();
    }
}
