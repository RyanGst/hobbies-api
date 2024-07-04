package click.ryangst.hobbies.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException : RuntimeException {
    constructor() : super("Required object is null.")
    constructor(exception: String?) : super(exception)
}
