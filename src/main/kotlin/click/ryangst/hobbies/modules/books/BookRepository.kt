package click.ryangst.hobbies.modules.books

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long?> {
    @Query("SELECT b FROM Book b WHERE b.author = :author")
    fun findByAuthor(author: String): List<Book>
}
