package com.example.wordbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    suspend fun insert(word: Word): Long

    @Insert
    suspend fun insertAll(words: List<Word>)

    @Update
    suspend fun update(word: Word): Int

    @Query("select * from words")
    fun selectAllWithLiveData(): LiveData<List<Word>>

    @Query("select * from words")
    suspend fun selectAll(): List<Word>

    @Query("select * from words WHERE id = :id")
    suspend fun findById(id: Int): Word

    @Query("SELECT * FROM words WHERE english LIKE '%' || :searchKeyword || '%' OR means LIKE '%' || :searchKeyword || '%'")
    suspend fun searchWords(searchKeyword: String): List<Word>

    @Query("SELECT * FROM words WHERE english = :english")
    suspend fun getWordByEnglish(english: String): Word?

    @Query("select count(*) from words")
    suspend fun getCount(): Int

    @Delete
    suspend fun delete(word: Word): Int

}