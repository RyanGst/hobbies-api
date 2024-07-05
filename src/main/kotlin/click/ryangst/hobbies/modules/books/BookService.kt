package click.ryangst.hobbies.modules.books

import click.ryangst.hobbies.data.vo.v1.BookVO
import click.ryangst.hobbies.exceptions.RequiredObjectIsNullException
import click.ryangst.hobbies.exceptions.ResourceNotFoundException
import click.ryangst.hobbies.mapper.DozerMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findById(id: Long): BookVO {
        val person = repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id $id")
        })
        val parseObject = DozerMapper.parseObject(person, BookVO::class.java)
        val withSelfLink = linkTo(BookController::class.java).slash(person.id).withSelfRel()
        parseObject.add(withSelfLink)
        return parseObject
    }

    fun findAll(): List<BookVO> {
        val people = repository.findAll()
        return DozerMapper.parseObjectList(people, BookVO::class.java)
    }

    fun save(person: BookVO?): BookVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating person $person")
        val saved = repository.save(DozerMapper.parseObject(person, Book::class.java))
        return DozerMapper.parseObject(saved, BookVO::class.java)
    }

    fun update(person: BookVO): BookVO {
        logger.info("Updating person $person")
        val entity = repository.findById(person.key).orElseThrow({
            ResourceNotFoundException("No record found for id ${person.key}")
        })

        val updated = repository.save(entity)
        return DozerMapper.parseObject(updated, BookVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting person with id $id")
        val entity = repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id ${id}")
        })
        repository.delete(entity)
    }

}
