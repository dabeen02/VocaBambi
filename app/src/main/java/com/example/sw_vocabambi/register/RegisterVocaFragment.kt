package com.example.wordbook.register

import android.content.Context
import android.os.Bundle
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
import com.example.wordbook.databinding.FragmentRegisterVocaBinding
import kotlinx.coroutines.launch

class RegisterVocaFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterVocaFragment()
    }

    private lateinit var binding: FragmentRegisterVocaBinding
    private lateinit var viewModel: RegisterVocaViewModel
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register_voca, container, false)
        viewModel = ViewModelProvider(this).get(RegisterVocaViewModel::class.java)

        binding.add.setOnClickListener {
            val english = binding.englishInput.text.toString()
            val means = binding.meansInput.text.toString()
            // 영어 단어와 뜻이 모두 입력되었는지 확인.
            lifecycleScope.launch {
                if (english.isNotEmpty() && means.isNotEmpty()) {
                    val existingWord = viewModel.getWordByEnglish(english)
                    if (existingWord == null) {
                        viewModel.registerWord(Word.make(english, means))
                        destroy()
                        Toast.makeText(context, "단어가 추가되었습니다.", Toast.LENGTH_SHORT).show()
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
        binding.addCancel.setOnClickListener {
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