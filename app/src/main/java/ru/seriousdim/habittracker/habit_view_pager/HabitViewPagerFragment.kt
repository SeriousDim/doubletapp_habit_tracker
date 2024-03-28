package ru.seriousdim.habittracker.habit_view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.databinding.FragmentHabitViewPagerBinding
import ru.seriousdim.habittracker.dataclasses.Habit
import ru.seriousdim.habittracker.dataclasses.HabitPeriod
import ru.seriousdim.habittracker.dataclasses.HabitPriority
import ru.seriousdim.habittracker.dataclasses.HabitType
import ru.seriousdim.habittracker.habit_fragment_new.HABIT_RESULT

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HabitViewPagerFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val habitTabNames = listOf(
        R.string.habit_type_1_plural,
        R.string.habit_type_2_plural
    )

    private lateinit var pagerAdapter: HabitListPagerAdapter

    private var _binding: FragmentHabitViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHabitViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Habit>(HABIT_RESULT)
            ?.observe(viewLifecycleOwner) {
                    result -> pagerAdapter.addHabit(result)
            }

        val habits = ArrayList(listOf(
            Habit("Привычка 1", "Программировать", R.color.habit_color_1,
                HabitPriority.LOW, HabitType.GOOD, HabitPeriod(2, 3)
            ),
            Habit("Привычка 1", "Программировать", R.color.habit_color_3,
                HabitPriority.LOW, HabitType.GOOD, HabitPeriod(3, 4)
            ),
            Habit("Привычка 1", "Поздно ложиться спать", R.color.habit_color_1,
                HabitPriority.LOW, HabitType.BAD, HabitPeriod(3, 10)
            ),
            Habit("Привычка 1", "Поздно вставать", R.color.habit_color_2,
                HabitPriority.LOW, HabitType.BAD, HabitPeriod(5, 11)
            ),
            Habit("Привычка 1", "Поздно вставать", R.color.habit_color_4,
                HabitPriority.LOW, HabitType.BAD, HabitPeriod(7, 12)
            ))
        )

        pagerAdapter = HabitListPagerAdapter(this)

        habits.forEach {
                item -> pagerAdapter.addHabit(item)
        }

        binding.habitViewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.habitViewPagerTabs, binding.habitViewPager) {
                tab, position -> tab.text = getString(habitTabNames[position])
        }.attach()

        binding.addHabitButton.setOnClickListener {
            view ->
                findNavController().navigate(R.id.action_nav_habit_list_to_habit_fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HabitViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}