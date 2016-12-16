package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.exceptions.BadRequestException;
import cz.muni.fi.pa165.api.exceptions.Exception;
import cz.muni.fi.pa165.api.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.api.exceptions.UnprocessableEntityException;
import cz.muni.fi.pa165.api.response.ErrorResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler
{

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(WebRequest request, Exception exception)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(
            exception,
            new ErrorResource(exception.getCode(), exception.getMessage()),
            headers,
            getExceptionStatus(exception),
            request
        );
    }

    @ExceptionHandler({java.lang.Exception.class})
    public ResponseEntity<Object> handleException(WebRequest request, java.lang.Exception exception)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(
            exception,
            new ErrorResource("internal-server-error", "Internal server error"),
            headers,
            HttpStatus.INTERNAL_SERVER_ERROR,
            request
        );
    }

    private HttpStatus getExceptionStatus(Exception e)
    {
        if (e instanceof BadRequestException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof UnprocessableEntityException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (e instanceof ResourceNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
