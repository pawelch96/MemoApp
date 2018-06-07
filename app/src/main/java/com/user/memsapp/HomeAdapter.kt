package com.user.memsapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.home_item.view.*


class HomeAdapter(context: Context, uid : String) : RecyclerView.Adapter<CustomViewHolder>() {

    val context = context
    val userId = uid
    //var count : Int? = null

    override fun getItemCount(): Int {
        val ref = FirebaseDatabase.getInstance().getReference("users/" + userId + "/events")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                var size : Long = p0!!.childrenCount
            }

            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(context,"Nie udało się!", Toast.LENGTH_SHORT).show()
            }
        })


        return 4
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.home_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {

    }

}

class CustomViewHolder(val view : View): RecyclerView.ViewHolder(view) {
    val click = view.findViewById(R.id.menu_image) as ImageView

    init {
        click.setOnClickListener {
           Toast.makeText(click.context, "Click", Toast.LENGTH_SHORT).show()
        }
    }
}