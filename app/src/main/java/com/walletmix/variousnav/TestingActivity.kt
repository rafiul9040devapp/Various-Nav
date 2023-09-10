package com.walletmix.variousnav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.walletmix.variousnav.databinding.ActivityTestingBinding

class TestingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityTestingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //navigation bottom
        binding.bottomNavigationView.background = null

        //default fragment setup
        supportFragmentManager.beginTransaction()
            .add(R.id.frame, HomeFragment())
            .commit()

        //custom appbar setup
        binding.toolbar.merchantName.setText(R.string.app_name)
       binding.toolbar.ivLogout.setOnClickListener {
           binding.root.openDrawer(GravityCompat.END)
       }

        //navigation drawer setup
        val toggle =
            ActionBarDrawerToggle(this, binding.root, R.string.open_drawer, R.string.close_drawer)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        binding.drawerNavigation.setNavigationItemSelectedListener(this)

        //bottom navigation setup
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_profile -> openFragment(ProfileFragment())
                R.id.nav_search -> openFragment(SearchFragment())
                R.id.nav_settings -> openFragment(SettingFragment())
            }
            true
        }

        //bottom sheet setup
        binding.fab.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.item_bottom_sheet,null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)

            val person = view.findViewById<View>(R.id.person)
            val share = view.findViewById<View>(R.id.share)
            val setting = view.findViewById<View>(R.id.setting)
            val home = view.findViewById<View>(R.id.home)

            person.setOnClickListener{
                openFragment(ProfileFragment())
                dialog.dismiss()
            }

            share.setOnClickListener {
                replaceFragment(SearchFragment())
                dialog.dismiss()
            }

            setting.setOnClickListener {
                replaceFragment(SettingFragment())
                dialog.dismiss()
            }

            home.setOnClickListener {
                replaceFragment(HomeFragment())
                dialog.dismiss()
            }

            dialog.show()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.dnav_home -> replaceFragment(HomeFragment())
            R.id.dnav_profile -> openFragment(ProfileFragment())
            R.id.dnav_search -> openFragment(SearchFragment())
            R.id.dnav_settings -> openFragment(SettingFragment())
        }
        binding.root.closeDrawer(GravityCompat.END)
        return true
    }

    override fun onBackPressed() {
        if (binding.root.isDrawerOpen(GravityCompat.END)) {
            binding.root.closeDrawer(GravityCompat.END)
        } else {
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.addToBackStack(null) // Add to back stack if needed
        fragmentTransaction.commit()
        binding.root.closeDrawers()
    }
}