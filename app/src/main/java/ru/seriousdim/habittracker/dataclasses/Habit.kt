package ru.seriousdim.habittracker.dataclasses

import android.os.Bundle
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.interfaces.IBundleable
import java.time.Period

data class Habit(
    var title: String,
    var description: String,
    var colorResource: Int,
    var priority: HabitPriority,
    var type: HabitType,
    var period: HabitPeriod
): IBundleable {
    companion object {
        fun getHabitPriorityByValue(index: Int): HabitPriority? {
            return HabitPriority.values().firstOrNull { it.index == index }
        }

        fun getHabitTypeByValue(resourceId: Int): HabitType? {
            return HabitType.values().firstOrNull { it.stringResourceId == resourceId }
        }
    }

    constructor(bundle: Bundle) : this(
        bundle.getString("title")!!,
        bundle.getString("description")!!,
        bundle.getInt("colorResource"),

        getHabitPriorityByValue(bundle.getInt("priority"))!!,
        getHabitTypeByValue(bundle.getInt("type"))!!,

        HabitPeriod(
            bundle.getInt("period_times"),
            bundle.getInt("period_dayInterval")
        )
    )

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString("title", title)
            putString("description", description)
            putInt("colorResource", colorResource)

            putInt("priority", priority.index)
            putInt("type", type.stringResourceId)

            // write HabitPeriod
            putInt("period_times", period.times)
            putInt("period_dayInterval", period.dayInterval)
        }
    }
}
