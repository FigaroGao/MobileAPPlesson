package ie.setu.explorenanjing.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButtonToggleGroup
import ie.setu.explorenanjing.adapters.CommentAdapter
import ie.setu.explorenanjing.databinding.ActivityCommentsBinding
import ie.setu.explorenanjing.main.MainApp
import ie.setu.explorenanjing.models.CommentModel

class CommentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var app: MainApp
    private var attractionId: Long = 0L
    private var currentFilter: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        attractionId = intent.getLongExtra("attractionId", 0L)
        title = "Comments - ${intent.getStringExtra("attractionName")}"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                currentFilter = when (checkedId) {
                    binding.btnExcellent.id -> "Excellent"
                    binding.btnGood.id -> "Good"
                    binding.btnOrdinary.id -> "Ordinary"
                    binding.btnBad.id -> "Bad"
                    else -> null
                }
                refresh()
            }
        }

        binding.btnAdd.setOnClickListener {
            val text = binding.editComment.text.toString().trim()
            if (text.isNotEmpty()) {
                val category = currentFilter ?: "Good"
                app.commentStore.create(
                    CommentModel(
                        attractionId = attractionId,
                        ratingCategory = category,
                        text = text
                    )
                )
                binding.editComment.text?.clear()
                refresh()
            }
        }

        refresh()
    }

    private fun refresh() {
        var list = app.commentStore.findForAttraction(attractionId)
        currentFilter?.let { list = list.filter { c -> c.ratingCategory == it } }
        binding.recyclerView.adapter = CommentAdapter(list) { comment ->
            // 长按删除自己的评论可以删除
            if (comment.userName == "Visitor") {
                app.commentStore.delete(comment)
                refresh()
            }
        }
    }
}