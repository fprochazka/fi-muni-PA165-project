package cz.muni.fi.pa165.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception
{

    public BadRequestException(String code, String message)
    {
        super(code, message);
    }

    public BadRequestException(String code, String message, Throwable cause)
    {
        super(code, message, cause);
    }

}
