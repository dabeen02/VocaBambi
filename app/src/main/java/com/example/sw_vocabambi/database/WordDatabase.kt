package com.example.wordbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.opencsv.CSVReaderBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.nio.charset.Charset

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase: RoomDatabase() {
    abstract val wordDao: WordDao
}

private const val DATABASE_NAME = "words.db"
private lateinit var INSTANCE: WordDatabase

fun fillInDb(context: Context){
//    CoroutineScope(Dispatchers.IO).launch {
//        getDatabase(context).wordDao.insertAll(
//            toeicWords
//        )
//    }
    fillInDbFromCsv(context)
}

fun getDatabase(context: Context): WordDatabase {
    synchronized(WordDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WordDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        fillInDb(context.applicationContext)
                    }
                })
                .build()
        }
    }
    return INSTANCE
}

//private val toeicWords = arrayListOf(
//    Word(1, "banana", "바나나", "B"),
//    Word(2, "candy", "사탕", "c")
//)

fun fillInDbFromCsv(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val reader = CSVReaderBuilder(
            InputStreamReader(context.assets.open("ToeicWords_deleteDay.csv"), Charset.forName("CP949"))
        ).withSkipLines(1).build()
        val iterator = reader.iterator()
        val words = ArrayList<Word>()
        var id = 1
        while (iterator.hasNext()) {
            val nextLine = iterator.next()
            val word = Word(
                id,
                nextLine[0],
                nextLine[1],
                nextLine[2]
            )
            words.add(word)
            id++
        }
        getDatabase(context).wordDao.insertAll(words)
    }
}
