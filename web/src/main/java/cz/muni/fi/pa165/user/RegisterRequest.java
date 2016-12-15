package cz.muni.fi.pa165.user;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class RegisterRequest
{

    @NotEmpty
    public String email;

    @NotEmpty
    public String password;

    @NotEmpty
    public String passwordRepeat;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPasswordRepeat()
    {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat)
    {
        this.passwordRepeat = passwordRepeat;
    }
}
