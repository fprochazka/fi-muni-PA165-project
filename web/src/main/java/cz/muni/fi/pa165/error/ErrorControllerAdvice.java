package cz.muni.fi.pa165.error;

import cz.muni.fi.pa165.web.exceptions.BadRequestException;
import cz.muni.fi.pa165.web.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@ControllerAdvice
public class ErrorControllerAdvice
{

    private static final Logger log = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({
        AuthenticationException.class,
    })
    public ModelAndView forbidden()
    {
        return new ModelAndView("error/403");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
        ResourceNotFoundException.class,
        NoSuchRequestHandlingMethodException.class,
        NoHandlerFoundException.class,
    })
    public ModelAndView notFound()
    {
        return new ModelAndView("error/404");
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
    })
    public ModelAndView methodNotAllowed()
    {
        return new ModelAndView("error/405");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        BadRequestException.class,
    })
    public ModelAndView badRequest()
    {
        return new ModelAndView("error/4xx");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
        Exception.class,
    })
    public ModelAndView internalError(Exception e)
    {
        log.error(e.getMessage(), e);

        return new ModelAndView("error/5xx");
    }

}
