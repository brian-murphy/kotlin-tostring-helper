package formats

import annotations.ToString
import kotlin.reflect.KCallable
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.isAccessible

data class IncludedProperty(val name: String, val value: String)

fun isToStringAnnotation(annotation: Annotation): Boolean {
    return annotation is ToString
}

fun memberNameAndValue(member: KCallable<*>, instance: Any): IncludedProperty {
    val annotation = member.annotations.first(::isToStringAnnotation) as ToString
    val name = if (annotation.name.isNotEmpty()) annotation.name else member.name
    member.isAccessible = true
    val value = member.call(instance)

    return IncludedProperty(name, value.toString())
}