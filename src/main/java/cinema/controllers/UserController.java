package cinema.controllers;

import cinema.model.User;
import cinema.model.dto.request.UserRequestDto;
import cinema.service.AuthenticationService;
import cinema.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/byemail",method = RequestMethod.GET)
    public UserRequestDto getByEmail(@RequestParam(name = "email") String email) {
        return getUserDtoFromEntity(userService.findByEmail(email));
    }

    private UserRequestDto getUserDtoFromEntity(User user) {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserId(user.getId());
        return userDto;
    }
}
