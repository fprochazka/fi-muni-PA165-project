package cz.muni.fi.pa165.api.exceptions;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public abstract class Exception extends RuntimeException
{

    private final String code;

    Exception(String code, String message)
    {
        super(message);
        this.code = code;
    }

    Exception(String code, String message, Throwable cause)
    {
        super(message, cause);
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
