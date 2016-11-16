package cz.muni.fi.pa165.user;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public enum UserRole
{
    USER("user") {
        @Override
        public boolean allowTransition(UserRole role)
        {
            return role == ADMIN || role == MODERATOR;
        }
    },

    MODERATOR("moderator") {
        @Override
        public boolean allowTransition(UserRole role)
        {
            return role == ADMIN;
        }
    },

    ADMIN("admin") {
        @Override
        public boolean allowTransition(UserRole role)
        {
            return false;
        }
    };

    private final String text;

    UserRole(final String text)
    {
        this.text = text;
    }

    /**
     * Verifies if the user can be transitioned to given role.
     */
    public abstract boolean allowTransition(UserRole role);

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return text;
    }

}
