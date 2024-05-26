package com.example.tourist_application

import Admin
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




private const val database_ref = "https://tourist-application-41b39-default-rtdb.firebaseio.com/"

private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
private val database_reference: DatabaseReference = database.getReference(database_ref)




class Admin_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }






    }

    private fun add_admin() {
        //with push().key() we shouldn't add an ID generator while the push().key() automatically generate a special key
        val admin_key =
            database_reference.child("Admin").push().key // Generate a unique key for the admin
        val new_admin = Admin(
            admin_username = "AdminUsername@gmail.com",
            countries = "Jordan, German",
            admin_password = "admin123",
            questionnaire = "Some questionnaire"
        )

        //the code inside this (?.let) does not work only if admin_key is not null
        admin_key?.let {
            database_reference.child("Admin").child(it).setValue(new_admin)
                .addOnSuccessListener {
                    // Data successfully written to the database
                    println("Admin added successfully")
                }
                .addOnFailureListener { e ->
                    // Handle any errors that occurred during the data writing process
                    println("Error adding admin: ${e.message}")
                }
        }
    }
}