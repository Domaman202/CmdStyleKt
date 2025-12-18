package ru.DmN.cmd.style

@Suppress("NOTHING_TO_INLINE")
object FmtUtils {
    val String.fmt: String get() {
        val out = StringBuilder()
        var i = 0
        while (i < this.length) {
            val char = this[i++]
            out.append(
                if (char == '§') {
                    when (val char = this[i++]) {
                        '§' -> out.append(char)
                        'f' -> colorOf(this[i++]).fg
                        'b' -> colorOf(this[i++]).bg
                        's' -> styleOf(this[i++]).text
                        else -> throw IllegalArgumentException("Unexpected formatting code '$char'")
                    }
                } else char
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

    private inline fun styleOf(char: Char) = when (char) {
        'r' -> FmtStyle.RESET
        'b' -> FmtStyle.BOLD
        'i' -> FmtStyle.ITALIC
        'u' -> FmtStyle.UNDERLINE
        else -> throw IllegalArgumentException("Unexpected style code '$char'")
    }
}