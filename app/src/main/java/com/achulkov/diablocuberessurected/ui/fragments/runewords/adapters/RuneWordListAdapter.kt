package com.achulkov.diablocuberessurected.ui.fragments.runewords.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achulkov.diablocuberessurected.data.models.DCubeMappedRuneword
import com.achulkov.diablocuberessurected.databinding.ListRunewordItemBinding
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class RuneWordListAdapter @Inject constructor(
    private val imageLoader: ImageLoader,
    private val storage: FirebaseStorage,
    private val gradientSetter: TextViewGradientSetter
) : ListAdapter<DCubeMappedRuneword, RuneWordListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<DCubeMappedRuneword>() {
    override fun areItemsTheSame(oldItem: DCubeMappedRuneword, newItem: DCubeMappedRuneword): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DCubeMappedRuneword, newItem: DCubeMappedRuneword): Boolean {
        return oldItem == newItem
    }
    override fun getChangePayload(oldItem: DCubeMappedRuneword, newItem: DCubeMappedRuneword): Any {
        return newItem
    }
}) {
    interface AdapterItemClickListener{
        fun onAdapterItemClick(runeword : DCubeMappedRuneword)
    }

    fun setListener(listener : AdapterItemClickListener){
        this.listener = listener
    }

    private lateinit var inflater: LayoutInflater
    private lateinit var listener: AdapterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListRunewordItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val runeword: DCubeMappedRuneword = getItem(position)



        holder.binding.title.text = runeword.name
        gradientSetter.setTextViewGradient(holder.binding.title)
        holder.binding.usedWith.text = runeword.inputBaseItem
        holder.binding.subtitle.text = runeword.levelRequirement

        holder.binding.rune2.visibility = View.GONE
        holder.binding.arrow12.visibility = View.GONE
        holder.binding.rune3.visibility = View.GONE
        holder.binding.arrow23.visibility = View.GONE
        holder.binding.rune4.visibility = View.GONE
        holder.binding.arrow34.visibility = View.GONE
        holder.binding.rune5.visibility = View.GONE
        holder.binding.arrow45.visibility = View.GONE
        holder.binding.rune6.visibility = View.GONE
        holder.binding.arrow56.visibility = View.GONE

        var stats = ""
        for(stat in runeword.stats) stats = stats + stat +"\n"
        holder.binding.statsList.text = stats

        for((i, rune) in runeword.inputs.withIndex()){
            var imageView : ImageView = holder.binding.rune1Img
            var runeTitle = holder.binding.runeTitle
            when(i){
                0 -> {
                    holder.binding.rune1.visibility = View.VISIBLE
                    imageView = holder.binding.rune1Img
                    runeTitle = holder.binding.runeTitle
                }
                1 -> {
                    holder.binding.rune2.visibility = View.VISIBLE
                    holder.binding.arrow12.visibility = View.VISIBLE
                    imageView = holder.binding.rune2Img
                    runeTitle = holder.binding.rune2Title
                }
                2 -> {
                    holder.binding.rune3.visibility = View.VISIBLE
                    holder.binding.arrow23.visibility = View.VISIBLE
                    imageView = holder.binding.rune3Img
                    runeTitle = holder.binding.rune3Title
                }
                3 -> {
                    holder.binding.rune4.visibility = View.VISIBLE
                    holder.binding.arrow34.visibility = View.VISIBLE
                    imageView = holder.binding.rune4Img
                    runeTitle = holder.binding.rune4Title
                }
                4 -> {
                    holder.binding.rune5.visibility = View.VISIBLE
                    holder.binding.arrow45.visibility = View.VISIBLE
                    imageView = holder.binding.rune5Img
                    runeTitle = holder.binding.rune5Title
                }
                else -> {
                    holder.binding.rune6.visibility = View.VISIBLE
                    holder.binding.arrow56.visibility = View.VISIBLE
                    imageView = holder.binding.rune6Img
                    runeTitle = holder.binding.rune6Title
                }
            }
            runeTitle.visibility = View.VISIBLE
            runeTitle.text = rune.itemname
            imageLoader
                .load(storage.getReferenceFromUrl(rune.image))
                .centerInside()
                .into(imageView)
        }


    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val context: Context = recyclerView.context

        inflater = LayoutInflater.from(context)

    }


    inner class ViewHolder(var binding: ListRunewordItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onAdapterItemClick(getItem(bindingAdapterPosition))
            }
        }


    }
}