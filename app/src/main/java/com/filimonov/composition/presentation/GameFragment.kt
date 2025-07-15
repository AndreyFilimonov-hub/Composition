package com.filimonov.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.filimonov.composition.databinding.FragmentGameBinding
import com.filimonov.composition.domain.entity.GameResult
import com.filimonov.composition.domain.entity.Level

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(requireActivity().application, args.level)
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
    }

    private fun setClickListenersToOptions() {
        tvOptions.forEach { textView ->
            textView.setOnClickListener {
                viewModel.chooseAnswer(textView.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            tvOptions.forEachIndexed { index, textView ->
                textView.text = it.options[index].toString()
            }
        }
        viewModel.formattedTimer.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val colorResId = if (it) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(requireContext(), colorResId)
            binding.tvAnswersProgress.setTextColor(color)
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val colorResId = if (it) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(requireContext(), colorResId)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.minPercentValue.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}