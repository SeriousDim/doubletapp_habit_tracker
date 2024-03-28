package ru.seriousdim.habittracker.habit_fragment_new

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.databinding.FragmentHabitBinding
import ru.seriousdim.habittracker.dataclasses.Habit
import ru.seriousdim.habittracker.dataclasses.HabitPeriod
import ru.seriousdim.habittracker.dataclasses.HabitPriority
import ru.seriousdim.habittracker.dataclasses.HabitType

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val HABIT_RESULT = "HABIT_RESULT"

class HabitFragmentNew : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHabitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun createHabit(): Habit {
        return Habit(
            "Example Habit",
            "Example description",
            R.color.habit_color_10,
            HabitPriority.MEDIUM,
            HabitType.BAD,
            HabitPeriod(10, 150)
        )
    }

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
        _binding = FragmentHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_habit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.menu_habit_item_save -> {
                        findNavController()
                            .previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(HABIT_RESULT, createHabit())

                        findNavController().popBackStack()

                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HabitFragmentNew().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}