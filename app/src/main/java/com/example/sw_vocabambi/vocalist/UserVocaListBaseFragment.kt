package com.example.wordbook.vocalist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.wordbook.R

class UserVocaListBaseFragment : Fragment() {
    companion object {
        const val VOCA_LIST_FRAGMENT_CONTAINER_ID = R.id.voca_list_container_view
        fun newInstance() = UserVocaListBaseFragment()
    }

    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voca_list_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            openUserVocaListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this, getBackPressCallback())
    }

    override fun onDetach() {
        super.onDetach()

        getBackPressCallback().remove()
    }

    private fun openUserVocaListFragment() {
        childFragmentManager.beginTransaction()
            .replace(VOCA_LIST_FRAGMENT_CONTAINER_ID, UserVocaListFragment.newInstance())
            .setReorderingAllowed(true)
            .commit()
    }

    private fun getBackPressCallback(): OnBackPressedCallback {
        if (!::backPressCallback.isInitialized) {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment = childFragmentManager.findFragmentById(VOCA_LIST_FRAGMENT_CONTAINER_ID)

                    if (currentFragment is UserVocaListFragment) {
                        destroy()
                    }
                }
            }
        }
        return backPressCallback
    }

    private fun destroy() {
        parentFragmentManager.popBackStack()
    }
}