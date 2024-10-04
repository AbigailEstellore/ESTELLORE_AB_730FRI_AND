package com.estellore.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var tvName: TextView
    private lateinit var tvCourse: TextView
    private lateinit var tvContactNumber: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvTalent: TextView

    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize TextViews
        tvName = view.findViewById(R.id.tv_name)
        tvCourse = view.findViewById(R.id.tv_course)
        tvContactNumber = view.findViewById(R.id.tv_contactNumber)
        tvAge = view.findViewById(R.id.tv_age)
        tvEmail = view.findViewById(R.id.tv_email)
        tvGender = view.findViewById(R.id.tv_gender)
        tvTalent = view.findViewById(R.id.tv_talent)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        // Load and display saved data
        loadProfileData()

        // Find the button and set an OnClickListener
        val btnEditProfile = view.findViewById<Button>(R.id.btn_editProfile)
        btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        return view
    }

    private fun loadProfileData() {
        // Retrieve saved data from SharedPreferences and set it to the TextViews
        tvName.text = sharedPreferences.getString("name", "")
        tvCourse.text = sharedPreferences.getString("course", "")
        tvContactNumber.text = sharedPreferences.getString("contactNumber", "")
        tvAge.text = sharedPreferences.getString("age", "")
        tvEmail.text = sharedPreferences.getString("email", "")
        tvGender.text = sharedPreferences.getString("gender", "")
        tvTalent.text = sharedPreferences.getString("talent", "")
    }

    private fun showEditProfileDialog() {
        // Inflate the custom layout for the dialog
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null)

        val editTextUsername = dialogView.findViewById<EditText>(R.id.editTextUsername)
        val editTextCourse = dialogView.findViewById<EditText>(R.id.editTextCourse)
        val editTextContactNumber = dialogView.findViewById<EditText>(R.id.editTextContactNumber)
        val editTextAge = dialogView.findViewById<EditText>(R.id.editTextAge)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)
        val radioGroupGender = dialogView.findViewById<RadioGroup>(R.id.radioGroupGender)
        val checkBoxSinging = dialogView.findViewById<CheckBox>(R.id.checkBoxc)
        val checkBoxDancing = dialogView.findViewById<CheckBox>(R.id.checkBoxDancingcsharp)
        val checkBoxDrawing = dialogView.findViewById<CheckBox>(R.id.checkBoxjava)
        val checkBoxWriting = dialogView.findViewById<CheckBox>(R.id.checkBoxpython)

        // Load existing data into the dialog fields
        editTextUsername.setText(sharedPreferences.getString("name", ""))
        editTextCourse.setText(sharedPreferences.getString("course", ""))
        editTextContactNumber.setText(sharedPreferences.getString("contactNumber", ""))
        editTextAge.setText(sharedPreferences.getString("age", ""))
        editTextEmail.setText(sharedPreferences.getString("email", ""))

        // Set gender radio button
        val savedGender = sharedPreferences.getString("gender", "")
        if (savedGender == "Male") {
            radioGroupGender.check(R.id.radioMale)
        } else if (savedGender == "Female") {
            radioGroupGender.check(R.id.radioFemale)
        }

        // Set talent checkboxes
        val savedTalent = sharedPreferences.getString("talent", "")
        checkBoxSinging.isChecked = savedTalent?.contains("C++") == true
        checkBoxDancing.isChecked = savedTalent?.contains("C#") == true
        checkBoxDrawing.isChecked = savedTalent?.contains("Java") == true
        checkBoxWriting.isChecked = savedTalent?.contains("Python") == true

        // Create and show the AlertDialog
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()

        // Handle the Save button click
        dialogView.findViewById<Button>(R.id.btn_save).setOnClickListener {
            // Get data from input fields
            val newName = editTextUsername.text.toString().trim()
            val newCourse = editTextCourse.text.toString().trim()
            val newContactNumber = editTextContactNumber.text.toString().trim()
            val newAge = editTextAge.text.toString().trim()
            val newEmail = editTextEmail.text.toString().trim()

            // Get gender selection
            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            val newGender = if (selectedGenderId != -1) {
                dialogView.findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                ""
            }

            // Get talent selection
            val newTalentList = mutableListOf<String>()
            if (checkBoxSinging.isChecked) newTalentList.add("C++")
            if (checkBoxDancing.isChecked) newTalentList.add("C#")
            if (checkBoxDrawing.isChecked) newTalentList.add("Java")
            if (checkBoxWriting.isChecked) newTalentList.add("Python")

            val newTalent = newTalentList.joinToString(", ")

            // Validate that no field is empty
            if (newName.isEmpty() || newCourse.isEmpty() || newContactNumber.isEmpty() ||
                newAge.isEmpty() || newEmail.isEmpty() || newGender.isEmpty() || newTalent.isEmpty()) {
                // Show an error message if any field is empty
                Toast.makeText(context, "Please input all the fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save the data to SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("name", newName)
                editor.putString("course", newCourse)
                editor.putString("contactNumber", newContactNumber)
                editor.putString("age", newAge)
                editor.putString("email", newEmail)
                editor.putString("gender", newGender)
                editor.putString("talent", newTalent)
                editor.apply()

                // Update TextViews in FragmentProfile with the input data
                tvName.text = newName
                tvCourse.text = newCourse
                tvContactNumber.text = newContactNumber
                tvAge.text = newAge
                tvEmail.text = newEmail
                tvGender.text = newGender
                tvTalent.text = newTalent

                dialog.dismiss() // Close the dialog
            }
        }

        // Handle the Cancel button click
        dialogView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        dialog.show()
    }
}