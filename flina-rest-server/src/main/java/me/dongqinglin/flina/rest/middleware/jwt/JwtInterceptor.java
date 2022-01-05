package me.dongqinglin.flina.rest.middleware.jwt;

import me.dongqinglin.flina.rest.data.Helper.TextHelper;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.relation.VisitorToUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@Order(1)
public class JwtInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    private static final String REQ_START_TIME = "request-start-time";
    public static final String REQ_VISITOR_NAME = "request-visitor-name";
    public static final String TOKEN_START = "Bearer ";
    @Autowired private VisitorToUserRepo repo;

    public JwtInterceptor() {
        super();
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQ_START_TIME, System.currentTimeMillis());
        //http的header中获得token
        String token = request.getHeader(JwtHelper.USER_LOGIN_TOKEN);
        //token不存在
        if (TextHelper.isEmpty(token)) throw new ApiException("请先登录");
        //验证token
        if(!token.startsWith(TOKEN_START)) throw new ApiException("请先登录");
        token = token.substring(TOKEN_START.length());
        String visitorName = JwtHelper.getVisitorName(token);
        if (TextHelper.isEmpty(visitorName))
            throw new ApiException("token验证失败");
        request.setAttribute(REQ_VISITOR_NAME, visitorName);
        Optional<VisitorToUserWithStatus> oneByVisitor_name = repo.findOneByVisitor_name(visitorName);
        if(!oneByVisitor_name.isPresent()) {
            throw new ApiException("token验证失败");
        }
        // todo jwt重放攻击
        //更新token有效时间 (如果需要更新其实就是产生一个新的token)
        if (JwtHelper.isNeedUpdate(token)){
            String newToken = JwtHelper.createToken(visitorName);
            response.setHeader(JwtHelper.USER_LOGIN_TOKEN, newToken);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
         long start = (Long) request.getAttribute(REQ_START_TIME);
         logger.info("The URL {} use {} ms", request.getRequestURL().toString(), System.currentTimeMillis() - start);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
