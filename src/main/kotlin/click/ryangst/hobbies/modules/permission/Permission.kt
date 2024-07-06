package click.ryangst.hobbies.modules.permission

import jakarta.persistence.*

@Entity
@Table(name = "permission")
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "description", nullable = false)
    var description: String = ""
)
