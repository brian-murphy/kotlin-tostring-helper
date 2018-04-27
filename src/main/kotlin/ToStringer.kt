import annotations.ToString
import formats.Formatter
import formats.JsonFormatter
import formats.TupleFormatter
import kotlin.reflect.KCallable
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

fun toStringer(obj: Any, formatter: Formatter=JsonFormatter()): String {
    val namesAndValues = obj::class.declaredMemberProperties
            .filter { member -> member.visibility == KVisibility.PUBLIC}
            .map { member -> memberNameAndValue(member, obj) }

    val stringBuilder = StringBuilder()
    stringBuilder.append(formatter.makeHeader())

    namesAndValues
            .map { nameAndValue -> formatter.formatMember(nameAndValue.name, nameAndValue.value) }
            .forEach { memberStringRep -> stringBuilder.append(memberStringRep)}

    return formatter.finalize(stringBuilder.toString())
}

private data class NameAndValue(val name: String, val value: String)

private fun isToStringAnnotation(annotation: Annotation): Boolean {
    return annotation is ToString
}

private fun memberNameAndValue(member: KCallable<*>, instance: Any): NameAndValue {
    val name: String
    val toStringAnnotation = member.annotations.firstOrNull(::isToStringAnnotation)
    if (toStringAnnotation != null && (toStringAnnotation as ToString).name.isNotEmpty()) {
        name = toStringAnnotation.name
    } else {
        name = member.name
    }

    member.isAccessible = true
    val value = member.call(instance)

    return NameAndValue(name, value.toString())
}


class TestClass (val testVal: String) {

    var testVar: String = "testVar value"

    val testProperty1: String
        get() = "testProperty1 value"

    private var backingField = "testProperty2 value"
    var testProperty2: String
        get() = backingField
        set(value) { backingField = value }

    fun publicFun() = "publicFun value"

    private fun privateFun() = "privateFun value"

    override fun toString(): String {
        return toStringer(this, formatter = TupleFormatter())
    }
}

val TestClass.extensionProperty
    get() = "extensionProperty"

fun main(args: Array<String>) {
    val testClass = TestClass("testVal value")

    println(testClass)
}
