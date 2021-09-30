package ajou.withme.locationData.controller;

import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.dto.IsExistUserDto;
import ajou.withme.locationData.dto.SaveUserDto;
import ajou.withme.locationData.service.UserService;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResFormat saveUser(@RequestBody  SaveUserDto saveUserDto) {
        User user = saveUserDto.toEntity();
        userService.saveUser(user);

        User userByName = userService.findUserByName(user.getName());
        if (userByName != null) {
            return new ResFormat(false, 400L, "해당 이름의 user가 이미 존재합니다.");
        }

        return new ResFormat(true, 201L, user);
    }

    @PostMapping("/exist")
    public ResFormat isNotExistUserByName(@RequestBody IsExistUserDto isExistUserDto) {
        User userByName = userService.findUserByName(isExistUserDto.getName());

        if (userByName == null) {
            return new ResFormat(true, 200L, "해당 이름의 user가 존재하지 않습니다.");
        }
        return new ResFormat(false, 400L, "해당 이름의 user가 이미 존재합니다.");

    }

}
