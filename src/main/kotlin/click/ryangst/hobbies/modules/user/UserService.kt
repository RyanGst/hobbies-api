package click.ryangst.hobbies.modules.user

import click.ryangst.hobbies.exceptions.RequiredObjectIsNullException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(
    @field:Autowired var repository: UserRepository,
) : UserDetailsService {

    private val logger = Logger.getLogger(UserService::class.java.name)

    fun findById(id: Long) {

    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw RequiredObjectIsNullException("Username is required")
        }
        val user = repository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
        return user
    }
}
