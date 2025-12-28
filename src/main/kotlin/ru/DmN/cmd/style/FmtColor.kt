package ru.DmN.cmd.style

enum class FmtColor(val fg: String, val bg: String) {
    BLACK("\u001B[30m", "\u001B[40m"),
    RED("\u001B[31m", "\u001B[41m"),
    GREEN("\u001B[32m", "\u001B[42m"),
    YELLOW("\u001B[33m", "\u001B[43m"),
    BLUE("\u001B[34m", "\u001B[44m"),
    MAGENTA("\u001B[35m", "\u001B[45m"),
    CYAN("\u001B[36m", "\u001B[46m"),
    WHITE("\u001B[37m", "\u001B[47m"),
    BRIGHT_BLACK("\u001B[90m", "\u001B[100m"),
    BRIGHT_RED("\u001B[91m", "\u001B[101m"),
    BRIGHT_GREEN("\u001B[92m", "\u001B[102m"),
    BRIGHT_YELLOW("\u001B[93m", "\u001B[103m"),
    BRIGHT_BLUE("\u001B[94m", "\u001B[104m"),
    BRIGHT_MAGENTA("\u001B[95m", "\u001B[105m"),
    BRIGHT_CYAN("\u001B[96m", "\u001B[106m"),
    BRIGHT_WHITE("\u001B[97m", "\u001B[107m"),
}