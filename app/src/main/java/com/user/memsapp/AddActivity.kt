package com.user.memsapp

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add.*
import java.io.IOException
import java.net.URL
import java.util.*

class AddActivity : AppCompatActivity() {

    internal var storage : FirebaseStorage? = null
    internal var storageReference : StorageReference? = null
    private var date : String? = null
    private val PICK_IMAGE_REQUEST = 1234
    private var filePath : Uri? = null
    private var uid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        uid = intent.getStringExtra("uid")

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        date_button.setOnClickListener {
            datePick()
        }

        photo_button.setOnClickListener {
            photoPick()
        }

        upload_button.setOnClickListener {
            validateAndUpload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            filePath = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                photo_view.visibility = View.VISIBLE
                photo_view!!.setImageBitmap(bitmap)
            } catch (e:IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun photoPick() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Wybierz zdjęcie"), PICK_IMAGE_REQUEST)
    }

    private fun datePick() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
            date = "" + mYear
            if (mMonth < 10) date += ".0" + mMonth
            else date += "." + mMonth
            if (mDay < 10) date += ".0" + mDay
            else date += "." + mDay
            date_text.setText("" + mDay + "." + mMonth + "." + mYear)
        }, year, month, day)
        datePickerDialog.show()
    }

    fun isValid (editText: EditText) : Boolean {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Pole nie może być puste!"
            return false
        }
        return true
    }

    private fun validateAndUpload() {
        if (isValid(title_text) && isValid(place_text) && isValid(description_text)) {
            var event = Event(title_text.text.toString(), description_text.text.toString(), date, place_text.text.toString(), "", "")
            uploadEvent(event)
        }
    }

    private fun uploadEvent(event : Event) {
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageReference!!.child("users/"+ uid + "/events/" + uuid)
        val ref = FirebaseDatabase.getInstance().getReference("users/" + uid + "/events")
        var url : String? = null
        if (filePath!=null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle(getString(R.string.loading))
            progressDialog.show()
            imageRef!!.putFile(filePath!!).addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                progressDialog.setMessage("" + progress.toInt() + "%")

            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Dodawanie zdjęcia nie powiodło się!", Toast.LENGTH_LONG).show()
            }.addOnSuccessListener { taskSnapshot ->

                    url = taskSnapshot.downloadUrl.toString()
                    event.uuid = uuid
                    event.url = url
                    ref.child(uuid).setValue(event).addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Dodano wydarzenie.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("uid", uid)
                        startActivity(intent)

                    }.addOnFailureListener {
                        Toast.makeText(this, "Dodawanie nie powiodło się!", Toast.LENGTH_LONG).show()
                    }
            }

        } else {
            Toast.makeText(this, "Dodawanie zdjęcia nie powiodło się!", Toast.LENGTH_LONG).show()
                }
        }
    }

