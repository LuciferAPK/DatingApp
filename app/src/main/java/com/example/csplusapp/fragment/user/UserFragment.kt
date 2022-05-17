package com.example.csplusapp.fragment.user

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.csplusapp.R
import com.example.csplusapp.databinding.FragmentUserBinding
import com.example.csplusapp.fragment.user.profile.ImagePickerFragment
import com.example.csplusapp.fragment.user.profile.firebaseimg.GetImgFrDtb
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

val userArrayList = arrayOf(
    "Edit Profile",
    "Show Profile"
)

class UserFragment : Fragment() {
    companion object {
        const val Camera_Code = 123
        const val Library_Code = 456
    }

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val getImgFrDtb by lazy {
        GetImgFrDtb()
    }

    private val imagePickerFragment by lazy {
        ImagePickerFragment(
            onCameraClicked = { clickCamera() },
            onLibraryClicked = { clickLibrary() })
    }
    private lateinit var getNameFromFirebase: String
    private lateinit var binding: FragmentUserBinding
    private var selectedPhotoUri: Uri? = null
    private val mAdapter by lazy {
        activity?.let { Adapter(it.supportFragmentManager, lifecycle) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false
        )
        binding.viewPager2Profile.adapter = mAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2Profile) { tab, position ->
            tab.text = userArrayList[position]
        }.attach()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getName = FirebaseDatabase.getInstance().reference.child("user/${mAuth.currentUser?.uid}/name")
        getName.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val getNameToString: String = snapshot.value.toString()
                getNameFromFirebase = getNameToString
                binding.nameProfile.text = getNameToString
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedCamera()
        getImgFrDtb.getImageFromFirebaseDatabase(binding.imageAvatar, requireContext())
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Camera_Code) {
            val pic = data?.getParcelableExtra<Bitmap>("data")
            if (pic != null) {
                binding.imageAvatar.setImageURI(getImageUriFromBitmap(this.requireContext(), pic))
            }
            uploadImage(getImageUriFromBitmap(requireContext(), pic!!))
        }

        if (requestCode == Library_Code) {
            selectedPhotoUri = data?.data
            if (selectedPhotoUri != null) {
                binding.imageAvatar.setImageURI(selectedPhotoUri)
            }
            uploadImage(selectedPhotoUri!!)
        }
    }

    private fun uploadImage(uri: Uri) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().reference.child("/images/$filename")
        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                updateUser(it)
            }
        }.addOnFailureListener {

        }
    }

    private fun updateUser(image: Uri) {
        val uid = FirebaseAuth.getInstance().uid
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        val ref = FirebaseDatabase.getInstance().getReference("/user")
        val update = mapOf<String, String>(
            "name" to getNameFromFirebase,
            "email" to email,
            "imgUrl" to image.toString(),
            "uid" to uid.toString()
        )
        ref.child(uid!!).updateChildren(update).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    //convert Bitmap to Uri
    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    private fun selectedCamera() {
        binding.camera.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 2507
            )
            imagePickerFragment.show(parentFragmentManager, null)
        }
    }

    private fun clickCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Camera_Code)
        imagePickerFragment.dismiss()
    }

    private fun clickLibrary() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Library_Code)
        imagePickerFragment.dismiss()
    }
}