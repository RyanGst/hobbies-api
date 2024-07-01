package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.data.vo.v1.PersonVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping("/person/{id}")
    fun findById(
        @PathVariable id: Long
    ): PersonVO {
        return personService.findById(id)
    }


    @GetMapping("/person")
    fun findAll(): List<PersonVO> {
        return personService.findAll()
    }

    @PostMapping(
        "/person",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.save(person)
    }

    @PutMapping(
        "/person",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.update(person)
    }

    @DeleteMapping("/person/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        personService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
