package ie.setu.explorenanjing.store

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.setu.explorenanjing.models.CommentModel
import timber.log.Timber
import java.io.File

class JsonCommentStore(private val context: Context) : CommentStore {
    private val gson = Gson()
    private val file = File(context.filesDir, "comments.json")
    private val comments = mutableListOf<CommentModel>()
    private var nextId = 1L

    init { load() }

    private fun load() {
        if (!file.exists()) return
        try {
            val json = file.readText()
            val type = object : TypeToken<List<CommentModel>>() {}.type
            val list: List<CommentModel> = gson.fromJson(json, type) ?: return
            comments.addAll(list)
            nextId = comments.maxOfOrNull { it.id }?.plus(1) ?: 1L
        } catch (e: Exception) { Timber.e(e) }
    }

    private fun save() {
        try { file.writeText(gson.toJson(comments)) }
        catch (e: Exception) { Timber.e(e) }
    }

    override fun findForAttraction(attractionId: Long): List<CommentModel> =
        comments.filter { it.attractionId == attractionId }
            .sortedByDescending { it.timestamp }

    override fun create(comment: CommentModel) {
        comment.id = nextId++
        comments.add(comment)
        save()
    }

    override fun delete(comment: CommentModel) {
        comments.removeAll { it.id == comment.id }
        save()
    }
}