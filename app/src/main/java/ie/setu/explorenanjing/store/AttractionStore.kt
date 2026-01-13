package ie.setu.explorenanjing.store

import ie.setu.explorenanjing.models.AttractionModel

interface AttractionStore {
    suspend fun findAll(): List<AttractionModel>
    fun findById(id: Long): AttractionModel?
}