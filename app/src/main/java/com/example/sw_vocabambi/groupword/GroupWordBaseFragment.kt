package com.example.wordbook.groupword

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.wordbook.R
import com.example.wordbook.grouplist.GroupListBaseFragment
import com.example.wordbook.grouplist.GroupListFragment

private const val ARG_GROUP_ID = "group_id"

class GroupWordBaseFragment : Fragment() {
    companion object{
        const val GROUP_WORD_FRAGMENT_CONTAINER_ID = R.id.group_word_container_view
        //이렇게 해라... 여기서는 new 인스턴스 내부에 인자 없는 프래그먼트 생성한 후
        //아규먼트 전달함.
        //여기서는 인자없는 프래그먼트를 전달하기 때문에 위반은 아니다.
        fun newInstance(groupid: Int): GroupWordBaseFragment {
            val fragment = GroupWordBaseFragment()
            val args = Bundle()
            args.putInt(ARG_GROUP_ID, groupid)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_word_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view 생성함. 만약 인스턴스가 안전하게 생성되었으면
        //번들에서 값 꺼내고
        //GroupWordFragment 생성함.
        if(savedInstanceState==null){
            //아규먼트 꺼내서 View- 그룹 워드 프래그먼트 생성함!
            val groupId = arguments?.let {
                it.getInt(ARG_GROUP_ID)
            } ?: -1
            openGroupWordFragment(groupId)
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
    private fun openGroupWordFragment(groupid:Int){
        childFragmentManager.beginTransaction()
            //여기서 group id 인자 전달해야하는지 아닌지 확인 필요함... GroupListFragment 보니 생성이 필요할 듯.
            .replace(GROUP_WORD_FRAGMENT_CONTAINER_ID, GroupWordFragment.newInstance(groupid))
            .setReorderingAllowed(true)
            .commit()
    }

    private fun getBackPressCallback(): OnBackPressedCallback{
        if(!::backPressCallback.isInitialized) {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment = childFragmentManager.findFragmentById(
                        GROUP_WORD_FRAGMENT_CONTAINER_ID
                    )
                    if (currentFragment is GroupWordFragment) {
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