package com.example.csplusapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.csplusapp.fragment.user.User

class UserAdapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var getLinkImageInFirebaseDatabase = R.drawable.avt_default

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_converstation, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        if (!currentUser.imgUrl.isNullOrEmpty()) {
            Glide.with(holder.image.context)
                .load(currentUser.imgUrl)
                .centerCrop()
                .into(holder.image)
        } else {
            Glide.with(holder.image.context)
                .load(getLinkImageInFirebaseDatabase)
                .centerCrop()
                .into(holder.image)
        }

        holder.txtName.text = currentUser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val image: ImageView = itemView.findViewById(R.id.image_profile)
    }
}