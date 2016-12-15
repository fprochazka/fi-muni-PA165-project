package cz.muni.fi.pa165.response;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class RedirectResponse extends ModelAndView
{

    public RedirectResponse(String url)
    {
        super(new RedirectView(url, true));
    }
}
