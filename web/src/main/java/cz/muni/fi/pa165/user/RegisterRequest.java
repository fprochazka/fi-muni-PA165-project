package cz.muni.fi.pa165.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class RegisterRequest
{

    @NotBlank
    @Size(max = 255)
    public String email;

    @NotBlank
    @Size(min = 8, max = 62)
    public String password;

    @NotBlank
    @Size(min = 8, max = 62)
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
