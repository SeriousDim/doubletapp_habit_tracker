package ru.seriousdim.habittracker.habit_list_fragment

import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.databinding.HabitListItemBinding
import ru.seriousdim.habittracker.dataclasses.Habit

class HabitViewHolder(private val viewBinding: HabitListItemBinding,
                      private val habitItemListener: IHabitItemListener)
    : RecyclerView.ViewHolder(viewBinding.root), OnClickListener {
    private val habitTypes = viewBinding.root.resources.getStringArray(R.array.habit_priorities)

    fun bind(item: Habit) {
        viewBinding.habitTitle.text = item.title
        viewBinding.habitDescription.text = item.description

        viewBinding.habitPriority.text = habitTypes[item.priority.index]
        viewBinding.habitType.text = viewBinding.root.resources.getString(item.type.stringResourceId)

        viewBinding.habitPeriod.text = item.period.toString()

        val color = ContextCompat.getColor(viewBinding.habitColor.context, item.colorResource)
        viewBinding.habitColor.setBackgroundColor(color)

        viewBinding.root.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        this.habitItemListener.onHabitItemClick(adapterPosition)
    }
}