package com.poly.minjin.poly_project.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.model.User
import com.poly.minjin.poly_project.model.UserCount
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {


    var isLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)


        back_btn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        val joinDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val joinRef: DatabaseReference = joinDatabase.getReference("User/UserCount")

        join_btn.setOnClickListener {

            if (edit_step_one.length() < 1 || edit_step_two.length() < 1 || edit_step_three.length() < 1) {
                Toast.makeText(applicationContext, "빈 값이 있습니다. 다시 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else{
               joinRef.addValueEventListener(object : ValueEventListener {
                   override fun onDataChange(snapshot: DataSnapshot) {
                       val userCount : UserCount? =  snapshot.getValue(UserCount::class.java)

                       setJoin(userCount!!.Count!!.toInt(), joinDatabase)
                       Log.d("로그",userCount.Count.toString())

                   }

                   override fun onCancelled(error: DatabaseError) {
                       Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                   }
               })


            }
        }


    }


    private fun setJoin(userCount : Int, joinDatabase: FirebaseDatabase ){
        var id : String? = edit_step_one.text.toString()
        var pw : String? = edit_step_two.text.toString()
        var nickname : String? = edit_step_three.text.toString()

        val joinRef: DatabaseReference = joinDatabase.getReference("User")
        val user = User(id, pw, nickname)

        var count = userCount


        if(id != null && pw != null && nickname != null && id != "" && pw != "" && nickname != "") {



            if(isLogin) {

                isLogin = false

                count++

                joinRef.child("UserCount")
                    .child("Count")
                    .setValue(count.toString())

                joinRef.child("User_0${count}")
                    .child("id")
                    .setValue(user.id.toString())

                joinRef.child("User_0${count}")
                    .child("pw")
                    .setValue(user.pw.toString())

                joinRef.child("User_0${count}")
                    .child("nickName")
                    .setValue(user.nickName.toString())


                Toast.makeText(applicationContext, "회원가입 성공", Toast.LENGTH_SHORT).show()
            }

        }

        // 이렇게까지 해야 되나...
        id = null
        pw = null
        nickname = null
        edit_step_one.text = null
        edit_step_two.text = null
        edit_step_three.text = null

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}