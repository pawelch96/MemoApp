package com.user.memsapp

import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var uid : String? = null
    lateinit var ref : DatabaseReference
    lateinit var eventList: MutableList<Event>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById(R.id.action_bar) as Toolbar
        setSupportActionBar(toolbar)

        uid = intent.getStringExtra("uid")
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val rvHome = findViewById(R.id.recyclerView_home) as RecyclerView
        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = LinearLayoutManager(this)
        ref = FirebaseDatabase.getInstance().getReference("users/" + uid + "/events")
        eventList = mutableListOf()
        runOnUiThread {
            ref.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.exists()) {
                        eventList.clear()
                        ref.orderByChild("date")
                        for (e in p0.children) {
                            val event = e.getValue(Event::class.java)
                            eventList.add(event!!)
                        }
                    eventList.sortByDescending { it.date }
                    }
                    if (eventList.size != 0) {
                        recyclerView_home.adapter = HomeAdapter(eventList, uid!!, applicationContext)
                        progress_bar.visibility = View.GONE
                    } else {
                        progress_bar.visibility = View.GONE
                        alert_text.visibility = View.VISIBLE
                        add_button.visibility = View.VISIBLE

                        add_button.setOnClickListener {
                            val intent : Intent = Intent(applicationContext, AddActivity::class.java)
                            intent.putExtra("uid", uid)
                            alert_text.visibility = View.GONE
                            add_button.visibility = View.GONE
                            startActivity(intent)
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(applicationContext).inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.action_add -> {
                val intent : Intent = Intent(this, AddActivity::class.java)
                intent.putExtra("uid", uid)
                startActivity(intent)
                return true
            }
            R.id.action_search -> {
                Toast.makeText(applicationContext,"Search clicked.", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_settings -> {
                Toast.makeText(applicationContext,"Settings clicked.", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
