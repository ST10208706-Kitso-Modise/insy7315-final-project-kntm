package com.example.kulongtechnologies

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ServicesFragment : Fragment() {

    private lateinit var bottomNav: BottomNavigationView
    private var activity: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNav = view.findViewById(R.id.bottom_nav)
        setupBottomNavigation()

        view.findViewById<View>(R.id.contactbtn)?.setOnClickListener {
            // Navigate to contact fragment using bottom navigation to keep the state in sync
            bottomNav.selectedItemId = R.id.contact
        }
        view.findViewById<View>(R.id.getaquotebtn)?.setOnClickListener {
            bottomNav.selectedItemId = R.id.contact
        }

        view.findViewById<View>(R.id.getaquotebtn2)?.setOnClickListener {
            bottomNav.selectedItemId = R.id.contact
        }

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.services
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
                    // Already on Services
                    true
                }
                R.id.contact -> {
                    replaceFragment(ContactFragment())
                    true
                }
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
