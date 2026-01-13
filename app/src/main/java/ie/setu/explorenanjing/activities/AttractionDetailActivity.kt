package ie.setu.explorenanjing.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ie.setu.explorenanjing.databinding.ActivityAttractionDetailBinding
import ie.setu.explorenanjing.models.AttractionModel

class AttractionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttractionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttractionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val attraction = intent.getParcelableExtra<AttractionModel>("attraction")!!

        binding.title.text = attraction.name
        binding.address.text = "Address: ${attraction.address}"
        binding.time.text = "Opening hours: ${attraction.openingHours}"
        binding.level.text = "Level: ${attraction.level}"
        binding.price.text = "Price: ${attraction.priceInfo}"
        binding.desc.text = attraction.description
        binding.ratingBar.rating = attraction.rating

        Glide.with(this)
            .load(attraction.imageUrl)
            .into(binding.backgroundImage)

        if (attraction.reservationUrl.isNotEmpty()) {
            binding.reservation.text = Html.fromHtml("<a href='${attraction.reservationUrl}'>Reservation / Booking</a>")
            binding.reservation.movementMethod = LinkMovementMethod.getInstance()
        }

        binding.btnComment.setOnClickListener {
            startActivity(Intent(this, CommentsActivity::class.java).apply {
                putExtra("attractionId", attraction.id)
                putExtra("attractionName", attraction.name)
            })
        }
    }
}