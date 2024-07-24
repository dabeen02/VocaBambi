package com.example.wordbook.addgroupword

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.wordbook.R
import com.example.wordbook.grouplist.GroupListBaseFragment
import com.example.wordbook.grouplist.GroupListFragment
import com.example.wordbook.groupword.GroupWordBaseFragment
import com.example.wordbook.groupword.GroupWordFragment

private const val ARG_GROUP_ID = "group_id"

class AddGroupWordBaseFragment : Fragment() {

    companion object{
        const val ADD_GROUP_WORD_FRAGMENT_CONTAINER_ID = R.id.add_group_word_container_view

        fun newInstance(groupid: Int): AddGroupWordBaseFragment {
            val fragment = AddGroupWordBaseFragment()
            val args = Bundle()
            args.putInt(ARG_GROUP_ID, groupid)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var backPressCallback: OnBackPressedCallback

    //화면 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group_word_base, container, false)
    }

    //AddGroupFragment 생성
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState==null){
            val groupId = arguments?.let {
                it.getInt(ARG_GROUP_ID)
            } ?: -1
            openAddGroupWordFragment(groupId)
        }
    }

    private fun openAddGroupWordFragment(groupid:Int){
        childFragmentManager.beginTransaction()
            .replace(ADD_GROUP_WORD_FRAGMENT_CONTAINER_ID, AddGroupWordFragment.newInstance(groupid))
            .setReorderingAllowed(true)
            .commit()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this, getBackPressCallback())
    }

    override fun onDetach() {
        super.onDetach()

        getBackPressCallback().remove()
    }

    private fun getBackPressCallback(): OnBackPressedCallback{
        if(!::backPressCallback.isInitialized) {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment = childFragmentManager.findFragmentById(
                        ADD_GROUP_WORD_FRAGMENT_CONTAINER_ID)
                    if (currentFragment is AddGroupWordFragment) {
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