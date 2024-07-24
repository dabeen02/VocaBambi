package com.example.wordbook.grouplist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.wordbook.R

class GroupListBaseFragment : Fragment() {

    //일단 베이스 프래그먼트에서 베이스 프래그먼트 생성
    companion object{
        const val GROUP_LIST_FRAGMENT_CONTAINER_ID = R.id.group_list_container_view
        fun newInstance() = GroupListBaseFragment()
    }

    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_list_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view 생성함. 만약 인스턴스가 안전하게 생성되었으면
        //GroupListFragment 생성함.
        if(savedInstanceState==null){
            openGroupListFragment()
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

    //여기서 그룹 프래먼트 생성하는 것 같음.
    private fun openGroupListFragment(){
        childFragmentManager.beginTransaction()
            .replace(GROUP_LIST_FRAGMENT_CONTAINER_ID, GroupListFragment.newInstance())
            .setReorderingAllowed(true)
            .commit()
    }

    private fun getBackPressCallback(): OnBackPressedCallback{
        if(!::backPressCallback.isInitialized) {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment = childFragmentManager.findFragmentById(
                        GROUP_LIST_FRAGMENT_CONTAINER_ID
                    )
                    if (currentFragment is GroupListFragment) {
                        destroy()
                    }
                }
            }
        }
        return backPressCallback
    }

    private fun destroy(){
        parentFragmentManager.popBackStack()
    }
}