package cz.muni.fi.pa165.user.exceptions;

import cz.muni.fi.pa165.user.User;
import cz.muni.fi.pa165.user.UserRole;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class UserCannotBePromotedToRoleExceptions extends RuntimeException
{

    private final User user;
    private final UserRole newRole;

    public UserCannotBePromotedToRoleExceptions(final User user, final UserRole newRole)
    {
        super(String.format(
            "User %s with role %s cannot be promoted to role %s",
            user.getId().toString(),
            user.getRole().toString(),
            newRole.toString()
        ));
        this.user = user;
        this.newRole = newRole;
    }

    public User getUser()
    {
        return user;
    }

    public UserRole getNewRole()
    {
        return newRole;
    }
}
