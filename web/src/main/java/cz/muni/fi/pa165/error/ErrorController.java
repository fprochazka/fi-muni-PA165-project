package cz.muni.fi.pa165.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Controller
public class ErrorController
{

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView forbidden()
    {
        return new ModelAndView("error/403");
    }

}
