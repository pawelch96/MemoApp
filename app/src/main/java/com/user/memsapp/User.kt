package com.user.memsapp

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import java.util.*


class User(val userId: String, val email : String, val name : String, val events: List<Event> ? = null, val friends: List<Friend>? = null)

class Event(val title : String? = null , val description : String? = null, val date : String? = null, val place : String? = null, var uuid : String? = null, var url : String? = null)

class Friend(val friendId: String)