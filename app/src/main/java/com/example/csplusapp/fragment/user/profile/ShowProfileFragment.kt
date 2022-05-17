package com.example.csplusapp.fragment.user.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.csplusapp.R
import com.example.csplusapp.databinding.FragmentEditProfileBinding
import com.example.csplusapp.databinding.FragmentShowProfileBinding

class ShowProfileFragment : Fragment() {
    private lateinit var binding: FragmentShowProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_show_profile,
            container,
            false
        )
        return binding.root
    }
}