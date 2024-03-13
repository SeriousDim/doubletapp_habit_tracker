package ru.seriousdim.habittracker.activities.habit

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import ru.seriousdim.habittracker.R
import ru.seriousdim.habittracker.databinding.FragmentHabitBinding
import ru.seriousdim.habittracker.dataclasses.Habit
import ru.seriousdim.habittracker.dataclasses.HabitPeriod
import ru.seriousdim.habittracker.dataclasses.HabitPriority
import ru.seriousdim.habittracker.dataclasses.HabitType


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HabitFragment(
    private val habitFormListener: IHabitFormListener
) : Fragment() {
    companion object {
        val RESULT_HABIT_KEY = "RESULT_HABIT_KEY"
    }

    private var _binding: FragmentHabitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var habitTypeButtons: Map<HabitType, Int>

    private lateinit var habit: Habit

    private fun createHabitFormView(habit: Habit) {
        binding.habitTitle.text = SpannableStringBuilder(habit.title)
        binding.habitDescription.text = SpannableStringBuilder(habit.description)
        binding.choosedColor.setBackgroundResource(habit.colorResource)

        binding.prioritySpinner.setSelection(habit.priority.index)
        binding.typeGroup.check(habitTypeButtons[habit.type]!!)

        binding.times.text = SpannableStringBuilder(habit.period.times.toString())
        binding.dayInterval.text = SpannableStringBuilder(habit.period.dayInterval.toString())
    }

    private fun createTextWatcher(afterTextChanged: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                afterTextChanged(s.toString())
                habitFormListener.onFormChange(habit)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
    }

    private fun createListeners() {
        binding.habitTitle.addTextChangedListener(
            createTextWatcher {text -> habit.title = text})
        binding.habitDescription.addTextChangedListener(
            createTextWatcher {text -> habit.description = text})

        binding.prioritySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                habit.priority = HabitPriority.values().firstOrNull() {it -> it.index == position}!!
                habitFormListener.onFormChange(habit)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.typeGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                habit.type = habitTypeButtons.entries.first() {it -> it.value == checkedId}.key
                habitFormListener.onFormChange(habit)
            }
        })

        binding.times.addTextChangedListener(
            createTextWatcher { text -> if (text.isNotEmpty()) habit.period.times = text.toInt() })
        binding.dayInterval.addTextChangedListener(
            createTextWatcher { text -> if (text.isNotEmpty()) habit.period.dayInterval = text.toInt() })
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

        habitTypeButtons = mapOf(
            HabitType.TYPE_1 to binding.type1.id,
            HabitType.TYPE_2 to binding.type2.id,
            HabitType.TYPE_3 to binding.type3.id
        )

        val habitBundle = arguments?.getBundle(RESULT_HABIT_KEY)

        habit = if (habitBundle != null) {
            Habit(habitBundle)
        } else {
            Habit(
                binding.habitTitle.text.toString(),
                binding.habitDescription.text.toString(),
                R.color.habit_color_1,
                HabitPriority.LOW,
                HabitType.TYPE_1,
                HabitPeriod(
                    1,
                    1
                )
            )
        }

        createHabitFormView(habit)
        createListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}