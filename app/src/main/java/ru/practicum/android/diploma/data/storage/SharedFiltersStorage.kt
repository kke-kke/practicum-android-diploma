package ru.practicum.android.diploma.data.storage

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.util.deserialize
import ru.practicum.android.diploma.util.serialize
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

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

    override fun getDraftFilters(): FilterParameters? {
        return prefLock.read {
            sharedPref.getString(DRAFT_FILTERS, null)
                ?.deserialize<FilterParameters>(gson)
        }
    }

    override fun putDraftFilters(filters: FilterParameters) {
        val filtersToPut: String = filters.serialize(gson)
        prefLock.write {
            sharedPref.edit {
                putString(DRAFT_FILTERS, filtersToPut)
            }
        }
    }

    override fun clearDraftFilters() {
        prefLock.write {
            sharedPref.edit { remove(DRAFT_FILTERS) }
        }
    }

    companion object {
        private const val PREFERENCES = "app_preferences"
        private const val FILTERS = "filters"
        private const val DRAFT_FILTERS = "draft_filters"
    }
}
