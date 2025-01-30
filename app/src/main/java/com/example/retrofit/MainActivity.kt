package com.example.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.retrofit.models.User
import com.example.retrofit.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Make API Request
        val user = User(id = 1, name = "John Doe", email = "john.doe@example.com")

        // Make a network request using Retrofit
        lifecycleScope.launch {
            try {
                // Run the network request on the IO thread
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.sendDataToServer(user)
                }

                if (response.isSuccessful) {
                    // Handle successful response
                    Toast.makeText(applicationContext, "Data sent successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle error
                    Toast.makeText(applicationContext, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: IOException) {
                // Network failure (e.g., no internet)
                Toast.makeText(applicationContext, "Network failure", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                // HTTP error
                Toast.makeText(applicationContext, "HTTP error: ${e.code()}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}