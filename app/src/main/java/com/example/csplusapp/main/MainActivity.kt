package com.example.csplusapp.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.csplusapp.MyViewPager2Adapter
import com.example.csplusapp.R
import com.example.csplusapp.databinding.ActivityMainBinding
import com.example.csplusapp.databinding.LayoutHeaderNavBinding
import com.example.csplusapp.fragment.user.profile.firebaseimg.GetImgFrDtb
import com.example.csplusapp.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        private const val FRAGMENT_HOME = 0
        private const val FRAGMENT_FAVORITE = 1
        private const val FRAGMENT_MESSAGE = 2
        private const val FRAGMENT_USER = 3
    }

    private val mMyViewPager2Adapter by lazy {
        MyViewPager2Adapter(this)
    }

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val getImgFrDtb by lazy {
        GetImgFrDtb()
    }
    private val headerNavBinding by lazy {
        LayoutHeaderNavBinding.bind(binding.navigationView.getHeaderView(0))
    }

    private lateinit var getNameFromFirebase: String
    private var mCurrentFragment = FRAGMENT_HOME
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNameFromFirebase = ""
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getImgFrDtb.getImageFromFirebaseDatabase(headerNavBinding.imgAvt, this)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_drawer_open, R.string.nav_drawer_close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)

        binding.viewPager2.adapter = mMyViewPager2Adapter

//        binding.navigationView.menu.findItem(R.id.nav_home).isChecked = true
//        binding.bottomNavigation.menu.findItem(R.id.action_home).isChecked = true

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        mCurrentFragment = FRAGMENT_HOME
//                        openHomeFragment()
                        binding.bottomNavigation.menu.findItem(R.id.action_home).isChecked = true
                    }
                    1 -> {
                        mCurrentFragment = FRAGMENT_FAVORITE
//                        openFavoriteFragment()
                        binding.bottomNavigation.menu.findItem(R.id.action_favorite).isChecked =
                            true
                    }
                    2 -> {
                        mCurrentFragment = FRAGMENT_MESSAGE
//                        openMessageFragment()
                        binding.bottomNavigation.menu.findItem(R.id.action_message).isChecked = true
                    }
                    3 -> {
                        mCurrentFragment = FRAGMENT_USER
//                        openUserFragment()
                        binding.bottomNavigation.menu.findItem(R.id.action_user).isChecked = true
                    }
                }
                setTitleToolbar()
            }
        })
        menuBottomNavigationView()
        getUserInfo()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                binding.viewPager2.currentItem = 0
                mCurrentFragment = FRAGMENT_HOME
            }
        } else if (id == R.id.nav_favorite) {
            if (mCurrentFragment != FRAGMENT_FAVORITE) {
                binding.viewPager2.currentItem = 1
                mCurrentFragment = FRAGMENT_FAVORITE
            }
        } else if (id == R.id.nav_message) {
            if (mCurrentFragment != FRAGMENT_MESSAGE) {
                binding.viewPager2.currentItem = 2
                mCurrentFragment = FRAGMENT_MESSAGE
            }
        } else if (id == R.id.nav_user) {
            if (mCurrentFragment != FRAGMENT_USER) {
                binding.viewPager2.currentItem = 3
                mCurrentFragment = FRAGMENT_USER
            }
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }
        setTitleToolbar()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun menuBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    binding.viewPager2.currentItem = 0
                    mCurrentFragment = FRAGMENT_HOME
                }
                R.id.action_favorite -> {
                    binding.viewPager2.currentItem = 1
                    mCurrentFragment = FRAGMENT_FAVORITE
                }
                R.id.action_message -> {
                    binding.viewPager2.currentItem = 2
                    mCurrentFragment = FRAGMENT_MESSAGE
                }
                R.id.action_user -> {
                    binding.viewPager2.currentItem = 3
                    mCurrentFragment = FRAGMENT_USER
                }
            }
            true
        }
    }

    private fun setTitleToolbar() {
        var title = ""
        when (mCurrentFragment) {
            FRAGMENT_HOME -> title = getString(R.string.nav_home)
            FRAGMENT_MESSAGE -> title = getString(R.string.nav_message)
            FRAGMENT_FAVORITE -> title = getString(R.string.nav_favorite)
            FRAGMENT_USER -> title = getString(R.string.nav_user)
        }
        binding.toolbar.title = title
        if (supportActionBar != null) {
            supportActionBar?.title = title
        }
    }

//    private fun openHomeFragment() {
//        binding.navigationView.menu.findItem(R.id.nav_home).isChecked = true
//        binding.navigationView.menu.findItem(R.id.nav_message).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_user).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_favorite).isChecked = false
//    }
//
//    private fun openMessageFragment() {
//        binding.navigationView.menu.findItem(R.id.nav_message).isChecked = true
//        binding.navigationView.menu.findItem(R.id.nav_home).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_user).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_favorite).isChecked = false
//    }
//
//    private fun openUserFragment() {
//        binding.navigationView.menu.findItem(R.id.nav_user).isChecked = true
//        binding.navigationView.menu.findItem(R.id.nav_message).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_home).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_favorite).isChecked = false
//    }
//
//    private fun openFavoriteFragment() {
//        binding.navigationView.menu.findItem(R.id.nav_favorite).isChecked = true
//        binding.navigationView.menu.findItem(R.id.nav_user).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_message).isChecked = false
//        binding.navigationView.menu.findItem(R.id.nav_home).isChecked = false
//    }

    private fun getUserInfo() {
        val getName =
            FirebaseDatabase.getInstance().reference.child("user/${mAuth.currentUser?.uid}/name")
        getName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val getNameToString: String = snapshot.value.toString()
                getNameFromFirebase = getNameToString
                val email = mAuth.currentUser?.email
                headerNavBinding.tvEmail.text = email
                headerNavBinding.tvName.text = getNameToString
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}