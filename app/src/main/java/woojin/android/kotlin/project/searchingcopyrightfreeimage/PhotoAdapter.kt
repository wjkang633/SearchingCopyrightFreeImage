package woojin.android.kotlin.project.searchingcopyrightfreeimage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import woojin.android.kotlin.project.searchingcopyrightfreeimage.data.models.PhotoResponse
import woojin.android.kotlin.project.searchingcopyrightfreeimage.databinding.ItemPhotoBinding


class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var photos: List<PhotoResponse> = emptyList()
    var onClickPhoto:(PhotoResponse)->Unit ={}

    inner class ViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClickPhoto(photos[adapterPosition])
            }
        }

        fun bind(photo: PhotoResponse) {

            //사진 크기 미리 산정
            val dimensionRation = photo.height / photo.width.toFloat()
            val targetWidth = binding.root.resources.displayMetrics.widthPixels - (binding.root.paddingStart + binding.root.paddingEnd)
            val targetHeight = (targetWidth * dimensionRation).toInt()

            binding.contentsContainer.layoutParams =
                    binding.contentsContainer.layoutParams.apply {
                        height = targetHeight
                    }

            //기본 이미지
            Glide.with(binding.root)
                    .load(photo.urls?.regular)
                    .thumbnail(
                            Glide.with(binding.root)
                                    .load(photo.urls?.thumb)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                    )
                    .override(targetWidth, targetHeight)
                    .into(binding.photoImageView)

            //프로필 이미지
            Glide.with(binding.root)
                    .load(photo.user?.profileImageUrls?.small)
                    .placeholder(R.drawable.shape_profile_placeholder)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
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