package com.user.memsapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.home_item.view.*


class HomeAdapter(context: Context) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
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

    val click : ImageView = view.findViewById(R.id.menu_image)

    init {
        click.setOnClickListener {
           Toast.makeText(click.context, "dupa dupa", Toast.LENGTH_LONG)
        }
    }
}