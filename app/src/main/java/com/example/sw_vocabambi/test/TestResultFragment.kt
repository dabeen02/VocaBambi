package com.example.wordbook.test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentTestResultBinding


class TestResultFragment : Fragment() {

    companion object {
        private const val ARG_CORRECT_COUNT = "arg_correct_count"

        fun newInstance(correctCount: Int): TestResultFragment {
            val fragment = TestResultFragment()
            val args = Bundle().apply {
                putInt(ARG_CORRECT_COUNT, correctCount)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: TestResultViewModel
    private lateinit var binding: FragmentTestResultBinding
    private lateinit var backPressCallback: OnBackPressedCallback



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test_result, container, false)
        viewModel = ViewModelProvider(this).get(TestResultViewModel::class.java)

        binding.viewModel = viewModel

        val testCorrectCount = arguments?.let {
            it.getInt(ARG_CORRECT_COUNT)
        } ?: -1

        binding.correctCountTextview.text = testCorrectCount.toString() //correctCountTextView 텍스트 값을 testCorrectCount 값으로 변경


        binding.moveToMain.setOnClickListener {
            parentFragmentManager.popBackStack("MainFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE) //바로 메인페이지로 돌아오게

        }


        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this, getBackPressCallback())
    }

    override fun onDetach() {
        super.onDetach()

        getBackPressCallback().remove()
    }

    private fun getBackPressCallback(): OnBackPressedCallback {
        if (!::backPressCallback.isInitialized) {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    destroy()
                }
            }
        }
        return backPressCallback
    }

    private fun destroy() {
        parentFragmentManager.popBackStack()
    }

}