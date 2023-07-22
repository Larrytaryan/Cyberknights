package com.example.cyberknight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cyberknight.databinding.ActivitySignupBinding
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                // Register user in Firebase
                if (password == confirmPassword) {
                    val actionCodeSettings = ActionCodeSettings.newBuilder()
                        .setUrl("http://www.example.com/verify")
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName("com.example.CyberKnight", false, null)
                        .build() //set it true if you want to be downloaded from playstore

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Registration successful
                                val user = firebaseAuth.currentUser
                                if (user != null) {
                                    // Send the verification email
                                    user.sendEmailVerification(actionCodeSettings)
                                        .addOnCompleteListener { verificationTask ->
                                            if (verificationTask.isSuccessful) {
                                                Log.d(TAG, "Email sent.")
                                                // Show a toast message indicating that the verification email has been sent
                                                Toast.makeText(
                                                    this,
                                                    "Verification email sent. Please check your email for verification.",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Wait for the user to verify their email before proceeding
                                                waitForEmailVerification(user)
                                            } else {
                                                Log.e(TAG, "Failed to send verification email: ${verificationTask.exception}")
                                                // Show an error toast message for verification email sending failure
                                                Toast.makeText(
                                                    this,
                                                    "Failed to send verification email. Please try again later.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    // User is null, handle the error case
                                    Toast.makeText(
                                        this,
                                        "Registration failed. Please try again later.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                // Registration failed
                                Toast.makeText(
                                    this,
                                    "Registration failed. ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    // Password does not match
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Fields cannot be empty
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginRedirectText.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun waitForEmailVerification(user: FirebaseUser) {
        firebaseAuth.addAuthStateListener { auth ->
            val currentUser = auth.currentUser
            if (currentUser != null && currentUser.uid == user.uid) {
                // Check if the user's email has been verified
                if (currentUser.isEmailVerified) {
                    // Email verified, proceed with the account creation and registration
                    Toast.makeText(this, "Email verified. Account created successfully!", Toast.LENGTH_SHORT).show()
                    // You can add your code here to redirect the user to the next screen or perform other actions
                } else {
                    // Email not verified yet, wait for the next check
                }
            }
        }
    }

    companion object {
        private const val TAG = "SignupActivity"
    }
}
