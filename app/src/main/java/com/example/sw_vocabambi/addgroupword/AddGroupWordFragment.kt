package com.example.wordbook.addgroupword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.R
import com.example.wordbook.addwordinfo.AddWordInfoFragment
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.FragmentAddGroupWordBinding
import com.example.wordbook.groupword.GroupWordBaseFragment


private const val ARG_GROUP_ID = "group2_id"

class AddGroupWordFragment : Fragment() {
    var sendgroupid = -1
    companion object{
        fun newInstance(groupId: Int): AddGroupWordFragment {
            val fragment = AddGroupWordFragment()
            val args = Bundle()
            args.putInt(ARG_GROUP_ID, groupId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentAddGroupWordBinding
    private lateinit var viewModel: AddGroupWordViewModel
    private lateinit var adapter: AddGroupWordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mGroupId = arguments?.let {
            it.getInt(ARG_GROUP_ID)
        } ?: -1
        sendgroupid = mGroupId
        Log.d("addfrag2", "groupId : $mGroupId")

        val viewModelFactory = AddGroupWordViewModelFactory(requireActivity().application, mGroupId)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_group_word, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddGroupWordViewModel::class.java)
        binding.viewModel = viewModel

        binding.wordlist.layoutManager = LinearLayoutManager(requireContext())
        //어답터 수정 필요
        adapter = AddGroupWordAdapter(::MoveToWordInfo)
        binding.wordlist.adapter = adapter

        binding.searchGroupWord.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText)
                return true
            }
        })
        viewModel.wordList.observe(viewLifecycleOwner){
            //어답터 구현하고 오류 뜨는지 확인 필요
            adapter.submitList(it)
        }

        return binding.root
    }

    private fun performSearch(query: String) {
        viewModel.searchWords(query) // Room 데이터베이스 쿼리 실행
    }

    //여기 수정해야함 복붙하느라 이상한 값 컨테이너가 들어갔네
    private fun MoveToWordInfo(word: Word){
        parentFragmentManager.beginTransaction()
            .replace(
                AddGroupWordBaseFragment.ADD_GROUP_WORD_FRAGMENT_CONTAINER_ID,
                AddWordInfoFragment.newInstance(word.id, sendgroupid)
            )
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}