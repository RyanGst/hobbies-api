package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.data.vo.v1.PersonVO
import com.fasterxml.jackson.annotation.JsonProperty

class WrapperPersonVO {
    @JsonProperty("_embedded")
    var embedded: PersonEmbeddedVO? = null

    class PersonEmbeddedVO {
        @JsonProperty("personVOList")
        var persons: List<PersonVO>? = null
    }
}
