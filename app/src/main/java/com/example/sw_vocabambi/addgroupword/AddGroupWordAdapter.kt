package com.example.wordbook.addgroupword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.ViewholderAddGroupwordBinding

//어답터에서는 클릭을 눌렀을 때 해당 word로 가는 처리가 필요함.
class AddGroupWordAdapter(val onMoveToWordInfo:(word:Word)->Unit) : ListAdapter<Word, AddGroupWordAdapter.AddGroupWordViewHolder>(AddGroupWordDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddGroupWordViewHolder {
        return AddGroupWordViewHolder.from(parent)
    }
//
    override fun onBindViewHolder(holder: AddGroupWordViewHolder, position: Int) {

        holder.binding.word = getItem(position)
        holder.binding.onClickAddWord = AddGroupWordClickListener(onMoveToWordInfo)
    }


    class AddGroupWordDiffCallback: DiffUtil.ItemCallback<Word>(){
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.equals(newItem)
        }
    }

    class AddGroupWordViewHolder private constructor(val binding: ViewholderAddGroupwordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): AddGroupWordViewHolder {
                return AddGroupWordViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.viewholder_add_groupword,
                        parent,
                        false
                    )
                )
            }
        }
    }
    class AddGroupWordClickListener(val onMoveToWordInfo:(word: Word) -> Unit){
        fun onAddGroupWordClick(word: Word) = onMoveToWordInfo(word)
    }
}
