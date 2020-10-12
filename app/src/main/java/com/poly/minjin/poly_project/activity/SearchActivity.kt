package com.poly.minjin.poly_project.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.poly.minjin.poly_project.R
import com.poly.minjin.poly_project.adapter.SearchRvAlcoholAdapter
import com.poly.minjin.poly_project.base.BaseActivity
import com.poly.minjin.poly_project.model.Alcohol
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.menu.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchActivity : BaseActivity() {


    var typeList = ArrayList<String>()
    var typeIndex = 0
    var alcoholSearch: Alcohol? = null
    var alcoholSearchList = ArrayList<Alcohol>()
    var searchSuccessList: ArrayList<Alcohol>? = ArrayList<Alcohol>()
    var position = 0
    var searchCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        movePage()
        home.isActivated = false
        search.isActivated = true
        star.isActivated = false
        heart.isActivated = false
        person.isActivated = false


        search_button.setOnClickListener {

            if (search_edit.length() < 1) {
                Toast.makeText(this, "검색어를 입력 후 클릭해주세요", Toast.LENGTH_SHORT).show()
            } else {
                empty_layout.visibility = View.GONE
                loading_circle.visibility = View.VISIBLE
                notFound_layout.visibility = View.INVISIBLE
                typeIndex = 0
                position = 0
                searchCheck = false
                alcoholSearchList.clear()
                searchSuccessList?.clear()
                getSearchData(search_edit.text.toString(), typeIndex)
            }

        }

    }

    private fun getSearchData(searchInput: String, index: Int) {

        typeList.apply {
            add("yakju")
            add("Makgeolli")
            add("etc")
            add("fruitWine")
            add("distilledBeverage")
        }

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val alcoholRef: DatabaseReference = database.getReference(typeList[index])
        alcoholRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (i: DataSnapshot in snapshot.children) {
                    alcoholSearch = i.getValue(Alcohol::class.java)
                    alcoholSearchList.add(alcoholSearch!!)

                    Log.d("로그", alcoholSearchList[position].name.toString())

                    if (searchInput in alcoholSearchList[position].name.toString() ||
                        searchInput in alcoholSearchList[position].type.toString() ||
                        searchInput in alcoholSearchList[position].point.toString()
                    ) {
                        loading_circle.visibility = View.INVISIBLE
                        notFound_layout.visibility = View.INVISIBLE
                        searchCheck = true
                        searchSuccessList!!.add(alcoholSearchList[position])
                    }
                    position++
                }


                if (searchCheck && index == 4) {
                    rv_search.visibility = View.VISIBLE
                    setSearchRecyclerView(searchSuccessList!!)
                }




                if (index == 4 && !searchCheck) {
                    rv_search.visibility = View.INVISIBLE
                    loading_circle.visibility = View.INVISIBLE
                    notFound_layout.visibility = View.VISIBLE

                } else if (index < 4) {
                    getSearchData(searchInput, ++typeIndex)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun setSearchRecyclerView(searchSuccessList: ArrayList<Alcohol>) {


        rv_search.adapter = SearchRvAlcoholAdapter(this, searchSuccessList, this)
        rv_search.layoutManager = LinearLayoutManager(this)



    }


}