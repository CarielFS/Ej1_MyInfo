package com.cafesa.ej1_myinfo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cafesa.ej1_myinfo.databinding.ActivityFormBinding
import java.util.*

class Form : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private lateinit var career: String //Stores the career of the user
    var careerIdx = 0
    private lateinit var months: Array<String> //Creates an array of the months

    //Variables to get and store dates
    private val calendar: Calendar = Calendar.getInstance()
    var varYear = calendar.get(Calendar.YEAR)
    var varMonth = calendar.get(Calendar.MONTH)
    var varDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializes the spinner
        val items = resources.getStringArray(R.array.form_careers)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spnCareer.adapter = adapter

        //Function to get the current value of the spinner
        binding.spnCareer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.BLACK)
                (parent.getChildAt(0) as TextView).textSize = 18F

                career = parent.getItemAtPosition(position) as String
                careerIdx = position
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
            }
        }

        //Initializes date picker with current date
        months = resources.getStringArray(R.array.form_months)
        binding.btnPickDate.text = getString(R.string.form_date_hint,varDayOfMonth,months[varMonth],varYear)

    }

    //Function to show datePicker menu when button is pressed
    fun pickDate(view: View) {
        val onDateSetListener = MyOnDateSetListener()
        val datePickerDialog = DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onDateSetListener, varYear, varMonth, varDayOfMonth)
        datePickerDialog.show()
    }

    // Inner class to implement the DatePickerDialog.OnDateSetListener interface
    private inner class MyOnDateSetListener : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            varYear = year
            varMonth = month
            varDayOfMonth = dayOfMonth
            binding.btnPickDate.text = getString(R.string.form_date_hint,varDayOfMonth,months[varMonth],varYear)
        }
    }

    fun logIn(view: View) {
        //Stores boolean values to change to the other activity
        var checkName = false
        var checkLastName = false
        var checkBirthDate = false
        var checkMail = false
        var checkNumber = false

        //Stores values of current and birth date
        val currDate = calendar.time
        val cal = Calendar.getInstance()
        cal.set(varYear,varMonth,varDayOfMonth)
        val birthDate = cal.time

        //Stores values from form into variables
        val email: String = binding.etEmail.text.toString()

        //Regex to check if string is valid
        //val pattern = "[^A-Za-z\\s]".toRegex()
        val regex = Regex("^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]+([\\s.]+[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]+)*\$")

        //Validates the name field
        if(binding.etName.text.isNotEmpty()){
            if(regex.matches(binding.etName.text)){
                checkName = true
            }else{
                binding.etName.error = getString(R.string.err_invalid_name)
            }
        }else{
            binding.etName.error = getString(R.string.err_no_name)
        }

        //Validates the name field
        if(binding.etLastName.text.isNotEmpty()){
            if(regex.matches(binding.etLastName.text)){
                checkLastName = true
            }else{
                binding.etLastName.error = getString(R.string.err_invalid_lastName)
            }
        }else{
            binding.etLastName.error = getString(R.string.err_no_lastName)
        }

        //Validates the number field
        if(binding.etNumber.text.isNotEmpty()){
            if(binding.etNumber.text.toString().length==9){
                checkNumber = true
            }else{
                binding.etNumber.error = getString(R.string.err_invalid_number)
            }
        }else{
            binding.etNumber.error = getString(R.string.err_no_number)
        }

        //Validates the date field
        if(currDate>birthDate){
            checkBirthDate = true
        }else{
            Toast.makeText(this,getString(R.string.err_invalid_date),Toast.LENGTH_LONG).show()
        }

        //Validates the mail field
        if(binding.etEmail.text.isNotEmpty()){
            //validates if the email is valid
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                checkMail = true
            }else{
                binding.etEmail.error = resources.getString(R.string.err_invalid_email)
            }
        }else{
            binding.etEmail.error = resources.getString(R.string.err_no_email)
        }

        if(checkName and checkLastName and checkBirthDate and checkMail and checkNumber) {

            val intent = Intent(this,Results::class.java)

            val bundle = Bundle()

            bundle.putString("name",binding.etName.text.toString())
            bundle.putString("lastName",binding.etLastName.text.toString())
            bundle.putInt("year",varYear)
            bundle.putInt("month",varMonth)
            bundle.putInt("day",varDayOfMonth)
            bundle.putString("mail",binding.etEmail.text.toString())
            bundle.putInt("number",binding.etNumber.text.toString().toInt())
            bundle.putInt("career",careerIdx)

            intent.putExtras(bundle)
            startActivity(intent)

        }

    }
}


