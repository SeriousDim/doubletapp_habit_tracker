package ru.seriousdim.habittracker.habit_list_fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ru.seriousdim.habittracker.activities.habit.HabitActivity
import ru.seriousdim.habittracker.databinding.FragmentHabitListBinding
import ru.seriousdim.habittracker.dataclasses.Habit

private const val RESULT_HABIT_KEY = "RESULT_HABIT_KEY"
private const val HABIT_INDEX = "HABIT_INDEX"

class HabitListFragment() : Fragment(), IHabitItemListener {
    companion object {
        fun newInstance(): HabitListFragment {
            return HabitListFragment()
        }
    }

    val adapter = HabitListAdapter(this)

    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data

                val habitBundle = intent?.extras?.getBundle(RESULT_HABIT_KEY)
                val index = intent?.extras?.getInt(HABIT_INDEX)!!

                if (habitBundle != null) {
                    if (index < adapter.size) {
                        adapter.removeAt(index)
                    }
                    adapter.add(index, Habit(habitBundle))
                    binding.habitList.adapter?.notifyItemChanged(index)
                }
            }
    }

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

        binding.habitList.adapter = adapter
    }

    override fun onHabitItemClick(position: Int) {
        val intent = Intent(context, HabitActivity::class.java)
            .putExtra(RESULT_HABIT_KEY, adapter[position].toBundle())
            .putExtra(HABIT_INDEX, position)

        launcher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}