package ru.practicum.android.diploma.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object VacancyUtils {
    fun divideIntoDigits(number: Int): String {
        val russianLocale = Locale("ru", "RU")
        val russianSymbols = DecimalFormatSymbols(russianLocale)
        val russianFormatter = DecimalFormat("#,###.##", russianSymbols)
        return russianFormatter.format(number)
    }

    fun getCurrencySymbol(currencyCode: String): String {
        return Currency.entries.find { it.currency == currencyCode }?.symbol ?: currencyCode
    }

    fun getVacancySalary(from: Int, to: Int): String {
        return when {
            from != -1 && to != -1 -> "от ${divideIntoDigits(from)} до ${divideIntoDigits(to)}"
            from != -1 -> "от ${divideIntoDigits(from)}"
            to != -1 -> "до ${divideIntoDigits(to)}"
            else -> "Зарплата не указана"
        }
    }

    fun getVacancyWord(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "вакансия"
            count % 10 in 2..4 && count % 100 !in 12..14 -> "вакансии"
            else -> "вакансий"
        }
    }
}
