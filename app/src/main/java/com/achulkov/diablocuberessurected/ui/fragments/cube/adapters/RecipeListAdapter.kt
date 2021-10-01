package com.achulkov.diablocuberessurected.ui.fragments.cube.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.data.models.DCubeMappedRecipe
import com.achulkov.diablocuberessurected.databinding.ListRecipeItemBinding
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class RecipeListAdapter @Inject constructor(
    private val imageLoader: ImageLoader,
    private val storage: FirebaseStorage
) : ListAdapter<DCubeMappedRecipe, RecipeListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<DCubeMappedRecipe>() {
    override fun areItemsTheSame(oldItem: DCubeMappedRecipe, newItem: DCubeMappedRecipe): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DCubeMappedRecipe, newItem: DCubeMappedRecipe): Boolean {
        return oldItem == newItem
    }
    override fun getChangePayload(oldItem: DCubeMappedRecipe, newItem: DCubeMappedRecipe): Any {
        return newItem
    }
}) {
    interface AdapterItemClickListener{
        fun onAdapterItemClick(recipe : DCubeMappedRecipe)
    }

    fun setListener(listener : AdapterItemClickListener){
        this.listener = listener
    }

    private lateinit var inflater: LayoutInflater
    private lateinit var listener: AdapterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListRecipeItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe: DCubeMappedRecipe = getItem(position)


        holder.binding.title.text = recipe.name
        holder.binding.subtitle.text = recipe.output.itemdesc
        holder.binding.icon.setImageDrawable(ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.convert_icon, null))


        if(!recipe.output.image.isNullOrEmpty())
        imageLoader
            .load(storage.getReferenceFromUrl(recipe.output.image))
            .centerInside()
            .into(holder.binding.icon)

    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val context: Context = recyclerView.context

        inflater = LayoutInflater.from(context)

    }


    inner class ViewHolder(var binding: ListRecipeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onAdapterItemClick(getItem(bindingAdapterPosition))
            }
        }


    }
}