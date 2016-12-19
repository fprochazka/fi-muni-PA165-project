package cz.muni.fi.pa165.user.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Controller
public class UserAuthenticationController
{

    @RequestMapping(value = "/sign-in", method = RequestMethod.GET)
    public ModelAndView viewLogin(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        HttpServletRequest request
    ) throws Exception
    {
        ModelAndView response = new ModelAndView("user/authentication/sign-in");

        if (error != null) {
            response.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }

        if (logout != null) {
            response.addObject("msg", "You've been logged out successfully.");
        }

        return response;
    }

    private String getErrorMessage(HttpServletRequest request, String key)
    {
        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }

        return error;
    }

}
