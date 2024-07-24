package com.example.wordbook.groupword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.R
import com.example.wordbook.addgroupword.AddGroupWordBaseFragment
import com.example.wordbook.database.GroupWord
import com.example.wordbook.databinding.FragmentGroupWordBinding
import kotlinx.coroutines.launch

private const val ARG_GROUP_ID = "group_id"

class GroupWordFragment : Fragment() {
    companion object{
        fun newInstance(groupId: Int): GroupWordFragment{
            val fragment = GroupWordFragment()
            val args = Bundle()
            args.putInt(ARG_GROUP_ID, groupId)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentGroupWordBinding
    private lateinit var viewModel: GroupWordViewModel
    private lateinit var adapter: GroupWordAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mGroupId = arguments?.let {
            it.getInt(ARG_GROUP_ID)
        } ?: -1
        Log.d("addfrag1", "groupId : $mGroupId")

        val viewModelFactory = GroupWordViewModelFactory(requireActivity().application, mGroupId)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_group_word, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GroupWordViewModel::class.java)
        binding.viewModel = viewModel

        //어답터에 상세 페이지 이동이 아닌  삭제 기능 추가하기.
        binding.groupwordlist.layoutManager = LinearLayoutManager(requireContext())
        adapter = GroupWordAdapter(::DeleteToGroupWord)
        binding.groupwordlist.adapter = adapter

        //클릭 감지 -> button
        binding.addGroupWordBtn.setOnClickListener{
            moveToAddGroupWord(mGroupId)
        }

        viewModel.groupWordList.observe(viewLifecycleOwner){
            //어답터 구현하고 오류 뜨는지 확인 필요
            adapter.submitList(it)
        }

        // SearchView 초기화
        binding.searchGroupWord.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // 검색어가 변경될 때마다 호출됨.
                performSearch(newText)
                return true
            }
        })

        return binding.root
    }

    //추가 버튼 누를 시 단어장에 단어 추가 프래그먼트로 이동
    private fun moveToAddGroupWord(groupId: Int){
        parentFragmentManager.beginTransaction()
            .replace(
                GroupWordBaseFragment.GROUP_WORD_FRAGMENT_CONTAINER_ID,
                AddGroupWordBaseFragment.newInstance(groupId)
            )
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    // 단어 삭제 누를 시 삭제
    private fun DeleteToGroupWord(groupword: GroupWord) {
        lifecycleScope.launch {
            viewModel.deleteGroupWord(groupword)
            viewModel.getWordList()
        }
    }

    private fun performSearch(query: String) {
        viewModel.searchWords(query) // Room 데이터베이스 쿼리 실행
    }

}