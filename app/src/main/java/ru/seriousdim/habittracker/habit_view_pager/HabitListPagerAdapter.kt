package ru.seriousdim.habittracker.habit_view_pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.seriousdim.habittracker.dataclasses.Habit
import ru.seriousdim.habittracker.dataclasses.HabitType
import ru.seriousdim.habittracker.habit_list_fragment.HabitListFragment

class HabitListPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val fragments = arrayOf<HabitListFragment?>(null, null)

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if (position < 0 || position > itemCount)
            throw IndexOutOfBoundsException()

        this.fragments[position] = HabitListFragment.newInstance()
        return fragments[position]!!
    }

    fun addHabit(habit: Habit) {
        if (habit.type == HabitType.GOOD) {
            this.fragments[0]?.adapter?.add(habit)
        } else if (habit.type == HabitType.BAD) {
            this.fragments[1]?.adapter?.add(habit)
        }
    }

}