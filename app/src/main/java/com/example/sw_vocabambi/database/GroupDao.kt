package com.example.wordbook.database

import androidx.lifecycle.LiveData
import androidx.room.*

//group or data base 두개 사용할 때 Transaction 사용
@Dao
interface GroupDao {
    @Insert
    suspend fun insertgroup(group: Group): Long

    @Delete
    suspend fun deletegroup(group: Group)
    @Query("select * from groups")
    fun selectAllGroup(): List<Group>
    @Query("select * from groups")
    fun getAllGroup(): LiveData<List<Group>>

    @Query("select * from groups WHERE groupid = :id")
    suspend fun findById(id: Int): Group

    @Query("select count(*) from groups")
    suspend fun countGroupNum(): Int

    /*
     * 필요한 함수 정의
     * 1. GroupWord에 word 넣는 insert 함수
     * 2. word 삭제
     * 3. GroupId 이용해서 GroupId가진 wordList 생성
     * 4. GroupId 이용해서 GroupId가진 wordLiveData
     * 5. groupword id 이용해 groupword객체 반환하는 함수
     *
     * 6. word 개수 세는 count 함수 - 필요할 시 구현하기.
     */

//    @Insert
//    suspend fun insertGroupWord(word:GroupWord):Long
//
//    @Delete
//    suspend fun deleteGroupWord(word:GroupWord)
//
//    @Query("select * from groupwords WHERE groupid_base_groupword = :groupid")
//    suspend fun selectAllGroupWordList(groupid: Int): List<GroupWord>
//
//    @Query("select * from groupwords WHERE groupid_base_groupword = :groupid")
//    fun getAllGroupWordList(groupid: Int): LiveData<List<GroupWord>>
//
//    @Query("select * from groupwords WHERE word_id = :word_id")
//    fun findGroupWordbyId(word_id: Int): GroupWord
}

