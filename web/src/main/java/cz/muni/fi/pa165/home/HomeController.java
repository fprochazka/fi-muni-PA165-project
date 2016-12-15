package cz.muni.fi.pa165.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Controller
public class HomeController
{

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView viewRegister() throws Exception
    {
        return new ModelAndView("home");
    }

}
