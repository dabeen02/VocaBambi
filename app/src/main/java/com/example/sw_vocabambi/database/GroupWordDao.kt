package com.example.wordbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GroupWordDao{
    @Insert
    suspend fun insertGroupWord(word:GroupWord):Long

    @Delete
    suspend fun deleteGroupWord(word:GroupWord)

    @Query("select * from groupwords WHERE groupid_base_groupword = :groupid")
    suspend fun selectAllGroupWordList(groupid: Int): List<GroupWord>

    @Query("select * from groupwords WHERE groupid_base_groupword = :groupid")
    fun getAllGroupWordList(groupid: Int): LiveData<List<GroupWord>>

    @Query("select * from groupwords WHERE word_id = :word_id")
    fun findGroupWordbyId(word_id: Int): GroupWord
    //group id 전달 필요
    @Query("SELECT * FROM groupwords WHERE (groupWordEnglish LIKE '%' || :searchKeyword || '%' OR groupWordMeans LIKE '%' || :searchKeyword || '%') AND groupid_base_groupword = :groupid  ")
    suspend fun searchWords(searchKeyword: String, groupid: Int): List<GroupWord>

    @Query("SELECT groupWordEnglish FROM groupwords WHERE groupid_base_groupword = :groupid")
    suspend fun getGroupWordEnglishList(groupid: Int):List<String>
}