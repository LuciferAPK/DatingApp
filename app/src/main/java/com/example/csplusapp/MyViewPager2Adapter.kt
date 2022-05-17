package com.example.csplusapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.csplusapp.fragment.favorite.FavoriteFragment
import com.example.csplusapp.fragment.history.HistoryFragment
import com.example.csplusapp.fragment.home.HomeFragment
import com.example.csplusapp.fragment.message.MessageFragment
import com.example.csplusapp.fragment.user.UserFragment

class MyViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> FavoriteFragment()
        2 -> MessageFragment()
        else -> UserFragment()
    }
}