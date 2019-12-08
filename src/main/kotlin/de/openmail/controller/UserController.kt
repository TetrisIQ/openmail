package de.openmail.controller

import de.openmail.config.UserService
import de.openmail.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("user")
class UserController(
    @Autowired val userService: UserService
) {
    @GetMapping
    @ResponseBody
    fun getAll(): MutableIterable<User> {
        var users = userService.userRepository.findAll()
        //return Gson().toJson(userResponse(users)) //for Some reason Spring try to send a mail at this point
        return users
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody user: User) {
        user.password = BCryptPasswordEncoder().encode(user.password)
        userService.userRepository.save(user);
    }

}
