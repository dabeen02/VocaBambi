package com.example.wordbook.test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentTestBinding
import com.example.wordbook.BaseActivity

class TestFragment : Fragment() {

    companion object {
        fun newInstance() = TestFragment()
    }

    private lateinit var viewModel: TestViewModel
    private lateinit var binding: FragmentTestBinding
    private lateinit var backPressCallback: OnBackPressedCallback

    private var isTestResultFragmentShown = false // TestFragment에서 TestResultFragment 이동 한번만 하게.



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test, container, false)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel


        var correctCount = 0

        viewModel.mTestcorrectCount.observe(viewLifecycleOwner){ mTestcorrectCount ->
            correctCount = mTestcorrectCount
        }

        viewModel.mTestCount.observe(viewLifecycleOwner){ mTestCount ->
            if (mTestCount <= 0 && !isTestResultFragmentShown){
                isTestResultFragmentShown = true //TestResultFragment에서 여기로 백할 때 이것이 다시 실행되지 않도록 막음
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(BaseActivity.FRAGMENT_CONTAINER_ID, TestResultFragment.newInstance(correctCount)) //correctCount값 전달
                    .addToBackStack(null)
                    .commit()
            }
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