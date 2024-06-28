package click.ryangst.hobbies

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController {
    val counter = AtomicLong()

    @RequestMapping(value = ["/sum/{num1}/{num2}"])
    fun sum(
        @PathVariable(value = "num1") num1: String?,
        @PathVariable(value = "num2") num2: String?
    ): Double {
        if (!isNumeric(num1) || !isNumeric(num2)) {
            throw UnsupportedOperationException("Please set a numeric value!")
        }

        return convertToDouble(num1) + convertToDouble(num2)
    }

    fun isNumeric(str: String?): Boolean {
        if (str.isNullOrBlank()) return false
        return str.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    fun convertToDouble(str: String?): Double {
        if (str.isNullOrBlank()) return 0.0

        val number = str.toDoubleOrNull()
        return if (isNumeric(str)) number!! else 0.0
    }
}
