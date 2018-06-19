package com.user.memsapp

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.user.memsapp.R.layout.activity_home
import kotlinx.android.synthetic.main.home_item.view.*
import me.drakeet.materialdialog.MaterialDialog
import kotlinx.android.synthetic.main.popup_view.*


class HomeAdapter(val eventList : MutableList<Event>, val uid : String, val context: Context) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun getItemCount(): Int {

        return eventList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.home_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {

        val event : Event = eventList.get(position)
        holder?.view?.title_text?.text = event.title
        holder?.view?.description_text?.text = event.description
        holder?.view?.place_text?.text = event.place
        holder?.view?.date_text?.text = event.date
        val image = holder?.view?.event_image
        Picasso.with(holder?.view?.context).load(event.url).resize(640,360).centerCrop().into(image)
    }
}

class CustomViewHolder(val view : View): RecyclerView.ViewHolder(view) {

    val click = view.findViewById(R.id.menu_image) as ImageView

    init {

//        val inflater : LayoutInflater = LayoutInflater.from(view.context)
//        val pView = inflater.inflate(R.layout.popup_view, null)
//        val popupWindow = PopupWindow(
//                pView,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT
//        )
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            popupWindow.elevation = 10.0F
//        }
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val slideIn = Slide()
//            slideIn.slideEdge = Gravity.TOP
//            popupWindow.enterTransition = slideIn
//
//            val slideOut = Slide()
//            slideOut.slideEdge = Gravity.RIGHT
//            popupWindow.exitTransition = slideOut
//        }
//
//        click.setOnClickListener {
//
//
//            TransitionManager.beginDelayedTransition(view.findViewById(R.id.home_item))
//            popupWindow.showAtLocation(
//                    view.findViewById(R.id.home_item),
//                    Gravity.CENTER,
//                    0,
//                    0
//            )
//            popupWindow.setOnDismissListener {
//                Toast.makeText(view.context, "Popup closed", Toast.LENGTH_SHORT).show()
//            }

//            cancel_button.setOnClickListener {
//                //popupWindow.dismiss()
//                Toast.makeText(click.context, "Click", Toast.LENGTH_SHORT).show()
//            }
//
//            delete_button.setOnClickListener {
//                Toast.makeText(click.context, "Click", Toast.LENGTH_SHORT).show()
//
//            }

//        }
    }
}