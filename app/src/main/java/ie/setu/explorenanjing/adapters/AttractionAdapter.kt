package ie.setu.explorenanjing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.setu.explorenanjing.databinding.CardAttractionBinding
import ie.setu.explorenanjing.models.AttractionModel

class AttractionAdapter(
    private val list: List<AttractionModel>,
    private val listener: Listener,
    private val favoriteIds: Set<Long> = emptySet()   // 新增：传进来收藏的id集合
) : RecyclerView.Adapter<AttractionAdapter.VH>() {

    interface Listener {
        fun onAttractionClick(attraction: AttractionModel)
        fun onFavoriteClick(attraction: AttractionModel)
    }

    inner class VH(val binding: CardAttractionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(CardAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        with(holder.binding) {
            title.text = item.name
            ratingBar.rating = item.rating
            Glide.with(root.context).load(item.imageUrl).into(image)

            // 判断是否已收藏
            btnFav.isSelected = favoriteIds.contains(item.id)

            root.setOnClickListener { listener.onAttractionClick(item) }
            btnFav.setOnClickListener {
                listener.onFavoriteClick(item)
            }
        }
    }

    override fun getItemCount() = list.size
}