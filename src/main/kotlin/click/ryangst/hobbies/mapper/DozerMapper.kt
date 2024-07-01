package click.ryangst.hobbies.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper

object DozerMapper {
    private val mapper = DozerBeanMapperBuilder.buildDefault()

    fun <S, D> parseObject(source: S, destination: Class<D>?): D {
        return mapper.map(source, destination)
    }

    fun <S, D> parseObjectList(source: List<S>, destination: Class<D>?): List<D> {
        return source.map { item -> mapper.map(item, destination) }
    }
}
