package click.ryangst.hobbies.modules.permission

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "permission")
class Permission : GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "description", nullable = false)
    var description: String = ""


    override fun getAuthority() = description
}
