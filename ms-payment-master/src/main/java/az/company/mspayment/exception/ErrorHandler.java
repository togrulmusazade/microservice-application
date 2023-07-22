package az.company.mspayment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

import static az.company.mspayment.model.constant.ExceptionConstants.UNEXPECTED_EXCEPTION_CODE;
import static az.company.mspayment.model.constant.ExceptionConstants.UNEXPECTED_EXCEPTION_MESSAGE;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternal(Exception exception) {
        log.error("Exception : ", exception);
        return new ExceptionResponse(UNEXPECTED_EXCEPTION_CODE, UNEXPECTED_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handleNotFound(NotFoundException exception) {
        log.error("NotFoundException : ", exception);
        return new ExceptionResponse(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(UndeclaredThrowableException.class) // InvocationTargetException
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ExceptionResponse handleServiceUnavailable(UndeclaredThrowableException exception) {
        log.error("UndeclaredThrowableException", exception);
        return new ExceptionResponse(SERVICE_UNAVAILABLE.name(), exception.getCause().getCause().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
