package ru.seriousdim.habittracker.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitPeriod(
    var times: Int,
    var dayInterval: Int
) : Parcelable {
    override fun toString(): String {
        return "${this.times} раз за ${this.dayInterval} дней"
    }
}
