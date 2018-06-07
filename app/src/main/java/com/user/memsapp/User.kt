package com.user.memsapp

import android.graphics.Bitmap
import android.media.Image


class User(val userId: String, val email : String, val name : String, val events: List<Event> ? = null, val friends: List<Friend>? = null)

class Event(val title : String? = null , val description : String? = null, val photo : Bitmap? = null, val date : String? = null, val place : String? = null)

class Friend(val friendId: String)