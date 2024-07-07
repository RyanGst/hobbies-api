package click.ryangst.hobbies.modules.user

import click.ryangst.hobbies.modules.permission.Permission
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["user_name"])])
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0

    @Column(name = "user_name", nullable = true, length = 255)
    val userName: String? = null

    @Column(name = "full_name", nullable = true, length = 255)
    val fullName: String? = null

    @Column(name = "password", nullable = true, length = 255)
    private val password: String? = null

    @Column(name = "account_non_expired", nullable = true)
    val accountNonExpired: Boolean? = null

    @Column(name = "account_non_locked", nullable = true)
    val accountNonLocked: Boolean? = null

    @Column(name = "credentials_non_expired", nullable = true)
    val credentialsNonExpired: Boolean? = null

    @Column(name = "enabled", nullable = true)
    val enabled: Boolean? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_permission",
        joinColumns = [JoinColumn(name = "id_user")],
        inverseJoinColumns = [JoinColumn(name = "id_permission")]
    )
    var permissions = mutableListOf<Permission>()

    val roles get() = permissions.map { it.description }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return permissions.toMutableList()
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return userName!!
    }
}
