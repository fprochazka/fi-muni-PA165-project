package cz.muni.fi.pa165.user.admin;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.user.User;
import cz.muni.fi.pa165.user.UserFacade;
import cz.muni.fi.pa165.user.UserRepository;
import cz.muni.fi.pa165.user.UserRole;
import cz.muni.fi.pa165.web.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Controller
public class UserAdminController
{

    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Autowired
    public UserAdminController(UserFacade userFacade, UserRepository userRepository)
    {
        this.userFacade = userFacade;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public ModelAndView viewList() throws Exception
    {
        return new ModelAndView("user/admin/list")
            .addObject("users", userRepository.findAllUsers());
    }

    @RequestMapping(value = "/admin/users/{userId}/promote", method = RequestMethod.POST)
    public ModelAndView promoteUser(
        @PathVariable("userId") UUID userId,
        @RequestParam("role") @Validated UserRole role,
        final RedirectAttributes redirectAttributes
    ) throws Exception
    {
        List<String> messages = new ArrayList<>();

        if (role == UserRole.ADMIN) {
            User user = userFacade.promoteUserToAdmin(userId);
            messages.add(String.format("User %s was promoted to admin", user.getEmail()));

        } else if (role == UserRole.MODERATOR) {
            User user = userFacade.promoteUserToModerator(userId);
            messages.add(String.format("User %s was promoted to moderator", user.getEmail()));

        } else {
            throw new BadRequestException("Invalid role");
        }

        redirectAttributes.addFlashAttribute("flashMessages", messages);

        return new RedirectResponse("/admin/users");
    }

}
