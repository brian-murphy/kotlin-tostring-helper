package annotations

import formats.toJsonString
import formats.toTupleString

open class TupleTestClass {
    @ToString("asdf")
    val testProp = "my private string"

    @ToString
    private var testVar = "my test variable"

    override fun toString(): String {
        return toTupleString(this)
    }
}

class TestTupleSubclass : TupleTestClass()

class JsonTestClass {
    @ToString("asdf")
    val testProp = "my private string"

    @ToString
    private var testVar = "my test variable"

    override fun toString(): String = toJsonString(this)
}

fun main(args: Array<String>) {
    println(TupleTestClass())
    println(TestTupleSubclass())
    println(JsonTestClass())
}
