package ie.setu.explorenanjing.store

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ie.setu.explorenanjing.models.AttractionModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

interface AttractionApiService {
    @GET("attractions.json")
    suspend fun getAttractions(): List<AttractionModel>
}

class AttractionApiStore(baseUrl: String) : AttractionStore {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(AttractionApiService::class.java)
    private val cache = mutableMapOf<Long, AttractionModel>()

    override suspend fun findAll(): List<AttractionModel> = withContext(Dispatchers.IO) {
        try {
            val list = service.getAttractions()
            list.forEach { cache[it.id] = it }
            list
        } catch (e: Exception) {
            Timber.e(e, "Failed to load attractions")
            emptyList()
        }
    }

    override fun findById(id: Long): AttractionModel? = cache[id]
}