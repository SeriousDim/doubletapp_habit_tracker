package ru.seriousdim.habittracker.activities.habit

import android.text.Editable
import ru.seriousdim.habittracker.dataclasses.HabitType

data class HabitPeriodFormView(
    val times: Editable,
    val dayInterval: Editable
)

data class HabitFormView(
    var title: Editable,
    var description: Editable,
    var colorResource: Int,
    var priorityIndex: Int,
    var type: HabitType,
    var period: HabitPeriodFormView
)