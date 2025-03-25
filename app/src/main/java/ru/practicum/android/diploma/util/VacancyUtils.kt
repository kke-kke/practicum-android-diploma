package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Salary
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object VacancyUtils {
    fun divideIntoDigits(number: Int?): String {
        if (number == null) return ""
        val russianLocale = Locale("ru", "RU")
        val russianSymbols = DecimalFormatSymbols(russianLocale)
        val russianFormatter = DecimalFormat("#,###.##", russianSymbols)
        return russianFormatter.format(number)
    }

    fun getCurrencySymbol(currencyCode: String?): String {
        return currencyCode?.let { code ->
            Currency.entries.find { it.currency == code }?.symbol ?: code
        } ?: ""
    }

    fun getVacancySalary(from: Int?, to: Int?): String {
        return when {
            from != -1 && to != -1 -> "от ${divideIntoDigits(from)} до ${divideIntoDigits(to)}"
            from != -1 -> "от ${divideIntoDigits(from)}"
            to != -1 -> "до ${divideIntoDigits(to)}"
            else -> "Зарплата не указана"
        }
    }

    fun Salary?.toSalaryString(): String {
        return this?.let { salary ->
            val salaryText = getVacancySalary(salary.from, salary.to)
            val currencySymbol = getCurrencySymbol(salary.currency)
            if (salaryText == "Зарплата не указана") salaryText else "$salaryText $currencySymbol"
        } ?: "Зарплата не указана"
    }
}
