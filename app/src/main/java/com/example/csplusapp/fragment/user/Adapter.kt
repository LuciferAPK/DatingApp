package com.example.csplusapp.fragment.user

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.csplusapp.fragment.user.profile.EditProfileFragment
import com.example.csplusapp.fragment.user.profile.ShowProfileFragment

class Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> EditProfileFragment()
        else -> ShowProfileFragment()
    }
}