package com.example.sw_vocabambi.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordbook.R;


public class PwanswerActivity extends AppCompatActivity {

    private EditText et_hint;
    private Button btn_gotologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwanswer);

        et_hint = findViewById(R.id.et_hintanswer);

        btn_gotologin= findViewById(R.id.btn_gotopw);


        //추가
        //로그인 찾기 버튼을 클릭 시 수행
        btn_gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PwanswerActivity.this, PwCheckActivity.class);
                startActivity(intent);
            }
        });

    }
}