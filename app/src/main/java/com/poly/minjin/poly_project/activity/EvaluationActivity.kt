package com.poly.minjin.poly_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.base.BaseActivity
import kotlinx.android.synthetic.main.menu.*

class EvaluationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        movePage()
        home.isActivated = false
        search.isActivated = false
        star.isActivated = true
        heart.isActivated = false
        person.isActivated = false





    }





}