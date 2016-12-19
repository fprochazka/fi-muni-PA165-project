package cz.muni.fi.pa165.api.error;

import cz.muni.fi.pa165.api.exceptions.BadRequestException;
import cz.muni.fi.pa165.api.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.api.exceptions.UnprocessableEntityException;
import cz.muni.fi.pa165.api.response.ErrorResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler
{

    private static final Logger log = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler({cz.muni.fi.pa165.api.exceptions.Exception.class})
    public ResponseEntity<Object> apiException(WebRequest request, cz.muni.fi.pa165.api.exceptions.Exception exception)
    {
        return handleExceptionInternal(
            exception,
            new ErrorResource(exception.getCode(), exception.getMessage()),
            new HttpHeaders(),
            getApiExceptionStatus(exception),
            request
        );
    }

    private HttpStatus getApiExceptionStatus(cz.muni.fi.pa165.api.exceptions.Exception e)
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

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> forbidden(WebRequest request, java.lang.Exception exception)
    {
        return handleExceptionInternal(
            exception,
            new ErrorResource("forbidden", "Forbidden"),
            new HttpHeaders(),
            HttpStatus.FORBIDDEN,
            request
        );
    }

    @ExceptionHandler({java.lang.Exception.class})
    public ResponseEntity<Object> internalServerError(WebRequest request, java.lang.Exception exception)
    {
        return handleExceptionInternal(
            exception,
            new ErrorResource("internal-server-error", "Internal server error"),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        if (status.is5xxServerError()) {
            log.error(ex.getMessage(), ex);

        } else if (status.is4xxClientError()) {
            log.warn(ex.getMessage());

        } else if (status.is3xxRedirection()) {
            log.info(ex.getMessage());
        }

        if (body == null) {
            String code = status.getReasonPhrase()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-");
            body = new ErrorResource(code, status.getReasonPhrase());
        }

        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
