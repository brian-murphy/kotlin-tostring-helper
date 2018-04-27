package formats

class JsonFormatter(val singleLine: Boolean = false) : Formatter() {
    override fun makeHeader(): String {
        return if (singleLine) "{" else "{\n"
    }

    override fun formatMember(name: String, value: Any): String {
        return if (singleLine) {
            " \"${name}\": \"${value}\","
        } else {
            "\t\"${name}\": \"${value}\",\n"
        }
    }

    override fun finalize(stringSoFar: String): String {
        val minusLastComma = stringSoFar.dropLast(1)
        return if (singleLine) {
            minusLastComma + " }"
        } else {
            minusLastComma + "\n}"
        }
    }
}
