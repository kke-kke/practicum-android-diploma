package ru.practicum.android.diploma.domain.models

/**
 * Общие параметры фильтра, которые пользователь может выбирать на экране "Фильтр".
 * Все поля nullable, так как пользователь может не указывать некоторые значения.
 */
data class FilterParameters(
    val salaryFrom: Int?,          // уровень ЗП "от"
    val excludeNoSalary: Boolean,  // флаг "Не показывать без ЗП"
    val industryId: String?,       // ID выбранной отрасли
    val industryName: String?,     // нзвание выбранной отрасли

    val countryId: String?,        // ID выбранной страны
    val countryName: String?,      // название выбранной страны
    val regionId: String?,         // ID выбранного региона
    val regionName: String?,       // название выбранного региона
)
