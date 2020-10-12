package com.poly.minjin.poly_project.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.activity.HomeActivity
import com.poly.minjin.poly_project.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private var userList = ArrayList<User>()
    var user : User? =  User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intent: Intent



        btn_login.setOnClickListener {

            if (edit_id.length() < 1 || edit_pw.length() < 1) {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                btn_login.text = "잠시만 기다려주세요..."
                btn_login.isEnabled = false
                getLoginData()
            }


        }

        btn_join.setOnClickListener {
            intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

    }




    private fun getLoginData() {

        val loginDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val loginRef: DatabaseReference = loginDatabase.getReference("User")

        loginRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (i: DataSnapshot in snapshot.children) {

                    user = i.getValue(User::class.java)
                    userList.add(user!!)
                }

                loginDataCheck(userList)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loginDataCheck(userList: ArrayList<User>) {

        val editId: String = edit_id.text.toString()
        val editPw: String = edit_pw.text.toString()
        var loginCheck = false
        var userData : User? = null


        for (i in userList.indices) {

            if (userList[i].id == editId) {
                if (userList[i].pw == editPw) {
                    Toast.makeText(
                        applicationContext,
                        "${userList[i].nickName}님 환영합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    userData = userList[i]
                    loginCheck = true
                    break
                }
            }

        }

        if (loginCheck) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("userData", userData)
            intent.putExtra("loginSuccess", true)
            startActivity(intent)
        } else if (!loginCheck) {
            btn_login.isEnabled = true
            Toast.makeText(applicationContext, "계정이 없습니다.", Toast.LENGTH_SHORT).show()
            btn_login.text = "LOGIN"

        }
    }


}