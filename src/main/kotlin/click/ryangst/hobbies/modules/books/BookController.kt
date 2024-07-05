package click.ryangst.hobbies.modules.books

import click.ryangst.hobbies.data.vo.v1.BookVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController {

    @Autowired
    private lateinit var bookService: BookService

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(
        @PathVariable id: Long
    ): BookVO {
        return bookService.findById(id)
    }


    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<BookVO> {
        return bookService.findAll()
    }

    @PostMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(
        @RequestBody person: BookVO
    ): BookVO {
        return bookService.save(person)
    }

    @PutMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(
        @RequestBody person: BookVO
    ): BookVO {
        return bookService.update(person)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        bookService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
