package ru.DmN.cmd.style

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import ru.DmN.cmd.style.FmtUtils.fmt
import ru.DmN.translate.Language
import kotlin.test.Test
import kotlin.test.assertEquals

class FmtUtilsTest {
    @Test
    @DisplayName("Проверка форматирования через свойство")
    fun validPropertyTest() {
        assertEquals(
            """
                §
                [30mblack
                [31mred
                [32mgreen
                [33myellow
                [34mblue
                [35mmagenta
                [36mcyan
                [37mwhite
                [90mbright_black
                [91mbright_red
                [92mbright_green
                [93mbright_yellow
                [94mbright_blue
                [95mbright_magenta
                [96mbright_cyan
                [97mbright_white
                [40mblack
                [41mred
                [42mgreen
                [43myellow
                [44mblue
                [45mmagenta
                [46mcyan
                [47mwhite
                [100mbright_black
                [101mbright_red
                [102mbright_green
                [103mbright_yellow
                [104mbright_blue
                [105mbright_magenta
                [106mbright_cyan
                [107mbright_white
                [00mreset
                [01mbold
                [03mitalic
                [04munderline
                reset[00m
            """.trimIndent(),
            """
                §§
                §f0black
                §f1red
                §f2green
                §f3yellow
                §f4blue
                §f5magenta
                §f6cyan
                §f7white
                §f8bright_black
                §f9bright_red
                §fabright_green
                §fbbright_yellow
                §fcbright_blue
                §fdbright_magenta
                §febright_cyan
                §ffbright_white
                §b0black
                §b1red
                §b2green
                §b3yellow
                §b4blue
                §b5magenta
                §b6cyan
                §b7white
                §b8bright_black
                §b9bright_red
                §babright_green
                §bbbright_yellow
                §bcbright_blue
                §bdbright_magenta
                §bebright_cyan
                §bfbright_white
                §srreset
                §sbbold
                §siitalic
                §suunderline
                reset
            """.trimIndent().fmt
        )
    }

    @Test
    @DisplayName("Проверка форматирования через метод без значений, но с цветом и стилями")
    fun validNoValuesTest() {
        assertEquals(
            """
                §
                [30mblack
                [31mred
                [32mgreen
                [33myellow
                [34mblue
                [35mmagenta
                [36mcyan
                [37mwhite
                [90mbright_black
                [91mbright_red
                [92mbright_green
                [93mbright_yellow
                [94mbright_blue
                [95mbright_magenta
                [96mbright_cyan
                [97mbright_white
                [40mblack
                [41mred
                [42mgreen
                [43myellow
                [44mblue
                [45mmagenta
                [46mcyan
                [47mwhite
                [100mbright_black
                [101mbright_red
                [102mbright_green
                [103mbright_yellow
                [104mbright_blue
                [105mbright_magenta
                [106mbright_cyan
                [107mbright_white
                [00mreset
                [01mbold
                [03mitalic
                [04munderline
                reset[00m
            """.trimIndent(),
            """
                §§
                §f0black
                §f1red
                §f2green
                §f3yellow
                §f4blue
                §f5magenta
                §f6cyan
                §f7white
                §f8bright_black
                §f9bright_red
                §fabright_green
                §fbbright_yellow
                §fcbright_blue
                §fdbright_magenta
                §febright_cyan
                §ffbright_white
                §b0black
                §b1red
                §b2green
                §b3yellow
                §b4blue
                §b5magenta
                §b6cyan
                §b7white
                §b8bright_black
                §b9bright_red
                §babright_green
                §bbbright_yellow
                §bcbright_blue
                §bdbright_magenta
                §bebright_cyan
                §bfbright_white
                §srreset
                §sbbold
                §siitalic
                §suunderline
                reset
            """.trimIndent().fmt()
        )
    }

    @Test
    @DisplayName("Проверка форматирования через метод со значениями без цветов и стилей")
    fun validValuesNoColorAndStyleTest() {
        assertEquals(
            "i = 12\u001B[00m",
            "i = §{i}".fmt("i" to 12)
        )
    }

    @Test
    @DisplayName("Проверка форматирования через метод со значениями, цветами и стилями")
    fun validValuesAndColorAndStyleTest() {
        assertEquals(
            "\u001B[01m\u001B[34mi = \u001B[31m12\u001B[00m\u001B[34m\u001B[01m\u001B[00m\u001B[34m\u001B[01m!\u001B[00m",
            "§sb§f4i = §{i}!".fmt("i" to "§f112§sr".fmt)
        )
        assertEquals(
            "\u001B[01m\u001B[34mi\u001B[03m\u001B[33m = \u001B[00m\u001B[34m\u001B[01m\u001B[31m21\u001B[00m\u001B[34m\u001B[01m\u001B[33m\u001B[00m\u001B[34m\u001B[01m\u001B[33m\u001B[00m\u001B[34m\u001B[01m!\u001B[00m",
            "§sb§f4i§{op}!".fmt("op" to "§si§f3 = §sr§{i}".fmt("i" to "§f121§sr".fmt))
        )
    }

    @Test
    @DisplayName("Ошибки форматирования через свойство")
    fun exceptionPropertyTest() {
        assertThrows<FmtException> { "§fx".fmt }.let {
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§bx".fmt }.let {
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§sx".fmt }.let {
            assertEquals(
                "Unexpected style code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected style code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§x".fmt }.let {
            assertEquals(
                "Unexpected formatting code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected formatting code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§{i}".fmt }.let {
            assertEquals(
                "Formatting values not allowed".fmt,
                it.message
            )
            assertEquals(
                "Formatting values not allowed".fmt,
                it.translate(Language.ENGLISH)
            )
        }
    }

    @Test
    @DisplayName("Ошибки форматирования через метод")
    fun exceptionMethodTest() {
        assertThrows<FmtException> { "§fx".fmt() }.let {
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§bx".fmt() }.let {
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected color code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§sx".fmt() }.let {
            assertEquals(
                "Unexpected style code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected style code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§x".fmt() }.let {
            assertEquals(
                "Unexpected formatting code '§sbx§sr'".fmt,
                it.message
            )
            assertEquals(
                "Unexpected formatting code '§sbx§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§{i".fmt() }.let {
            assertEquals(
                "Incompleted formatting value declaration '§sbi§sr'".fmt,
                it.message
            )
            assertEquals(
                "Incompleted formatting value declaration '§sbi§sr'".fmt,
                it.translate(Language.ENGLISH)
            )
        }
        assertThrows<FmtException> { "§{i}".fmt() }.let {
            assertEquals(
                "Formatting value '§sbi§sr' not founded".fmt,
                it.message
            )
            assertEquals(
                "Formatting value '§sbi§sr' not founded".fmt,
                it.translate(Language.ENGLISH)
            )
        }
    }
}