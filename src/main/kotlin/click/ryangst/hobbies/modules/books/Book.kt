package click.ryangst.hobbies.modules.books

import jakarta.persistence.*

@Entity
@Table(name = "book")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "title", nullable = false)
    var title: String = "",
    @Column(name = "author", nullable = false)
    var author: String = "",
    @Column(name = "price", nullable = false)
    var price: Double = 0.0,
    @Column(name = "launch_date", nullable = false)
    var launchDate: String = ""

)
