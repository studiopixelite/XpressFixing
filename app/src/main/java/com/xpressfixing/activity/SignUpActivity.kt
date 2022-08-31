package com.xpressfixing.activity

import android.content.ContentValues.TAG
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xpressfixing.MainActivity
import com.xpressfixing.R
import com.xpressfixing.databinding.ActivitySignUpBinding
import com.xpressfixing.model.Profile
import com.xpressfixing.model.User

class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    val MYPREFRENCES = "Preferences"
    val NAME = "username"
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_sign_up)


        auth = Firebase.auth
        val image = "https://firebasestorage.googleapis.com/v0/b/foodiecom-736b9.appspot.com/o/images%2Fno-profile-pic-icon-12.jpg?alt=media&token=f871ba9b-2d54-4130-9e1f-a6273070ceb5"

        sharedPreferences = getSharedPreferences(MYPREFRENCES, Context.MODE_PRIVATE);

        val currentuser = auth.currentUser
        if(currentuser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.continueButton.setOnClickListener {
            if(binding.nameTextInput.text!!.isNotEmpty() && binding.emailAddressText.text!!.isNotEmpty() && binding.textPassword.text!!.isNotEmpty()){
                auth.createUserWithEmailAndPassword(binding.emailAddressText.text.toString().trim(), binding.textPassword.text.toString().trim())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            databaseReference = Firebase.database.reference
                            val user = auth.currentUser
                            val currentUser = auth.currentUser

                            val User = User(currentUser!!.email, binding.nameTextInput.text.toString(), image, "XpressFixing User", currentUser.uid)
                            // val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                            databaseReference?.child("users")!!.child(currentUser.uid).setValue(User).addOnSuccessListener {
                                Log.d("TAG", "Success")

                                val Profile = Profile(binding.nameTextInput.text.toString(), "XpressFixing User", image, currentUser.uid)
                                databaseReference?.child("profile")!!.child(currentUser.uid).setValue(Profile).addOnSuccessListener {
                                    Log.d("TAG", "Success")

                                }
                                    .addOnFailureListener {
                                        Log.d("TAG", "Failed")
                                    }
                            }
                                .addOnFailureListener {
                                    Log.d("TAG", "Failed")

                                }

                            // val Profile = Profile(nameTextInput.text.toString(), "Just a Foodiecom user", image, currentUser.uid)
                            // val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                            /*   try{
                                   val Profile = Profile(nameTextInput.text.toString(), "Just a Foodiecom user", image, currentUser.uid)
                                   databaseReference?.child("profile")!!.child(currentUser.uid).setValue(Profile).addOnSuccessListener {
                                       Log.d("TAG", "Success")
                                   }
                                       .addOnFailureListener {
                                           Log.d("TAG", "Failed")
                                       }
                               }catch(e: Exception){
                                   Toast.makeText(this@SignupActivity, e.message, Toast.LENGTH_SHORT).show()
                               }*/


                            putUsername(binding.nameTextInput.text.toString())

                            Toast.makeText(baseContext, "Authentication Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication Failed. ${task.exception!!.toString()}",
                                Toast.LENGTH_SHORT).show()
                            val snackbar = Snackbar.make(binding.signupLayout, "Signup failed. Please check your internet connection", Snackbar.LENGTH_LONG)
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
                val snackbar = Snackbar.make(binding.signupLayout, "A text field cannot be empty. Make sure you have filled all fields", Snackbar.LENGTH_LONG)
                snackbar.duration = 5000
                snackbar.setActionTextColor(resources.getColor(R.color.colorBackground))
                snackbar.setAction("CLOSE", View.OnClickListener {
                })
                snackbar.setBackgroundTint(resources.getColor(R.color.colorRed))
                snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE;
                snackbar.show()
            }
        }

        binding.loginText.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }


        binding.backButton.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    private fun putUsername(string: String){
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(NAME, string);
        editor.apply();
    }
}