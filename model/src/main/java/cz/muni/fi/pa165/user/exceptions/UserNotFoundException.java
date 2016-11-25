package cz.muni.fi.pa165.user.exceptions;

import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class UserNotFoundException extends RuntimeException
{

    private final UUID userId;

    public UserNotFoundException(final UUID userId, Throwable cause)
    {
        super(String.format("User %s not found", userId.toString()), cause);
        this.userId = userId;
    }

    public UUID getUserId()
    {
        return userId;
    }

}
