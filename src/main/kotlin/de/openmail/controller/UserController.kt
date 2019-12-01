package de.openmail.controller

import com.google.gson.Gson
import de.openmail.config.UserService
import de.openmail.model.Account
import de.openmail.model.Server
import de.openmail.model.User
import de.openmail.repository.AccountRepository
import de.openmail.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.net.ssl.HttpsURLConnection

@Controller
@RequestMapping("user")
class UserController(
    @Autowired val userService: UserService,
    @Autowired val accountRepository: AccountRepository
) {


    @GetMapping
    @ResponseBody
    fun getAll(): List<UserResponse> {
        var users = userService.userRepository.findAll()
        //return Gson().toJson(userResponse(users)) //for Some reason Spring try to send a mail at this point
        return userResponse(users)              //So we need to return a String
    }



    private fun userResponse(users: Iterable<User>): List<UserResponse> {
        var ret : MutableList<UserResponse> = mutableListOf()
        for (user in users) {
            ret.add(UserResponse(user.id,user.username,user.role,serverResponse(user.server)))
        }
        return ret
    }

    private fun accountResponse(accounts: List<Account>): MutableList<AccountResponse> {
        var ls = mutableListOf<AccountResponse>()
        for(a in accounts) {
            ls.add(AccountResponse(a.id,a.name,a.imapFolders))
        }
        return ls
    }

    fun serverResponse(server: MutableList<Server>): MutableList<ServerResponse> {
        var ls = mutableListOf<ServerResponse>()
        for(s in server) {
            ls.add(ServerResponse(s.id,s.host,accountResponse(s.account)))
        }
        return ls
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody user: User) {
        user.password = BCryptPasswordEncoder().encode(user.password)
        userService.userRepository.save(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addServer(@RequestBody server: Server) {
        var user = userService.currentUser()
        if (user == null) {
            throw Exception("User not Found")
        }
        user.addServer(server)
        userService.userRepository.save(user)
    }

    @PutMapping("/account/{serverId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addAccount(@RequestBody account: Account, @PathVariable serverId : Int) {
        var user : User? = userService.currentUser()
        if (user == null) {
            throw Exception("User not Found")
        }
        user.getServerById(serverId).get().addAccount(account)
        accountRepository.save(account)
        userService.userRepository.save(user)

    }


}
