package cn.l13z.aspect;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import cn.l13z.model.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * ClassName: GlobalExceptionHandler.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 03:28 <br> Description: 全局异常处理 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 全局异常处理 <br>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1 内部API调用的异常处理
     */
    @ExceptionHandler(value = ApiException.class)
    public Result handlerApiException(ApiException exception){
        IErrorCode errorCode = exception.getErrorCode();
        if(errorCode!=null){
            return Result.fail(errorCode) ;
        }
        return Result.fail(exception.getMessage()) ;
    }

    /**
     * 2 方法参数校验失败的异常
     * MethodArgumentNotValidException
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if(fieldError!=null){
                return Result.fail(fieldError.getField()+fieldError.getDefaultMessage()) ;
            }
        }
        return Result.fail(exception.getMessage()) ;
    }

    /**
     * 3 对象内部使用Validate 没有校验成功的异常
     */
    @ExceptionHandler(BindException.class)
    public Result handlerBindException(BindException bindException){
        BindingResult bindingResult = bindException.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if(fieldError!=null){
                return Result.fail(fieldError.getField()+fieldError.getDefaultMessage()) ;
            }
        }
        return Result.fail(bindException.getMessage()) ;
    }

}
