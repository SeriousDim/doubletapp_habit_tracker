package ru.seriousdim.habittracker.activities.habit

import ru.seriousdim.habittracker.dataclasses.Habit

interface IHabitFormListener {
    fun onFormChange(habit: Habit)
}