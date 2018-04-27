package formats

abstract class Formatter {
    open fun makeHeader(): String {
        return ""
    }

    abstract fun formatMember(name: String, value: Any): String

    open fun finalize(stringSoFar: String): String {
        return stringSoFar
    }
}
