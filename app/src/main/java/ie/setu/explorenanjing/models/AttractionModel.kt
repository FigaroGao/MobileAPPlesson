package ie.setu.explorenanjing.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionModel(
    var id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var address: String = "",
    var openingHours: String = "",
    var level: String = "",
    var priceInfo: String = "",
    var imageUrl: String = "",
    var reservationUrl: String = "",
    var rating: Float = 4.5f
) : Parcelable