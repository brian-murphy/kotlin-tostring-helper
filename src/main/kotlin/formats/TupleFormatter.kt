package formats

class TupleFormatter() : Formatter() {
    override fun makeHeader(): String {
        return "("
    }

    override fun formatMember(name: String, value: Any): String {
        return value.toString() + ", "
    }

    override fun finalize(stringSoFar: String): String {
        return stringSoFar.dropLast(2) + ")"
    }
}

