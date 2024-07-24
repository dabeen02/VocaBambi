package com.example.wordbook.addwordinfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentAddWordInfoBinding
import kotlinx.coroutines.launch

private const val ARG_WORD_ID = "word_id3"
private const val ARG_GROUP_ID = "group_id3"

class AddWordInfoFragment : Fragment() {

    companion object {
        fun newInstance(wordId: Int, groupId: Int): AddWordInfoFragment {
            val fragment = AddWordInfoFragment()
            val args = Bundle()
            args.putInt(ARG_WORD_ID, wordId)
            args.putInt(ARG_GROUP_ID, groupId)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentAddWordInfoBinding
    private lateinit var viewModel: AddWordInfoViewModel
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_word_info, container, false)

        val mWordId = arguments?.let {
            it.getInt(ARG_WORD_ID)
        } ?: -1

        val mGroupId = arguments?.let {
            it.getInt(ARG_GROUP_ID)
        } ?: -1

        Log.d("addfrag", "groupId : $mGroupId")

        val viewModelFactory = AddWordInfoViewModelFactory(requireActivity().application, mWordId, mGroupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddWordInfoViewModel::class.java)

        viewModel.state.observe(viewLifecycleOwner) {
            //word 있으면 해당 값을 이걸로 바꾸어라 임... 근데 이건 word LIst에서는 어답터에서 처리해야함 그래서 걍
            //어답터에서 생성, 확인하면 될 것 같다. 만약 오류 나면 여기가 문제 인것
            when (it) {
                is AddWordInfoViewModelState.Ready -> binding.wordInfo = it.word
            }
        }

        binding.backInfoBtn.setOnClickListener{
            destroy()
        }
        binding.wordInfoAddBtn.setOnClickListener{
            lifecycleScope.launch {
                if (viewModel.checkWordInGroupWord(mWordId)){
                    Toast.makeText(context, "단어장에 이미 있는 단어입니다. 다른 단어를 선택하세요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    viewModel.insertGroupWord()
                    destroy()
                }
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