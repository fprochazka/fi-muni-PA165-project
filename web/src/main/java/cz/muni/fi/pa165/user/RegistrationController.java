package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.response.RedirectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Controller
public class RegistrationController
{

    private final UserFacade userFacade;

    @Autowired
    public RegistrationController(UserFacade userFacade)
    {
        this.userFacade = userFacade;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new RegistrationValidator());
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView viewRegister() throws Exception
    {
        return new ModelAndView("user/register")
            .addObject("registerRequest", new RegisterRequest());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView submitRegister(
        @ModelAttribute("registerRequest") @Validated RegisterRequest registerRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("user/register")
                .addObject("registerRequest", registerRequest);
        }

        userFacade.createUser(
            registerRequest.email,
            registerRequest.password
        );

        return new RedirectResponse("/sign-in");
    }

    /**
     * Validates register form data.
     */
    public class RegistrationValidator implements Validator
    {

        @Override
        public boolean supports(Class type)
        {
            return RegisterRequest.class.isAssignableFrom(type);
        }

        @Override
        public void validate(Object target, Errors errors)
        {
            RegisterRequest request = (RegisterRequest) target;

            if (!(request.getPassword().equals(request.getPasswordRepeat()))) {
                errors.rejectValue("password", "notmatch.password");
            }
        }

    }

}
