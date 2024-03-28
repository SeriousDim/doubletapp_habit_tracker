package ru.seriousdim.habittracker.habit_list_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.seriousdim.habittracker.databinding.HabitListItemBinding
import ru.seriousdim.habittracker.dataclasses.Habit

class HabitListAdapter(
    private val habitItemListener: IHabitItemListener
): RecyclerView.Adapter<HabitViewHolder>() {

    private val items = mutableListOf<Habit>()

    val size: Int
        get() = this.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = HabitListItemBinding.inflate(inflater, parent, false)
        return HabitViewHolder(viewBinding, habitItemListener)
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    operator fun get(position: Int): Habit {
        return this.items[position]
    }

    fun getAll(): List<Habit> {
        return this.items.toList()
    }

    fun add(habit: Habit) {
        this.items.add(habit)
        this.notifyItemChanged(size - 1)
    }

    fun add(index: Int, habit: Habit) {
        this.items.add(index, habit)
        this.notifyItemChanged(index)
    }

    fun removeAt(index: Int) {
        this.items.removeAt(index)
        this.notifyItemRemoved(index)
    }

}