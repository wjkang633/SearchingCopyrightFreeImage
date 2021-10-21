package woojin.android.kotlin.project.searchingcopyrightfreeimage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woojin.android.kotlin.project.searchingcopyrightfreeimage.data.models.PhotoResponse
import woojin.android.kotlin.project.searchingcopyrightfreeimage.databinding.ItemPhotoBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var photos: List<PhotoResponse> = emptyList()

    inner class ViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoResponse) {
            Glide.with(binding.root)
                .load(photo.urls?.regular)
                .into(binding.photoImageView)

            Glide.with(binding.root)
                .load(photo.user?.profileImageUrls?.small)
                .into(binding.profileImageView)

            if (photo.user?.name.isNullOrBlank()) {
                binding.authorTextView.isVisible = false
            } else {
                binding.authorTextView.isVisible = true
                binding.authorTextView.text = photo.user?.name
            }

            if (photo.description.isNullOrBlank()) {
                binding.descriptionTextView.isVisible = false
            } else {
                binding.descriptionTextView.isVisible = true
                binding.descriptionTextView.text = photo.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}