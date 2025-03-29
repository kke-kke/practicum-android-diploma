package ru.practicum.android.diploma.data

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.FilterParameters
import ru.practicum.android.diploma.util.deserialize
import ru.practicum.android.diploma.util.serialize
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import androidx.core.content.edit

class SharedFiltersStorage(
    private val context: Context,
    private val gson: Gson
) : FiltersStorage {
    private val sharedPref by lazy {
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    private val prefLock = ReentrantReadWriteLock()

    override fun getFilters(): FilterParameters? {
        return prefLock.read {
            sharedPref.getString(FILTERS, null)
                ?.deserialize<FilterParameters>(gson)
        }
    }

    override fun putFilters(filters: FilterParameters) {
        val filtersToPut: String = filters.serialize(gson)
        prefLock.write {
            sharedPref.edit {
                putString(FILTERS, filtersToPut)
            }
        }
    }

    override fun clear() {
        prefLock.write {
            sharedPref.edit { clear() }
        }
    }

    companion object {
        private const val PREFERENCES = "app_preferences_filters"
        private const val FILTERS = "filters"
    }


}
