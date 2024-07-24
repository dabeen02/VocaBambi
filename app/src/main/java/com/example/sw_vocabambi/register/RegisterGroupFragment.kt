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
import com.example.wordbook.R
import com.example.wordbook.database.Group
import com.example.wordbook.databinding.FragmentRegisterGroupBinding

class RegisterGroupFragment : Fragment() {

    companion object{
        fun newInstance() = RegisterGroupFragment()
    }

    private lateinit var binding: FragmentRegisterGroupBinding
    private lateinit var viewModel: RegisterGroupViewModel
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_group, container, false)
        viewModel = ViewModelProvider(this).get(RegisterGroupViewModel::class.java)
        //여기서 등록 만듦.Title 입력 받아 title변수에 저장하고, 이를 이용해 Group 객체 생성(make함수)
        //register가 DB에 저장하는 함수임 얘는 ViewModel에 정의되어 있음.
        binding.registerGroupBtn.setOnClickListener{
            val title = binding.inputGroupTitle.text.toString()
            if(title.isEmpty()){
                Toast.makeText(context, "단어장 이름에 글자를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.registerGroup(Group.make(title))
                destroy()
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context){
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this,getBackPressCallback())
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