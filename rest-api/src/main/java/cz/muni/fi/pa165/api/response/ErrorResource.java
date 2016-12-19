package cz.muni.fi.pa165.api.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class ErrorResource
{

    private String code;
    private String message;
    private List<FieldErrorResource> fieldErrors = new ArrayList<>();

    public ErrorResource()
    {
    }

    public ErrorResource(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<FieldErrorResource> getFieldErrors()
    {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorResource> fieldErrors)
    {
        this.fieldErrors = fieldErrors;
    }
}
