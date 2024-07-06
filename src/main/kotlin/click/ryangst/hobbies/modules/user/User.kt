package click.ryangst.hobbies.modules.user

import jakarta.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["user_name"])])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "user_name", nullable = true, length = 255)
    val userName: String? = null,

    @Column(name = "full_name", nullable = true, length = 255)
    val fullName: String? = null,

    @Column(name = "password", nullable = true, length = 255)
    val password: String? = null,

    @Column(name = "account_non_expired", nullable = true)
    val accountNonExpired: Boolean? = null,

    @Column(name = "account_non_locked", nullable = true)
    val accountNonLocked: Boolean? = null,

    @Column(name = "credentials_non_expired", nullable = true)
    val credentialsNonExpired: Boolean? = null,

    @Column(name = "enabled", nullable = true)
    val enabled: Boolean? = null
)
