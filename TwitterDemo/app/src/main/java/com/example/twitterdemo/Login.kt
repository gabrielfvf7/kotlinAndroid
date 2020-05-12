package com.example.twitterdemo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Login : AppCompatActivity() {

    private val READ_EXTERNAL_STORAGE: Int = 253
    private var myAuth: FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        myAuth = FirebaseAuth.getInstance()

        ivPerson.setOnClickListener {
            checkPermission()
        }

    }

    fun loginToFirebase(email: String, password: String) {
        myAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login realizado", Toast.LENGTH_LONG).show()
                    val currentUser = myAuth!!.currentUser
                    saveImageToFirebase(currentUser!!)
                } else {
                    Toast.makeText(applicationContext, "Erro ao logar", Toast.LENGTH_LONG).show()
                    Log.d("porra", task.exception.toString())
                }
            }
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE)
                return
            } else {
                loadImage()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            READ_EXTERNAL_STORAGE -> {
                if(grantResults[0]  == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                } else {
                    Toast.makeText(this, "Give the permission to load image", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val pickImageCode = 12

    fun loadImage() {
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(galleryIntent, pickImageCode)
    }

    fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun saveImageToFirebase(currentUser: FirebaseUser) {
        Toast.makeText(this, "Realzando uplaod da img", Toast.LENGTH_SHORT).show()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs://twitterdemo-7e282.appspot.com")
        val df = SimpleDateFormat("ddMMyyHHmmss")
        val dataObj = Date()
        val imgPath = "${splitString(currentUser.email!!)}.${df.format(dataObj)}.jpg"
        val imgRef = storageRef.child("images/${imgPath}")
        val bitmap = getBitmapFromView(ivPerson)
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imgRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Failed to upload", Toast.LENGTH_LONG).show()
            Log.d("porra", it.toString())
        }
            .addOnSuccessListener { taskSnapshot ->
                Log.d("porra", "fazendo upload")
                val downloadURL = taskSnapshot.storage.downloadUrl.toString()
                myRef.child("Users").child(currentUser.uid).child("email").setValue(currentUser.email)
                myRef.child("Users").child(currentUser.uid).child("ProfileImage").setValue(downloadURL)
                loadTweets()
            }
    }

    override fun onStart() {
        super.onStart()
        loadTweets()
    }

    fun loadTweets() {
        val currentUser = myAuth!!.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)
            startActivity(intent)
        }
    }

    fun splitString(email: String): String {
        val split = email.split("@")
        return split[0]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageCode && data != null && resultCode == Activity.RESULT_OK) {
           try {
               val imgUri = data.data
               val bitMap: Bitmap = BitmapFactory.decodeStream(imgUri?.let {
                   contentResolver.openInputStream(it)
               })
               ivPerson.setImageBitmap(bitMap)
           } catch (ex: Exception) {
               Log.d("ERRO", ex.toString())
           }
        }
    }

    fun onClickLogin(view: View) {
        loginToFirebase(etEmail.text.toString(), etPassword.text.toString())
    }
}
