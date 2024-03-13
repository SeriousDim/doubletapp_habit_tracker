package ru.seriousdim.habittracker.dataclasses

data class HabitPeriod(
    var times: Int,
    var dayInterval: Int
) {
    override fun toString(): String {
        return "${this.times} раз за ${this.dayInterval} дней"
    }
}
