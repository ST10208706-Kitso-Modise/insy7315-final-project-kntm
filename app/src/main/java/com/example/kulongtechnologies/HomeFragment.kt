package com.example.kulongtechnologies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var bottomNav: BottomNavigationView
    private var activity: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        bottomNav = view.findViewById(R.id.bottom_nav)
        setupBottomNavigation()
        
        // Set up button click listeners
        view.findViewById<View>(R.id.about_us_btn).setOnClickListener {
            bottomNav.selectedItemId = R.id.about
        }
        
        view.findViewById<View>(R.id.services_btn).setOnClickListener {
            bottomNav.selectedItemId = R.id.services
        }
        
        view.findViewById<View>(R.id.read_more_btn)?.setOnClickListener {
            bottomNav.selectedItemId = R.id.services
        }
        
        view.findViewById<View>(R.id.contact_us_btn)?.setOnClickListener {
            bottomNav.selectedItemId = R.id.contact
        }
        
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.home
        }
    }
    
    override fun onAttach(activity: android.app.Activity) {
        super.onAttach(activity)
        this.activity = activity as FragmentActivity
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.services -> {
                    replaceFragment(ServicesFragment())
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

    private fun showHome() {
        val current = activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
        if (current != null) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.remove(current)
                ?.commit()
        }
    }

    fun onServicesClick(view: View) {
        // Route through BottomNavigation to keep selection state in sync
        bottomNav.selectedItemId = R.id.services
    }
}