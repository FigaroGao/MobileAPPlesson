package ie.setu.explorenanjing.store

import ie.setu.explorenanjing.models.CommentModel

interface CommentStore {
    fun findForAttraction(attractionId: Long): List<CommentModel>
    fun create(comment: CommentModel)
    fun delete(comment: CommentModel)
}