package ru.seriousdim.habittracker.dataclasses

import ru.seriousdim.habittracker.R

enum class HabitType(val stringResourceId: Int) {
    TYPE_1(R.string.habit_type_1),
    TYPE_2(R.string.habit_type_2),
    TYPE_3(R.string.habit_type_3)
}