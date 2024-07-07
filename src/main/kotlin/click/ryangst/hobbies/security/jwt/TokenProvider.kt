package click.ryangst.hobbies.security.jwt

import click.ryangst.hobbies.data.vo.v1.TokenVO
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@Service
class TokenProvider {

    @Value("\${security.jwt.token.secret}:secret-key")
    private lateinit var secretKey: String

    @Value("\${security.jwt.token.expiration}:3600000")
    private var validityInMilliseconds: Long = 3600000

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    private lateinit var algorithm: Algorithm

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, roles: List<String>): TokenVO {
        val now = Date()

        return TokenVO(
            username = username,
            authenticated = true,
            created = now,
            expiration = Date(now.time + validityInMilliseconds),
            token = createToken(username, roles, now),
            refreshToken = getRefreshToken(username, roles, now)
        )
    }

    private fun getRefreshToken(username: String, roles: List<String>, now: Date): String? {
        val validityRefreshToken = Date(now.time + 2 * validityInMilliseconds)

        return JWT.create().withClaim("roles", roles).withIssuedAt(now).withSubject(username)
            .withExpiresAt(validityRefreshToken).sign(algorithm)
    }

    private fun createToken(username: String, roles: List<String>, now: Date): String? {
        val issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()

        return JWT.create().withClaim("roles", roles).withIssuedAt(now).withSubject(username)
            .withExpiresAt(Date(now.time + validityInMilliseconds)).withIssuer(issuerUrl).sign(algorithm)
    }
}
