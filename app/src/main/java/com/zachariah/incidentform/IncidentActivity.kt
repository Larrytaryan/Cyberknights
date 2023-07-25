package com.zachariah.incidentform

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class IncidentActivity : AppCompatActivity() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var editTextPhone: EditText
    private lateinit var editTextIncident: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident_form)

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextEmail = findViewById(R.id.editTextEmail)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextIncident = findViewById(R.id.editTextIncident)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            // Retrieve user inputs
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val email = editTextEmail.text.toString()
            val gender = getSelectedGender()
            val phone = editTextPhone.text.toString()
            val incident = editTextIncident.text.toString()

            // Here, you can perform further actions, such as saving the data or sending it to a server.
            // For this example, we will simply print the data.
            println("First Name: $firstName")
            println("Last Name: $lastName")
            println("Email: $email")
            println("Gender: $gender")
            println("Follow-up Phone Number: $phone")
            println("Incident Description: $incident")
        }
    }

    private fun getSelectedGender(): String {
        val radioButtonId = radioGroupGender.checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(radioButtonId)
        return selectedRadioButton?.text.toString()
    }
}
