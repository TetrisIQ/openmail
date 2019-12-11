package de.openmail.controller

import com.google.gson.Gson
import de.openmail.config.UserService
import de.openmail.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(
    @Autowired val userService: UserService
) {
    @GetMapping
    @ResponseBody
    fun getAll(): String? {
        var users = userService.userRepository.findAll()
        return Gson().toJson(users) //for some reason a user will throw some errors TODO: find a fix for this!
        //return users
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody user: User) {
        user.password = BCryptPasswordEncoder().encode(user.password)
        userService.userRepository.save(user);
    }

    @PostMapping("/update/{password}")
    @ResponseBody
    fun updatePassword(@PathVariable password : String): User {
        var user: User = userService.currentUser().get()
        user.password = BCryptPasswordEncoder().encode(password)
        userService.userRepository.save(user)
        return user
    }
}
