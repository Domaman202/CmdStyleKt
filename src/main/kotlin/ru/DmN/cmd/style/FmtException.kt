package ru.DmN.cmd.style

import ru.DmN.translate.Language
import ru.DmN.translate.TranslationKey
import ru.DmN.translate.exception.ITranslatedThrowable
import ru.DmN.translate.exception.ThrowableTranslator
import ru.DmN.translate.provider.ResourceTranslationProvider

class FmtException(val key: TranslationKey, val value: Any?) : Exception(), ITranslatedThrowable<FmtException> {
    override val translator: ThrowableTranslator<FmtException> get() = Translator
    override val message: String get() = Translator.translate(Language.ENGLISH, this)

    private object Translator : ThrowableTranslator<FmtException>() {
        private val PROVIDER = ResourceTranslationProvider("ru/dmn/cmd/style/lang")

        override fun translate(language: Language, throwable: FmtException): String =
            PROVIDER.translate(language, throwable.key, "value" to throwable.value)
    }
}