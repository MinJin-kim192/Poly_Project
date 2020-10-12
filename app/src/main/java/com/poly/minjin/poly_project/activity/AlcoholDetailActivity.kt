package com.poly.minjin.poly_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.model.Alcohol
import kotlinx.android.synthetic.main.activity_alcohol_detail.*
import kotlinx.android.synthetic.main.activity_alcohol_detail.alcohol_item_image
import kotlinx.android.synthetic.main.activity_alcohol_detail.view.*
import kotlinx.android.synthetic.main.item_alcohol_item.*

class AlcoholDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alcohol_detail)

        val alcoholItem = intent.getParcelableExtra<Alcohol>("alcohol_item")

        Glide.with(this)
            .load(alcoholItem.imageUrl)
            .error(R.drawable.ic_baseline_emoji_food_beverage_24)
            .into(alcohol_item_image)

        alcohol_item_name.text = alcoholItem.name
        alcohol_item_type.text = alcoholItem.type
        alcohol_item_amount.text = alcoholItem.amount
        alcohol_item_alcohol.text = alcoholItem.alcohol
        alcohol_item_point.text = alcoholItem.point


        back_btn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

    }

}