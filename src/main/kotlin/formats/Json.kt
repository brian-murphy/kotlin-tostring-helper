package formats

import kotlin.reflect.KCallable

fun toJsonString(obj: Any): String {
    fun filterFunc(member: KCallable<*>) = memberNameAndValue(member, obj)

    val includedValues = obj::class.members
            .filter { member -> member.annotations.any(::isToStringAnnotation) }
            .map(::filterFunc)
            .map { nameAndVal -> "\t\"${nameAndVal.name}\" : \"${nameAndVal.value}\","}
            .toTypedArray()

    return includedValues.joinToString("\n", "{\n", "\n}")
}
