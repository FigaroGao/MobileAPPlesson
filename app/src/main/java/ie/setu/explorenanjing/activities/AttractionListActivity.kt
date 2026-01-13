package ie.setu.explorenanjing.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.explorenanjing.adapters.AttractionAdapter
import ie.setu.explorenanjing.databinding.ActivityAttractionListBinding
import ie.setu.explorenanjing.main.MainApp
import ie.setu.explorenanjing.models.AttractionModel
import kotlinx.coroutines.launch

class AttractionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttractionListBinding
    private lateinit var app: MainApp
    private var allAttractions: List<AttractionModel> = emptyList() // 存储全部景点数据
    private val favHistoryStore get() = app.favHistoryStore

    // 标记当前显示的列表类型：all(全部) / favorite(收藏) / history(历史)
    private var currentListType: String = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAttractionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化MainApp，获取已有存储实例（不修改原有接口逻辑）
        app = application as MainApp

        // RecyclerView 基础配置（保留原有逻辑）
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化：加载全部景点数据
        loadAllAttractions()

        // 处理收藏按钮点击（保留原有命名，补充逻辑）
        binding.btnFavorites.setOnClickListener {
            currentListType = "favorite"
            loadFavoriteAttractions()
            title = "Favorites"
        }

        // 处理历史按钮点击（保留原有命名，补充逻辑）
        binding.btnHistory.setOnClickListener {
            currentListType = "history"
            loadHistoryAttractions()
            title = "History"
        }

        // 可选：添加“全部”按钮（如果布局有，若无则可通过返回键/重新进入恢复）
        // 这里默认打开页面显示全部，也可在布局加btnAll并绑定点击事件
        binding.root.findViewById<android.widget.Button>(android.R.id.button1)?.setOnClickListener {
            currentListType = "all"
            loadAllAttractions()
            title = "All Attractions"
        }
    }

    /**
     * 加载全部景点（调用现有AttractionStore的findAll接口，不修改其内部逻辑）
     */
    private fun loadAllAttractions() {
        // 用lifecycleScope处理协程（适配Activity生命周期）
        lifecycleScope.launch {
            allAttractions = app.attractionStore.findAll() // 调用现有接口
            refreshAdapter(allAttractions) // 刷新列表
            title = "All Attractions"
        }
    }

    /**
     * 加载收藏的景点（基于全部景点 + 收藏ID过滤，不修改FavoriteHistoryStore逻辑）
     */
    private fun loadFavoriteAttractions() {
        val favoriteIds = favHistoryStore.getFavorites()
        val favoriteList = allAttractions.filter { favoriteIds.contains(it.id) }
        refreshAdapter(favoriteList)
    }

    /**
     * 加载历史记录的景点（基于全部景点 + 历史ID过滤，不修改FavoriteHistoryStore逻辑）
     */
    private fun loadHistoryAttractions() {
        val historyIds = favHistoryStore.getHistoryIds()
        val historyList = allAttractions.filter { historyIds.contains(it.id) }
        refreshAdapter(historyList)
    }

    /**
     * 刷新RecyclerView适配器（复用现有AttractionAdapter，传递收藏ID）
     */
    private fun refreshAdapter(list: List<AttractionModel>) {
        val favoriteIds = favHistoryStore.getFavorites()
        binding.recyclerView.adapter = AttractionAdapter(
            list = list,
            favoriteIds = favoriteIds,
            listener = object : AttractionAdapter.Listener {
                // 点击景点项：跳转到详情页（复用现有AttractionDetailActivity）
                override fun onAttractionClick(attraction: AttractionModel) {
                    // 点击后添加到历史记录（调用现有接口）
                    favHistoryStore.addToHistory(attraction.id)
                    // 跳转到详情页
                    startActivity(
                        Intent(this@AttractionListActivity, AttractionDetailActivity::class.java)
                            .putExtra("attraction", attraction)
                    )
                }

                // 点击收藏按钮：切换收藏状态（调用现有接口）
                override fun onFavoriteClick(attraction: AttractionModel) {
                    favHistoryStore.toggleFavorite(attraction.id)
                    // 根据当前显示类型刷新列表
                    when (currentListType) {
                        "all" -> refreshAdapter(allAttractions)
                        "favorite" -> loadFavoriteAttractions()
                        "history" -> loadHistoryAttractions()
                    }
                }
            }
        )
    }
}