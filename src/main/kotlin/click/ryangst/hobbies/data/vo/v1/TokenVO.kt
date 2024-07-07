package click.ryangst.hobbies.data.vo.v1

import java.util.Date

data class TokenVO(
    var username: String? = null,
    val authenticated: Boolean = false,
    val created: Date? = null,
    val expiration: Date? = null,
    val token: String? = null,
    val refreshToken: String? = null
)
