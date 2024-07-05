package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.exceptions.RequiredObjectIsNullException
import click.ryangst.hobbies.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository


    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findById() {
        val mockEntity = inputObject.mockEntity()
        `when`(repository.findById(0)).thenReturn(Optional.of(mockEntity))
        val personVO = service.findById(0)
        assertEquals(0, personVO.key)
        assertEquals("First Name Test0", personVO.firstName)
        assertEquals("Last Name Test0", personVO.lastName)
        assertEquals("Address Test0", personVO.address)
    }

    @Test
    fun findAll() {
        val mockEntityList = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(mockEntityList)
        val personVOList = service.findAll()
        assertEquals(14, personVOList.size)
        assertEquals(0, personVOList[0].key)
        assertEquals("First Name Test0", personVOList[0].firstName)
        assertEquals("Last Name Test0", personVOList[0].lastName)
        assertEquals("Address Test0", personVOList[0].address)
    }

    @Test
    fun `save with null person`() {
        val exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.save(null)
        }

        assertEquals("Required object is null.", exception.message)

    }

    @Test
    fun save() {
        val mockEntity = inputObject.mockEntity()
        `when`(repository.save(mockEntity)).thenReturn(mockEntity)
        val personVO = service.save(inputObject.mockVO())
        assertEquals(0, personVO.key)
        assertEquals("First Name Test0", personVO.firstName)
        assertEquals("Last Name Test0", personVO.lastName)
        assertEquals("Address Test0", personVO.address)
    }

    @Test
    fun update() {
        val mockEntity = inputObject.mockEntity()
        `when`(repository.findById(0)).thenReturn(Optional.of(mockEntity))
        `when`(repository.save(mockEntity)).thenReturn(mockEntity)
        val personVO = service.update(inputObject.mockVO())
        assertEquals(0, personVO.key)
        assertEquals("First Name Test0", personVO.firstName)
        assertEquals("Last Name Test0", personVO.lastName)
        assertEquals("Address Test0", personVO.address)
    }

    @Test
    fun delete() {
        val mockEntity = inputObject.mockEntity()
        `when`(repository.findById(0)).thenReturn(Optional.of(mockEntity))
        service.delete(0)
    }
}
