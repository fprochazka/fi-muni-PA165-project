package cz.muni.fi.pa165.api.response;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class FieldErrorResource
{

    private String field;
    private String message;

    public FieldErrorResource(String resource, String field, String code, String message)
    {
        this.field = field;
        this.message = message;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
