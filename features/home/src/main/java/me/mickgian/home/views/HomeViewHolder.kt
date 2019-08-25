package me.mickgian.home.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.mickgian.home.HomeViewModel
import me.mickgian.home.databinding.ItemHomeBinding
import me.mickgian.model.User

class HomeViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    private val binding = ItemHomeBinding.bind(parent)

    fun bindTo(user: User, viewModel: HomeViewModel) {
        binding.user = user
        binding.viewmodel = viewModel
    }
}