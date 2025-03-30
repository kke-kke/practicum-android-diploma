package ru.practicum.android.diploma.data.database.storage

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.FilterParameters

class FiltersStorage(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val KEY_SALARY_FROM = "filter_salary_from"
        private const val KEY_EXCLUDE_NO_SALARY = "filter_exclude_no_salary"
        private const val KEY_INDUSTRY_ID = "filter_industry_id"
        private const val KEY_INDUSTRY_NAME = "filter_industry_name"
        private const val KEY_COUNTRY_ID = "filter_country_id"
        private const val KEY_COUNTRY_NAME = "filter_country_name"
        private const val KEY_REGION_ID = "filter_region_id"
        private const val KEY_REGION_NAME = "filter_region_name"
    }

    fun saveFilterParameters(params: FilterParameters) {
        sharedPreferences.edit()
            .putInt(KEY_SALARY_FROM, params.salaryFrom ?: -1)
            .putBoolean(KEY_EXCLUDE_NO_SALARY, params.excludeNoSalary)
            .putString(KEY_INDUSTRY_ID, params.industryId)
            .putString(KEY_INDUSTRY_NAME, params.industryName)
            .putString(KEY_COUNTRY_ID, params.countryId)
            .putString(KEY_COUNTRY_NAME, params.countryName)
            .putString(KEY_REGION_ID, params.regionId)
            .putString(KEY_REGION_NAME, params.regionName)
            .apply()
    }

    fun getFilterParameters(): FilterParameters {
        val salaryFromInt = sharedPreferences.getInt(KEY_SALARY_FROM, -1)
        return FilterParameters(
            salaryFrom = if (salaryFromInt >= 0) salaryFromInt else null,
            excludeNoSalary = sharedPreferences.getBoolean(KEY_EXCLUDE_NO_SALARY, false),
            industryId = sharedPreferences.getString(KEY_INDUSTRY_ID, null),
            industryName = sharedPreferences.getString(KEY_INDUSTRY_NAME, null),
            countryId = sharedPreferences.getString(KEY_COUNTRY_ID, null),
            countryName = sharedPreferences.getString(KEY_COUNTRY_NAME, null),
            regionId = sharedPreferences.getString(KEY_REGION_ID, null),
            regionName = sharedPreferences.getString(KEY_REGION_NAME, null)
        )
    }

    fun clearFilterParameters() {
        sharedPreferences.edit()
            .remove(KEY_SALARY_FROM)
            .remove(KEY_EXCLUDE_NO_SALARY)
            .remove(KEY_INDUSTRY_ID)
            .remove(KEY_INDUSTRY_NAME)
            .remove(KEY_COUNTRY_ID)
            .remove(KEY_COUNTRY_NAME)
            .remove(KEY_REGION_ID)
            .remove(KEY_REGION_NAME)
            .apply()
    }
}
