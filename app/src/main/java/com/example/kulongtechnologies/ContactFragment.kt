package com.example.kulongtechnologies

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class ContactFragment : Fragment() {

    private lateinit var bottomNav: BottomNavigationView
    private var activity: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNav = view.findViewById(R.id.bottom_nav)
        setupBottomNavigation()

        val nameEt = view.findViewById<TextInputEditText>(R.id.etName)
        val emailEt = view.findViewById<TextInputEditText>(R.id.etEmail)
        val subjectEt = view.findViewById<TextInputEditText>(R.id.etSubject)
        val messageEt = view.findViewById<TextInputEditText>(R.id.etMessage)
        val submitBtn = view.findViewById<Button>(R.id.btnSubmit)

        submitBtn.setOnClickListener {
            val name = nameEt.text?.toString()?.trim().orEmpty()
            val email = emailEt.text?.toString()?.trim().orEmpty()
            val subject = subjectEt.text?.toString()?.trim().orEmpty()
            val message = messageEt.text?.toString()?.trim().orEmpty()

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in name, email and message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "name" to name,
                "email" to email,
                "subject" to subject,
                "message" to message,
                "timestamp" to System.currentTimeMillis()
            )

            val ref = FirebaseDatabase.getInstance().getReference("contactMessages")
            val key = ref.push().key
            if (key == null) {
                Toast.makeText(requireContext(), "Failed to create request, try again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submitBtn.isEnabled = false
            ref.child(key).setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Message sent", Toast.LENGTH_SHORT).show()
                    nameEt.setText("")
                    emailEt.setText("")
                    subjectEt.setText("")
                    messageEt.setText("")
                    submitBtn.isEnabled = true
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to send: ${it.message}", Toast.LENGTH_SHORT).show()
                    submitBtn.isEnabled = true
                }
        }

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.contact
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as FragmentActivity
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.services -> {
                    replaceFragment(ServicesFragment())
                    true
                }
                R.id.contact -> true // already here
                R.id.about -> {
                    replaceFragment(AboutFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.commit()
    }
}
