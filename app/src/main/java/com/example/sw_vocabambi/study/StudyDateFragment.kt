package com.example.wordbook.study

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
import com.example.wordbook.databinding.FragmentStudyDateBinding
import com.example.wordbook.BaseActivity


class StudyDateFragment : Fragment() {

    companion object {
        fun newInstance() = StudyDateFragment()
    }

    private lateinit var viewModel: StudyDateViewModel
    private lateinit var binding: FragmentStudyDateBinding
    private lateinit var backPressCallback: OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_study_date, container, false)
        viewModel = ViewModelProvider(this).get(StudyDateViewModel::class.java)

        binding.viewModel = viewModel

        //return inflater.inflate(R.layout.fragment_test_date, container, false)


        binding.buttonDay1.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay2.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay3.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay4.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay5.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay6.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay7.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay8.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay9.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay10.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay11.setOnClickListener{
            addStudyFragment()
        }
        binding.buttonDay12.setOnClickListener{
            addStudyFragment()
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

    private fun addStudyFragment(){
        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, StudyFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


}