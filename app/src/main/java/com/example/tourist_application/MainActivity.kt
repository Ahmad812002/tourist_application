package com.example.tourist_application




import Admin
import User
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.tourist_application.R.id.sign_in_button_id as sign_in_button
import com.example.tourist_application.R.id.sign_up_button_id as sign_up_button
import com.example.tourist_application.R.id.username_edit_text as username_edit_view
import com.example.tourist_application.R.id.password_edit_text as password_edit_view


private const val database_ref = "https://tourist-application-41b39-default-rtdb.firebaseio.com/"
private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
private val database_reference: DatabaseReference = database.getReference(database_ref)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sign_in_button = findViewById<Button>(sign_in_button)
        val sign_up_button = findViewById<Button>(sign_up_button)
        val username_edit_view = findViewById<EditText>(username_edit_view).toString()
        val password_edit_view = findViewById<EditText>(password_edit_view).toString()


        sign_in_button.setOnClickListener(){
            sign_in(username_edit_view,password_edit_view)
        }
        sign_up_button.setOnClickListener(){
            sign_up(username_edit_view,password_edit_view)
        }
    }
    private fun sign_in(username_edit_view: String,password_edit_view: String) {
        var authentication: Boolean = false
        database_reference.child("Admin").get().addOnSuccessListener { data_snapshot ->
            //using map to add password and username is suitable and faster but not secure
            val admins = mutableMapOf<String, String>()

            for (child_snapshot in data_snapshot.children) {
                val admin_username_from_data =
                    child_snapshot.child("admin_username").getValue(String::class.java)
                val admin_password_from_data =
                    child_snapshot.child("admin_password").getValue(String::class.java)
                admin_username_from_data?.let {
                    admin_password_from_data?.let {
                        admins[admin_username_from_data] = admin_password_from_data
                    }
                }
            }

            for ((admin_username, admin_password) in admins) {
                if (username_edit_view == admin_username && password_edit_view == admin_password) {
                    //go to the admin page
                    authentication = true
                }
            }

            val users = mutableMapOf<String, String>()

            for (child_snapshot in data_snapshot.children) {
                val user_username_from_data =
                    child_snapshot.child("User").getValue(String::class.java)
                val user_password_from_data =
                    child_snapshot.child("User").getValue(String::class.java)
                user_username_from_data?.let {
                    user_password_from_data?.let {
                        users[user_username_from_data] = user_password_from_data
                    }
                }
            }

            for ((user_username, user_password) in users) {
                if (user_username == username_edit_view && user_password == password_edit_view) {
                    //go to user page
                    authentication = true
                }
            }
        }
        if (authentication) {
            Toast.makeText(
                this,
                "username or password is incorrect try again or sing up",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun sign_up(username_edit_view: String,password_edit_view: String){
        val user_key = database_reference.child("User").push().key

        if(user_key != null) {
            val new_user = User(
                username = username_edit_view,
                user_password = password_edit_view
            )
            database_reference.child("User").child(user_key).setValue(new_user)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "sign up successfully welcome ${new_user.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener{error ->
                    Log.e("TAG", "Error signing up: $error")
                }
        }
    }
}






