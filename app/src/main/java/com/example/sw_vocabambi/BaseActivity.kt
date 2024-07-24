package com.example.sw_vocabambi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wordbook.main.MainFragment

class BaseActivity : AppCompatActivity() {
    companion object {
        const val FRAGMENT_CONTAINER_ID = R.id.fragment_container_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        // 로그인 액티비티에서 전달된 사용자 아이디 가져오기
        val userID = intent.getStringExtra("userID")

        // MainFragment로 아이디 전달
        val mainFragment = MainFragment.newInstance(userID)
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(FRAGMENT_CONTAINER_ID, mainFragment)
            .commit()

//        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .setReorderingAllowed(true)
//                .replace(FRAGMENT_CONTAINER_ID, MainFragment.newInstance())
//                .commit()
//        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(FRAGMENT_CONTAINER_ID)

        if (currentFragment is MainFragment) {
            Toast.makeText(applicationContext, R.string.good_bye, Toast.LENGTH_SHORT).show()
        }
        super.onBackPressed()
    }
}