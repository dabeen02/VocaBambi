package com.example.wordbook.grouplist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wordbook.R
import com.example.wordbook.database.Group
import com.example.wordbook.databinding.ViewholderGroupBinding

class GroupListAdapter (val onMoveToGroup: (group: Group) -> Unit, val onDeleteGroup: (group: Group)->Unit): ListAdapter<Group, GroupListAdapter.GroupListViewHolder>(GroupDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        return GroupListViewHolder.from(parent)
    }

    //여기서 객체 클릭 제어
    //얘가 data를 연결하네.... 아ㅏ....
    //여기서 gorupid 전달하기.
    //그리고 전달받은 프래그먼트에서 newInstance에 인자로 받아 get으로 얻기.
    //get으로 id얻어서 객체를 LiveData 변환으로 뷰모델에서 얻어보면 됨.
    //얻은 후 방식 그대로 사용하기...
    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        holder.binding.grouptitle = getItem(position)
        holder.binding.onClickGroup = GroupClickListener(onMoveToGroup)
        //그룹 삭제 구현
        holder.binding.onClickDeleteGroup = DeleteClickListener(onDeleteGroup)
    }


    class GroupDiffCallback:DiffUtil.ItemCallback<Group>(){
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.groupid == newItem.groupid
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.equals(newItem)
        }
    }

    class GroupListViewHolder private constructor(val binding: ViewholderGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): GroupListViewHolder {
                return GroupListViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.viewholder_group,
                        parent,
                        false
                    )
                )
            }
        }
    }
    //여기서 객체 클릭, 삭제 관여
    //https://parkho79.tistory.com/155 확인해보기
    class GroupClickListener(val onMoveToGroup: (group: Group) -> Unit){
        //클릭해서 Group으로 이동하기 전에 word 설정하고 가기
        fun onGroupClick(group: Group) = onMoveToGroup(group)
    }

    class DeleteClickListener(val onDeleteGroup:(group:Group)->Unit){
        fun onDeleteClick(group:Group) = onDeleteGroup(group)
    }
}
