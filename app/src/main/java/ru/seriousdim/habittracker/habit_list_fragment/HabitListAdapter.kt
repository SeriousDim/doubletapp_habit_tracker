package ru.seriousdim.habittracker.habit_list_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.seriousdim.habittracker.databinding.HabitListItemBinding
import ru.seriousdim.habittracker.dataclasses.Habit

class HabitListAdapter(
    private val items: List<Habit>,
    private val habitItemListener: IHabitItemListener
): RecyclerView.Adapter<HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = HabitListItemBinding.inflate(inflater, parent, false)
        return HabitViewHolder(viewBinding, habitItemListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}