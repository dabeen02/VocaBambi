package com.example.wordbook.vocalist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.FragmentVocaListBinding
import com.example.wordbook.edit.EditVocaFragment
import com.example.wordbook.register.RegisterVocaFragment
import com.google.android.material.appbar.AppBarLayout

class UserVocaListFragment : Fragment() {

    companion object {
        fun newInstance() = UserVocaListFragment()
    }

    private lateinit var viewModel: VocaListViewModel
    private lateinit var binding: FragmentVocaListBinding
    private lateinit var adapter: VocaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_voca_list, container, false)
        viewModel = ViewModelProvider(this).get(VocaListViewModel::class.java)

        binding.viewModel = viewModel

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        adapter = VocaListAdapter(::moveToEditVoca)
        binding.list.adapter = adapter

        // SearchView 초기화
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // 검색어가 변경될 때마다 호출됨.
                performSearch(newText)
                return true
            }
        })

        viewModel.vocas.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        //action bar 숨기기
        //(requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.add.visibility = View.GONE

        return binding.root
    }

    private fun performSearch(query: String) {
        viewModel.searchWords(query) // Room 데이터베이스 쿼리 실행
    }

    private fun moveToEditVoca(word: Word) {
        Toast.makeText(requireContext(), "단어 수정 기능은 관리자만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
    }


}