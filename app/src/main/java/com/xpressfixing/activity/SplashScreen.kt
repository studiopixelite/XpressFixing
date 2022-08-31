package com.xpressfixing.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.xpressfixing.R



@SuppressWarnings("Deprecation")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        val MYPREFRENCES = "Preferences"
        val KEY_ISFIRSTTIME = "isFirstTime"
        var sharedPreferences: SharedPreferences? = null

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_splash_screen)

        loadNextActivity()

    }

    private fun loadNextActivity() {
        /*if(sharedPreferences!!.getBoolean(KEY_ISFIRSTTIME, true)){
            Handler().postDelayed(
                {
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000
            )
        }

        else {
            Handler().postDelayed(
                {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000
            )
        }*/
        Handler().postDelayed(
            {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000
        )
    }
}