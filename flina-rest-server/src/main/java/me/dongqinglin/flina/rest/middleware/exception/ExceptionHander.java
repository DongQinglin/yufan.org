package me.dongqinglin.flina.rest.middleware.exception;

import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.middleware.jwt.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHander {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHander.class);
    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public Message bizExceptionHandler(HttpServletRequest req, ApiException e){
        e.printStackTrace();
        logger.error("发生业务异常！原因是：{}", e.getMessage());
        return Message.createIllegalMessage(e.getMessage());
    }
}

