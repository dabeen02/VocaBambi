package com.example.wordbook.groupword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordbook.R
import com.example.wordbook.database.Group
import com.example.wordbook.database.GroupWord
import com.example.wordbook.databinding.ViewholderGroupwordBinding

//삭제 기능 구현. 어답터에서 할일은 그것 밖에 없음.
class GroupWordAdapter(val onDeleteGroupWord:(groupword: GroupWord)->Unit) : ListAdapter<GroupWord, GroupWordAdapter.GroupWordViewHolder>(GroupWordDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupWordViewHolder {
        return GroupWordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GroupWordViewHolder, position: Int) {
        holder.binding.groupword = getItem(position)
        holder.binding.onClickDeleteGroupWord = DeleteWordClickListener(onDeleteGroupWord)
    }


    class GroupWordDiffCallback: DiffUtil.ItemCallback<GroupWord>(){
        override fun areItemsTheSame(oldItem: GroupWord, newItem: GroupWord): Boolean {
            return oldItem.word_id == newItem.word_id
        }

        override fun areContentsTheSame(oldItem: GroupWord, newItem: GroupWord): Boolean {
            return oldItem.equals(newItem)
        }
    }

    class GroupWordViewHolder private constructor(val binding: ViewholderGroupwordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): GroupWordViewHolder {
                return GroupWordViewHolder(
                    DataBindingUtil.inflate(
                        //viewholder_group이 아니잖냐!!!!!!
                        LayoutInflater.from(parent.context),
                        R.layout.viewholder_groupword,
                        parent,
                        false
                    )
                )
            }
        }

    }
    class DeleteWordClickListener(val onDeleteGroupWord: (groupword: GroupWord) -> Unit){
        fun onDeleteGroupWordClick(groupword: GroupWord) = onDeleteGroupWord(groupword)
    }
}
