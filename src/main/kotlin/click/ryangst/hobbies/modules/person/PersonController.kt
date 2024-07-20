package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.data.vo.v1.PersonVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person")
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ): PersonVO {
        return personService.findById(id)
    }


    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun findAll(): List<PersonVO> {
        return personService.findAll()
    }

    @PostMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.save(person)
    }

    @PutMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.update(person)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        personService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}")
    fun updateEnabled(
        @PathVariable id: Long,
        @RequestParam enabled: Boolean
    ): ResponseEntity<PersonVO> {
        return ResponseEntity.ok(personService.updateEnabled(id, enabled))
    }

}
