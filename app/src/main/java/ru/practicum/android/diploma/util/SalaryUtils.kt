package ru.practicum.android.diploma.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object SalaryUtils {
    private fun divideIntoDigits(number: Int?): String {
        val russianLocale = Locale("ru", "RU")
        val russianSymbols = DecimalFormatSymbols(russianLocale)
        val russianFormatter = DecimalFormat("#,###.##", russianSymbols)
        return russianFormatter.format(number)
    }

    fun getCurrencySymbol(currencyCode: String): String {
        return Currency.entries.find { it.currency == currencyCode }?.symbol ?: currencyCode
    }

    fun getVacancySalary(from: Int?, to: Int?): String {
        return when {
            from != null && to != null -> "от ${divideIntoDigits(from)} до ${divideIntoDigits(to)}"
            from != null -> "от ${divideIntoDigits(from)}"
            to != null -> "до ${divideIntoDigits(to)}"
            else -> "Зарплата не указана"
        }
    }
}
