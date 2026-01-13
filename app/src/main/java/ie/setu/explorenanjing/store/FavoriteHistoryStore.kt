package ie.setu.explorenanjing.store

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class FavoriteHistoryStore(private val context: Context) {
    private val gson = Gson()
    private val favFile = File(context.filesDir, "favorites.json")
    private val historyFile = File(context.filesDir, "history.json")

    private val favorites = mutableSetOf<Long>()
    private val history = mutableListOf<Long>()

    init {
        load(favFile) { favorites.addAll(it) }
        load(historyFile) { history.addAll(it) }
    }

    private inline fun load(file: File, action: (Set<Long>) -> Unit) {
        if (!file.exists()) return
        try {
            val json = file.readText()
            val type = object : TypeToken<Set<Long>>() {}.type
            val set: Set<Long> = gson.fromJson(json, type) ?: return
            action(set)
        } catch (e: Exception) { }
    }

    private fun save(file: File, data: Collection<Long>) {
        try { file.writeText(gson.toJson(data)) } catch (e: Exception) { }
    }

    fun toggleFavorite(id: Long): Boolean {
        return if (favorites.contains(id)) {
            favorites.remove(id)
            save(favFile, favorites)
            false
        } else {
            favorites.add(id)
            save(favFile, favorites)
            true
        }
    }

    fun addToHistory(id: Long) {
        history.remove(id)
        history.add(0, id)
        if (history.size > 50) history.removeAt(history.lastIndex)
        save(historyFile, history)
    }

    fun getFavorites(): Set<Long> = favorites
    fun getHistoryIds(): List<Long> = history
}