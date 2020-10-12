package com.poly.minjin.poly_project.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.activity.*
import kotlinx.android.synthetic.main.menu.*


open class BaseActivity : AppCompatActivity() {

    private var backKeyPressedTime: Long = 0
    lateinit var pref: SharedPreferences

    lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        pref = getSharedPreferences("ref", Context.MODE_PRIVATE)


    }

    override fun onBackPressed() {
        super.onBackPressed()

    }


    fun movePage() {

        // 홈 화면
        home.setOnClickListener {
            val i = Intent(applicationContext, HomeActivity::class.java)
            startActivity(i)
            // 엑티비티 전환을 자연스럽게 해주는 애니메이션
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            home.setBackgroundColor(Color.YELLOW)


        }

        // 검색 화면
        search.setOnClickListener {
            val i = Intent(applicationContext, SearchActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            search.setBackgroundColor(Color.YELLOW)

        }


        // 평가 화면
        star.setOnClickListener {
            val i = Intent(applicationContext, EvaluationActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            star.setBackgroundColor(Color.YELLOW)

        }

        // 소셜(sns) 화면
        heart.setOnClickListener {
            val i = Intent(applicationContext, SocialActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            heart.setBackgroundColor(Color.YELLOW)

        }

        // 설정(myPage) 화면
        person.setOnClickListener {
            val i = Intent(applicationContext, SettingActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            person.setBackgroundColor(Color.YELLOW)

        }


    }


}