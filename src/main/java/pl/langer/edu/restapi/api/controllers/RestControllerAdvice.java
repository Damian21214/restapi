package pl.langer.edu.restapi.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.langer.edu.restapi.api.messages.BaseApiResponse;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.infrastructure.webhelpers.JsonNameResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Damian Langer on 01.10.16.
 */
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    private final JsonNameResolver jsonNameResolver;

    @Autowired
    public RestControllerAdvice(JsonNameResolver jsonNameResolver) {
        this.jsonNameResolver = jsonNameResolver;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<BaseApiResponse> notFoundException(Exception exception, WebRequest request){
        BaseApiResponse msg = new BaseApiResponse(exception.getMessage(), request, null);
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<BindingError> errors = new ArrayList<>(ex.getBindingResult().getErrorCount());

        final Class<?> clazz = ex.getParameter().getParameterType();
        final Map<String, String> jsnonNameDictionary = this.jsonNameResolver.jsonNameDictionary(clazz);

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->  {
                    BindingError error = new BindingError(
                            jsnonNameDictionary.get(fieldError.getField()), fieldError.getDefaultMessage());
                    errors.add(error);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    class BindingError {
        private String jsonFieldName;
        private String message;

        public BindingError(String jsonFieldName, String message) {
            this.jsonFieldName = jsonFieldName;
            this.message = message;
        }

        public String getJsonFieldName() {
            return jsonFieldName;
        }

        public void setJsonFieldName(String jsonFieldName) {
            this.jsonFieldName = jsonFieldName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
