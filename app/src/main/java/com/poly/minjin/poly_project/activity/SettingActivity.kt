package com.poly.minjin.poly_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.base.BaseActivity
import kotlinx.android.synthetic.main.menu.*

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        movePage()
        home.isActivated = false
        search.isActivated = false
        star.isActivated = false
        heart.isActivated = false
        person.isActivated = true
    }
}