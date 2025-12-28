package ru.DmN.cmd.style

@Suppress("NOTHING_TO_INLINE")
object FmtUtils {
    fun CharSequence.fmt(vararg args: Pair<String, Any?>): String {
        var foregroundColor: FmtColor? = null
        var backgroundColor: FmtColor? = null
        var styles: MutableList<FmtStyle> = mutableListOf()
        val out = StringBuilder()
        var i = 0
        global@while (i < this.length) {
            out.append(
                when (val char = this[i++]) {
                    '§' -> {
                        when (val char = this[i++]) {
                            '§' -> '§'
                            'f' -> {
                                foregroundColor = colorOf(this[i++])
                                foregroundColor.fg
                            }
                            'b' -> {
                                backgroundColor = colorOf(this[i++])
                                backgroundColor.bg
                            }
                            's' -> {
                                val style = styleOf(this[i++])
                                if (style == FmtStyle.RESET)
                                    styles = mutableListOf()
                                else styles += style
                                style.text
                            }
                            '{' -> {
                                val name = StringBuilder()
                                while (i < this.length) {
                                    val char = this[i++]
                                    if (char == '}') {
                                        val name = name.toString()
                                        val value = args.find { it.first == name }
                                            ?: throw IllegalArgumentException("Formatting value '${name}' not founded")
                                        val formatting =
                                            StringBuilder().apply {
                                                append(FmtStyle.RESET.text)
                                                foregroundColor?.let { append(it.fg) }
                                                backgroundColor?.let { append(it.bg) }
                                                styles.forEach { append(it.text) }
                                            }.toString()
                                        out.append(value.second.toString().replace(FmtStyle.RESET.text, formatting))
                                        continue@global
                                    }
                                    name.append(char)
                                }
                                throw IllegalArgumentException("Incompleted formatting value declaration '${name}'")
                            }
                            else -> throw IllegalArgumentException("Unexpected formatting code '$char'")
                        }
                    }
                    else -> char
                }
            )
        }
        return out.append(FmtStyle.RESET.text).toString()
    }

    val CharSequence.fmt: String get() {
        val out = StringBuilder()
        var i = 0
        while (i < this.length) {
            out.append(
                when (val char = this[i++]) {
                    '§' -> {
                        when (val char = this[i++]) {
                            '§' -> '§'
                            'f' -> colorOf(this[i++]).fg
                            'b' -> colorOf(this[i++]).bg
                            's' -> styleOf(this[i++]).text
                            '{' -> throw IllegalArgumentException("Formatting values not allowed")
                            else -> throw IllegalArgumentException("Unexpected formatting code '$char'")
                        }
                    }
                    else -> char
                }
            )
        }
        return out.append(FmtStyle.RESET.text).toString()
    }

    private inline fun colorOf(char: Char) = when (char) {
        '0' -> FmtColor.BLACK
        '1' -> FmtColor.RED
        '2' -> FmtColor.GREEN
        '3' -> FmtColor.YELLOW
        '4' -> FmtColor.BLUE
        '5' -> FmtColor.MAGENTA
        '6' -> FmtColor.CYAN
        '7' -> FmtColor.WHITE
        '8' -> FmtColor.BRIGHT_BLACK
        '9' -> FmtColor.BRIGHT_RED
        'a' -> FmtColor.BRIGHT_GREEN
        'b' -> FmtColor.BRIGHT_YELLOW
        'c' -> FmtColor.BRIGHT_BLUE
        'd' -> FmtColor.BRIGHT_MAGENTA
        'e' -> FmtColor.BRIGHT_CYAN
        'f' -> FmtColor.BRIGHT_WHITE
        else -> throw IllegalArgumentException("Unexpected color code '$char'")
    }

    private inline fun styleOf(char: Char): FmtStyle = when (char) {
        'r' -> FmtStyle.RESET
        'b' -> FmtStyle.BOLD
        'i' -> FmtStyle.ITALIC
        'u' -> FmtStyle.UNDERLINE
        else -> throw IllegalArgumentException("Unexpected style code '$char'")
    }
}