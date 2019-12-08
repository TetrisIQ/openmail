package de.openmail.config

import de.openmail.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService : UserDetailsService {

    @Autowired
	lateinit var userRepository: UserRepository

	fun currentUser(): Optional<de.openmail.model.User> {
		val auth = SecurityContextHolder.getContext().getAuthentication()
		val user = userRepository.findByUsername((auth.getPrincipal() as UserDetails).getUsername())
		if (user.isPresent()) {
            return Optional.of(user.get())
		}
		return Optional.empty()
	}

	override fun loadUserByUsername(username: String): UserDetails? {
		val user = userRepository.findByUsername(username)
		if (user.isPresent()) {
			return userDetails(user.get())
		}
		return null
	}

	private fun userDetails(user: de.openmail.model.User): User {
		val privileges = listOf(SimpleGrantedAuthority(user.role))
		return User(user.username, user.password, true, true, true, true, privileges)
	}

}
