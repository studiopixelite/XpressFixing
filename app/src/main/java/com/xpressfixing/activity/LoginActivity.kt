package com.xpressfixing.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xpressfixing.MainActivity
import com.xpressfixing.R
import com.xpressfixing.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    var databaseReference :  DatabaseReference? = null

    val MYPREFRENCES = "Preferences"
    val NAME = "username"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_login)

        auth = Firebase.auth

        sharedPreferences = getSharedPreferences(MYPREFRENCES, Context.MODE_PRIVATE)

        val currentuser = auth.currentUser
        if(currentuser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        binding.backButtonLogin.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.continueButton.setOnClickListener {
            if(binding.emailAddressText.text!!.isNotEmpty() && binding.textPassword.text!!.isNotEmpty()){
                auth.signInWithEmailAndPassword(binding.emailAddressText.text.toString().trim(), binding.textPassword.text.toString().trim())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(baseContext, "Login Successful", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            databaseReference = Firebase.database.reference
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            /* Toast.makeText(baseContext, "Login Failed.",
                                 Toast.LENGTH_SHORT).show()*/
                            val snackbar = Snackbar.make(binding.root, "Login failed. Please check your internet connection", Snackbar.LENGTH_LONG)
                            snackbar.duration = 5000
                            snackbar.setActionTextColor(resources.getColor(R.color.colorBackground))
                            snackbar.setAction("CLOSE", View.OnClickListener {
                            })
                            snackbar.setBackgroundTint(resources.getColor(R.color.colorRed))
                            snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE;
                            snackbar.show()
                            //updateUI(null)
                        }
                    }

            }else{
                val snackbar = Snackbar.make(binding.root, "A text field cannot be empty. Make sure you have filled all fields", Snackbar.LENGTH_LONG)
                snackbar.duration = 5000
                snackbar.setActionTextColor(resources.getColor(R.color.colorRed))
                snackbar.setAction("CLOSE", View.OnClickListener {

                })
                snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE;
                snackbar.show()
            }
        }

        binding.signUpText.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }


        binding.backButtonLogin.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun putUsername(string: String){
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(NAME, string);
        editor.apply();
    }
}