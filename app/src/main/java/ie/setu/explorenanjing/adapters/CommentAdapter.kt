package ie.setu.explorenanjing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.explorenanjing.databinding.ItemCommentBinding
import ie.setu.explorenanjing.models.CommentModel
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(
    private val list: List<CommentModel>,
    private val onLongClick: (CommentModel) -> Unit
) : RecyclerView.Adapter<CommentAdapter.VH>() {

    inner class VH(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = list[position]
        with(holder.binding) {
            userName.text = c.userName
            time.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(c.timestamp))
            content.text = c.text
            category.text = c.ratingCategory
            root.setOnLongClickListener {
                onLongClick(c)
                true
            }
        }
    }

    override fun getItemCount() = list.size
}