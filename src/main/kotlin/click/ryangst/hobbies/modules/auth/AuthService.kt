package click.ryangst.hobbies.modules.auth

import click.ryangst.hobbies.data.vo.v1.AccountCredentialsVO
import click.ryangst.hobbies.data.vo.v1.TokenVO
import click.ryangst.hobbies.modules.user.UserRepository
import click.ryangst.hobbies.modules.user.UserService
import click.ryangst.hobbies.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class AuthService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var userRepository: UserRepository

    private val logger = Logger.getLogger(UserService::class.java.name)

    fun signIn(data: AccountCredentialsVO): ResponseEntity<*> {
        logger.info("Trying log user ${data.username}")

        return try {
            val username = data.username
            val password = data.password
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            val user = userRepository.findByUsername(username)

            val token = if (user != null) {
                tokenProvider.createAccessToken(username!!, user.roles)
            } else {
                throw UsernameNotFoundException("User ($username) not found")
            }

            ResponseEntity.ok(token)
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("Invalid username or password")
        }
    }
}
