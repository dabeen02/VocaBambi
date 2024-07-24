package com.example.wordbook.edit

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
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.FragmentEditVocaBinding
import kotlinx.coroutines.launch

private const val ARG_VOCA_ID = "voca_id"

class EditVocaFragment : Fragment() {

    companion object {
        fun newInstance(vocaId: Int): EditVocaFragment {
            val fragment = EditVocaFragment()
            val args = Bundle()
            args.putInt(ARG_VOCA_ID, vocaId)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentEditVocaBinding
    private lateinit var viewModel: EditVocaViewModel
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_voca, container, false)

        val mWordId = arguments?.let {
            it.getInt(ARG_VOCA_ID)
        } ?: -1

        Log.d("Yebon", "wordId : $mWordId")

        val viewModelFactory = EditVocaViewModelFactory(requireActivity().application, mWordId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditVocaViewModel::class.java)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is EditVocaViewModelState.Ready -> binding.word = it.word
//                is EditVocaViewModelState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }

        binding.edit.setOnClickListener {
            val english = binding.englishInput.text.toString()
            val means = binding.meansInput.text.toString()

            lifecycleScope.launch {
                if (english.isNotEmpty() && means.isNotEmpty()) {
                    val existingWord = viewModel.getWordByEnglish(english)
                    if (existingWord == null || existingWord.id == mWordId) {
                        viewModel.updateWord(english, means)
                        destroy()
                        Toast.makeText(context, "단어가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "이미 존재하는 단어입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else if (english.isEmpty()) {
                    // 영어 단어가 입력되지 않은 경우, 즉시 알림을 띄웁니다.
                    Toast.makeText(context, "영어에 글자를 입력해 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    // 뜻에 글자가 입력되지 않은 경우, 즉시 알림을 띄웁니다.
                    Toast.makeText(context, "뜻에 글자를 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //delete
        binding.delete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteWord()
                destroy()
                Toast.makeText(context, "단어가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.editCancel.setOnClickListener {
            destroy()
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