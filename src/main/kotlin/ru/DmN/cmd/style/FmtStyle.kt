package ru.DmN.cmd.style

enum class FmtStyle(val text: String) {
    RESET("\u001B[00m"),
    BOLD("\u001B[01m"),
    ITALIC("\u001B[03m"),
    UNDERLINE("\u001B[04m"),
}