package me.dongqinglin.flina.rest.middleware.role;

import me.dongqinglin.flina.rest.data.schema.entity.User;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.StatusRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.VisitorToUserRepo;
import me.dongqinglin.flina.rest.middleware.jwt.ApiException;
import me.dongqinglin.flina.rest.middleware.jwt.JwtInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Aspect
@Component
public class RoleAspect {

    @Autowired
    private VisitorToUserRepo toUserRepo;

    @Autowired
    private StatusRepo statusRepo;


    @Pointcut("@annotation(me.dongqinglin.flina.rest.middleware.role.HasRole)")
    private void permissionCheck() {
    }

    @Around("permissionCheck()")
    public Object permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String requestURI = request.getRequestURI();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        HasRole annotation = signature.getMethod().getAnnotation(HasRole.class);
        String visitorName = (String) request.getAttribute(JwtInterceptor.REQ_VISITOR_NAME);
        Optional<VisitorToUserWithStatus> oneByVisitor_name = toUserRepo.findOneByVisitor_name(visitorName);
        if(!oneByVisitor_name.isPresent()) {
            throw new ApiException("token验证失败");
        }
        VisitorToUserWithStatus visitorToUserWithStatus = oneByVisitor_name.get();
        String role = visitorToUserWithStatus.getUser().getRole().getName();

        switch (annotation.value()) {
            case MANAGER:
                if(!role.equals(User.RoleEnum.MANAGER.toString()))  {
                    throw new ApiException("警告，权限不足，封禁账号");
                }
                break;
            case EDITOR:
                if(!role.equals(User.RoleEnum.MANAGER.toString()) || !role.equals(User.RoleEnum.EDITOR.toString()))  {
                    throw new ApiException("警告，权限不足，封禁账号");
                }
                break;
            case VISITOR:
                if(!role.equals(User.RoleEnum.MANAGER.toString()) || !role.equals(User.RoleEnum.EDITOR.toString()) || !role.equals(User.RoleEnum.VISITOR.toString()))  {
                    throw new ApiException("警告，权限不足，封禁账号");
                }
                break;
            default:
        }
        return joinPoint.proceed();

    }


}
