package click.ryangst.hobbies.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel

data class BookVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long = 0,
    var title: String = "",
    var author: String = "",
    var price: Double = 0.0,
    var launchDate: String = "",
) : RepresentationModel<BookVO>()
