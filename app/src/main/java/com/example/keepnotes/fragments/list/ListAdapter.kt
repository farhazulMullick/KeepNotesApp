package com.example.keepnotes.fragments.list

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.R
import com.example.keepnotes.database.Notes
import com.example.keepnotes.databinding.RowLayoutBinding
import androidx.compose.ui.res.stringResource as androidxComposeUiResStringResource

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<Notes>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle: TextView = itemView.findViewById(R.id.title_tv)
        val mPriority: CardView = itemView.findViewById(R.id.priority_indicator)
        val mDescription: TextView = itemView.findViewById(R.id.description_tv)
        val mRowBackground: CardView = itemView.findViewById(R.id.row_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.mTitle.text = currentItem.title
        holder.mPriority.setCardBackgroundColor(
                when (currentItem.priority) {
                    "HIGH" -> ContextCompat.getColor(holder.itemView.context, R.color.red)
                    "MEDIUM" -> ContextCompat.getColor(holder.itemView.context, R.color.gold)
                    else -> ContextCompat.getColor(holder.itemView.context, R.color.leafgreen)
                }
        )
        holder.mDescription.text = currentItem.description.toString().trim()
        holder.mRowBackground.setOnClickListener { view->
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            view?.findNavController()?.navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(notes: List<Notes>){
        this.dataList = notes
        notifyDataSetChanged()
    }


}