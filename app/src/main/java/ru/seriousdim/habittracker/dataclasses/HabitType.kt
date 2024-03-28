package ru.seriousdim.habittracker.dataclasses

import ru.seriousdim.habittracker.R

enum class HabitType(val stringResourceId: Int) {
    GOOD(R.string.habit_type_1),
    BAD(R.string.habit_type_2)
}