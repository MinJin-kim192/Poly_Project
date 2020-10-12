package com.poly.minjin.poly_project.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.*
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.adapter.HomeAlcoholAdapter
import com.poly.minjin.poly_project.adapter.HomeViewPagerAdapter
import com.poly.minjin.poly_project.base.BaseActivity
import com.poly.minjin.poly_project.model.Alcohol
import com.poly.minjin.poly_project.model.EventPage
import com.poly.minjin.poly_project.model.User
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_home_vp.*
import kotlinx.android.synthetic.main.menu.*

class HomeActivity : BaseActivity() {

    private var backKeyPressedTime: Long = 0



    private var alcoholList = ArrayList<Alcohol>()
    private var eventPageList = ArrayList<EventPage>()

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        movePage()

        val edit = pref.edit()
        val user : User? = intent.getParcelableExtra<User>("userData")
        val firstCheck = intent.getBooleanExtra("loginSuccess",false)

        if(firstCheck) {
            edit.putString("id", user?.id.toString())
            edit.putString("nickName", user?.nickName.toString())
            edit.apply()
        }


        Log.d("로그","유저 아이디 : ${pref.getString("id","").toString()}")
        Log.d("로그","유저 닉네임 : ${pref.getString("nickName","").toString()}")



        home.isActivated = true
        search.isActivated = false
        star.isActivated = false
        heart.isActivated = false
        person.isActivated = false

    }



    override fun onStart() {
        super.onStart()
        getViewPagerData()

        makgeolli_btn.setOnClickListener {
            getRvServerData("Makgeolli")
        }

        yakju_btn.setOnClickListener {
            getRvServerData("yakju") // 아직 데이터 없음

        }

        distilledBeverage_btn.setOnClickListener {
            getRvServerData("distilledBeverage") // 아직 데이터 없음
        }

        fruitWine_btn.setOnClickListener {
            getRvServerData("fruitWine") // 아직 데이터 없음

        }

        etc_btn.setOnClickListener {
            getRvServerData("etc") // 아직 데이터 없음

        }

        makgeolli_btn.performClick()
    }






    private fun getViewPagerData(){
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val eventRef : DatabaseReference = database.getReference("eventPage")

        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(i : DataSnapshot in snapshot.children) {
                    val page: EventPage? = i.getValue(EventPage::class.java)
                    eventPageList.add(page!!)
                }
                setViewPager()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })



    }


    private fun getRvServerData(alcoholText : String){

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val alcoholRef : DatabaseReference = database.getReference(alcoholText)
        alcoholRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                alcoholList.clear()
                for (i: DataSnapshot in snapshot.children) {

                    val alcohol: Alcohol? = i.getValue(Alcohol::class.java)

                    alcoholList.add(alcohol!!)

                }
                setRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }


    private fun setRecyclerView() {

        alcohol_item_rv.adapter = HomeAlcoholAdapter(this, alcoholList, this)
        alcohol_item_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        alcohol_item_rv.setHasFixedSize(true)
    }

    private fun setViewPager(){
        image_slide.adapter = HomeViewPagerAdapter(this, eventPageList)
        image_slide.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        dots_indicator.setViewPager2(image_slide)
    }



    override fun onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis()
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG)
            toast.show()
            return
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {

            finish()
            toast.cancel()
            toast = Toast.makeText(this, "이용해 주셔서 감사합니다.", Toast.LENGTH_LONG)
            toast.show()

            onDestroy()
        }
    }

}

