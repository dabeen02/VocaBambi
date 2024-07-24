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
import com.example.wordbook.databinding.FragmentTestDateBinding
import com.example.wordbook.BaseActivity


class TestDateFragment : Fragment() {

    companion object {
        fun newInstance() = TestDateFragment()
    }

    private lateinit var viewModel: TestDateViewModel
    private lateinit var binding: FragmentTestDateBinding
    private lateinit var backPressCallback: OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test_date, container, false)
        viewModel = ViewModelProvider(this).get(TestDateViewModel::class.java)

        binding.viewModel = viewModel

        //return inflater.inflate(R.layout.fragment_test_date, container, false)


        binding.buttonDay3.setOnClickListener{
            addTestFragment()
        }
        binding.buttonDay12.setOnClickListener{
            addTestFragment()
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

    private fun addTestFragment(){
        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, TestFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }



}