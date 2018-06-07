package com.user.memsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var uid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById(R.id.action_bar) as Toolbar
        setSupportActionBar(toolbar)
        uid = intent.getStringExtra("uid")
        supportActionBar?.setDisplayShowTitleEnabled(false)
        recyclerView_home.setHasFixedSize(true)
        recyclerView_home.layoutManager = LinearLayoutManager(this)
        recyclerView_home.adapter = HomeAdapter(this, uid!!)
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
