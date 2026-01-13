package ie.setu.explorenanjing.main

import android.app.Application
import ie.setu.explorenanjing.store.*
import timber.log.Timber

class MainApp : Application() {
    lateinit var attractionStore: AttractionStore
    lateinit var commentStore: CommentStore
    lateinit var favHistoryStore: FavoriteHistoryStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        attractionStore = AttractionApiStore("https://cdn.jsdelivr.net/gh/FigaroGao/APPlesson-nanjing-data@master/")
        commentStore = JsonCommentStore(this)
        favHistoryStore = FavoriteHistoryStore(this)

        Timber.i("Explore Nanjing App Started!")
    }
}