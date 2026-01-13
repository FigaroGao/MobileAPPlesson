package ie.setu.explorenanjing.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.setu.explorenanjing.databinding.ItemCarouselBinding

class ImageCarouselAdapter(
    private val ctx: Context,
    private val urls: List<String>
) : RecyclerView.Adapter<ImageCarouselAdapter.VH>() {

    inner class VH(val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(ctx).load(urls[position]).into(holder.binding.image)
    }

    override fun getItemCount() = urls.size
}