package com.my.springboot.exception;

import com.my.springboot.utils.Result.ResponseJson;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理 无法处理filter抛出的异常
 * <p>
 * BasicExceptionController——SpringBoot默认处理异常方式，用于异常跳转到/error，可实现自定义错误页面请求。
 *
 * @ExceptionHandle注解——只能在控制器中定义异常处理方法。
 * @ControllerAdvice+@ExceptionHandler——增强控制前Controller实现异常拦截。 SimpleMappingExceptionResolver——拦截异常跳转到error页面。
 * HandlerExceptionResolver——实现HandlerExceptionResolver拦截异常
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 */
@RestControllerAdvice
public class GlobalExceptionCapture {

    /**
     * 全局异常处理，比如：系统404,500 第三方validated(参数校验)等 e instanceof 类型判断
     * validated 参数校验异常处理。
     * ConstraintViolationException ：参数上加@RequestParam或参数加@NotBlank @NotNull等
     * MethodArgumentNotValidException：前段以json格式有效
     * BindException：表单提交有效，对于以json格式提交将会失效
     */
//    这个可以更改真实的http状态码
//1**	信息，服务器收到请求，需要请求者继续执行操作
//2**	成功，操作被成功接收并处理
//3**	重定向，需要进一步的操作以完成请求
//4**	客户端错误，请求包含语法错误或无法完成请求
//5**	服务器错误，服务器在处理请求的过程中发生了错误
// 200	OK	请求成功。一般用于GET与POST请求
//201	Created	已创建。成功请求并创建了新的资源
//202	Accepted	已接受。已经接受请求，但未处理完成
//400	Bad Request	客户端错误，请求包含语法错误或无法完成请求
//401	Unauthorized	请求要求用户的身份认证
//403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
//404	Not Found	服务器无法根据客户端的请求找到资源（网页）
//500	Internal Server Error	服务器内部错误，无法完成请求
//501	Not Implemented	服务器不支持请求的功能，无法完成请求
//503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求
    @ExceptionHandler
    public ResponseEntity globalErrorHandler(Exception e) {
//        validated
        if (e instanceof MethodArgumentNotValidException) {
            System.out.println("---------1----------" + e.getMessage());
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            return ResponseJson.fail(400,
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining(";"))
            );

//        validated
        } else if (e instanceof ConstraintViolationException) {
            System.out.println("---------2----------" + e.getMessage());
            ConstraintViolationException ex = (ConstraintViolationException) e;
            return ResponseJson.fail(400,
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(";"))
            );

            //        validated
        } else if (e instanceof BindException) {
            System.out.println("---------3----------" + e.getMessage());
            BindException ex = (BindException) e;
            return ResponseJson.fail(400,
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining(";"))
            );
            //        系统异常 没找到接口
        } else if (e instanceof NoHandlerFoundException) {
            System.out.println("---------4----------" + e.getMessage());
            return ResponseJson.fail(404,
                    "404 没找到接口"
            );
            //        系统异常 缺少参数
        } else if (e instanceof MissingServletRequestParameterException) {
            System.out.println("---------5----------" + e.getMessage());
            return ResponseJson.fail(400,
                    "缺少参数"
            );
        } else if (e instanceof GlobalException) {
            System.out.println("---------6----------" + e.getMessage());
            if (((GlobalException) e).getCode() != null) {
                return ResponseJson.fail(((GlobalException) e).getCode(),
                        e.getMessage()
                );
            } else {
                return ResponseJson.fail(400,
                        e.getMessage()
                );
            }

        } else if (e instanceof AccessDeniedException) {
            return ResponseJson.fail(400,
                    "没有权限"
            );
        } else {
            System.out.println("---------7----------" + e.getClass() + "---" + e.getMessage());
            return ResponseJson.fail(400,
                    "未知异常"
            );

        }
    }

}
