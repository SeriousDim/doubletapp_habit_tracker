package ru.seriousdim.habittracker.activities.habit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.databinding.ActivityHabitBinding
import ru.seriousdim.habittracker.dataclasses.Habit

class HabitActivity : AppCompatActivity(), IHabitFormListener {
    companion object {
        val RESULT_HABIT_KEY = "RESULT_HABIT_KEY"
        val HABIT_INDEX = "HABIT_INDEX"
    }

    private lateinit var binding: ActivityHabitBinding

    private lateinit var editingHabit: Habit

    private var habitIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitBinding.inflate(layoutInflater)

        habitIndex = intent.extras?.getInt(HABIT_INDEX)!!

        val fragment = HabitFragment(this)
        fragment.arguments = intent.extras

        supportFragmentManager
            .beginTransaction()
            .add(R.id.habitLayoutContainer, fragment)
            .commit()

        val view = binding.root
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_habit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_habit_item_save -> {
                editingHabit?.let {
                    val intent = Intent()
                    intent.putExtra(RESULT_HABIT_KEY, editingHabit.toBundle())
                    intent.putExtra(HABIT_INDEX, habitIndex)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                    return true
                }
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onFormChange(habit: Habit) {
        this.editingHabit = habit
    }
}