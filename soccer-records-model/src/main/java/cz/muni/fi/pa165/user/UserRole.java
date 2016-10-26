package cz.muni.fi.pa165.user;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public enum UserRole
{
    USER("user"),
    MODERATOR("moderator"),
    ADMIN("admin");

    private final String text;

    private UserRole(final String text)
    {
        this.text = text;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return text;
    }

}
