package commonClient.data.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class SearchHistoryCache(
    private val settings: DataStore<Preferences>,
) : Cache<List<String>> {

    val data: Flow<List<String>> = settings.data.map {
        it[KEY_CACHE_SEARCH_HISTORY] ?: emptySet()
    }.map {
        it.toList()
    }

    suspend fun put(value: String) {
        settings.updateData {
            it.toMutablePreferences().apply {
                val list = (get(KEY_CACHE_SEARCH_HISTORY) ?: emptySet()).toMutableList()
                list.remove(value)
                list.add(0, value)
                if (list.size > 7) list.removeAt(7)
                set(KEY_CACHE_SEARCH_HISTORY, list.toSet())
            }
        }
    }

    suspend fun remove(value: String) {
        settings.updateData {
            it.toMutablePreferences().apply {
                val list = (get(KEY_CACHE_SEARCH_HISTORY) ?: emptySet()).toMutableList()
                list.remove(value)
                set(KEY_CACHE_SEARCH_HISTORY, list.toSet())
            }
        }
    }

    suspend fun clear() {
        settings.updateData {
            it.toMutablePreferences().apply {
                set(KEY_CACHE_SEARCH_HISTORY, emptySet())
            }
        }
    }

    override suspend fun save(value: List<String>) {
        settings.updateData {
            it.toMutablePreferences().apply {
                set(KEY_CACHE_SEARCH_HISTORY, value.take(7).toSet())
            }
        }
    }

    companion object {
        private val KEY_CACHE_SEARCH_HISTORY = stringSetPreferencesKey("CACHE_SEARCH_HISTORY")
    }

}