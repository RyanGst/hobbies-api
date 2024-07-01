package click.ryangst.hobbies.modules.person

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class PersonVO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "first_name", nullable = false)
    var firstName: String = "",
    @Column(name = "last_name", nullable = false)
    var lastName: String = "",
    @Column
    var address: String = "",
    @Column
    var gender: String = "",
)
