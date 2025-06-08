package com.inacap.smartflora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inacap.smartflora.databinding.ItemIntroSlideBinding

class IntroAdapter(private val items: List<IntroSlide>) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    inner class IntroViewHolder(private val binding: ItemIntroSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(slide: IntroSlide) {
            binding.imageSlide.setImageResource(slide.image)
            binding.textDescription.text = slide.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val binding = ItemIntroSlideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IntroViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
