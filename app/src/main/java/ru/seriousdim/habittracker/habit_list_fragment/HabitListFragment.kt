package ru.seriousdim.habittracker.habit_list_fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ru.seriousdim.habittracker.MainActivity
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.activities.habit.HabitActivity
import ru.seriousdim.habittracker.databinding.FragmentHabitListBinding
import ru.seriousdim.habittracker.dataclasses.Habit
import ru.seriousdim.habittracker.dataclasses.HabitPeriod
import ru.seriousdim.habittracker.dataclasses.HabitPriority
import ru.seriousdim.habittracker.dataclasses.HabitType


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HabitListFragment : Fragment(), IHabitItemListener {
    companion object {
        val RESULT_HABIT_KEY = "RESULT_HABIT_KEY"
        val HABIT_INDEX = "HABIT_INDEX"
    }

    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data

                val habitBundle = intent?.extras?.getBundle(RESULT_HABIT_KEY)
                val index = intent?.extras?.getInt(HABIT_INDEX)!!

                if (habitBundle != null) {
                    if (index < items.size) {
                        items.removeAt(index)
                    }
                    items.add(index, Habit(habitBundle))
                    binding.habitList.adapter?.notifyItemChanged(index)
                }
            }
    }

    val items = mutableListOf<Habit>(
        Habit("Заниматься спортом", "Бегать и прыгать", R.color.habit_color_3, HabitPriority.LOW, HabitType.TYPE_1, HabitPeriod(3, 7)),
        Habit("Программировать", "Заниматься Android", R.color.habit_color_1, HabitPriority.HIGH, HabitType.TYPE_2, HabitPeriod(5, 14)),
    )

    private var _binding: FragmentHabitListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.habitList.adapter = HabitListAdapter(items, this)

        (activity as MainActivity).setFabClickListener {view ->
            val intent = Intent(context, HabitActivity::class.java)
                .putExtra(HABIT_INDEX, items.size)

            launcher.launch(intent)
        }
    }

    override fun onHabitItemClick(position: Int) {
        val intent = Intent(context, HabitActivity::class.java)
            .putExtra(RESULT_HABIT_KEY, items[position].toBundle())
            .putExtra(HABIT_INDEX, position)

        launcher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}