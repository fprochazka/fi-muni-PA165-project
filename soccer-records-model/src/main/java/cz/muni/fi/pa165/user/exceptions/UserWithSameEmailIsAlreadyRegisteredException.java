package cz.muni.fi.pa165.user.exceptions;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class UserWithSameEmailIsAlreadyRegisteredException extends RuntimeException
{

    private final String email;

    public UserWithSameEmailIsAlreadyRegisteredException(String email)
    {
        super(String.format("User with email %s is already registered", email));
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

}
