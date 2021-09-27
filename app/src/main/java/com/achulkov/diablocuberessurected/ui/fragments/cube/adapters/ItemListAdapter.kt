package com.achulkov.diablocuberessurected.ui.fragments.cube.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.data.models.DCubeMappedInput
import com.achulkov.diablocuberessurected.databinding.ListItemBinding
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class ItemListAdapter @Inject constructor(
    private val imageLoader: ImageLoader,
    private val storage: FirebaseStorage
) : ListAdapter<DCubeMappedInput, ItemListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<DCubeMappedInput>() {
    override fun areItemsTheSame(oldItem: DCubeMappedInput, newItem: DCubeMappedInput): Boolean {
        return oldItem.item.itemname == newItem.item.itemname
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DCubeMappedInput, newItem: DCubeMappedInput): Boolean {
        return oldItem == newItem
    }
    override fun getChangePayload(oldItem: DCubeMappedInput, newItem: DCubeMappedInput): Any {
        return newItem
    }
}) {
    interface AdapterItemClickListener{
        fun onAdapterItemClick(item : DCubeMappedInput)
    }

    fun setListener(listener : AdapterItemClickListener){
        this.listener = listener
    }

    private lateinit var inflater: LayoutInflater
    private lateinit var listener: AdapterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val input: DCubeMappedInput = getItem(position)


        holder.binding.title.text = input.item.itemname
        holder.binding.subtitle.text = input.item.itemdesc
        holder.binding.countText.text = String.format(holder.itemView.resources.getString(R.string.count_text), input.count)

        if(input.item.image.isNotEmpty())
            imageLoader
                .load(storage.getReferenceFromUrl(input.item.image))
                .centerInside()
                .into(holder.binding.icon)

    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val context: Context = recyclerView.context

        inflater = LayoutInflater.from(context)

    }


    inner class ViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onAdapterItemClick(getItem(bindingAdapterPosition))
            }
        }


    }
}