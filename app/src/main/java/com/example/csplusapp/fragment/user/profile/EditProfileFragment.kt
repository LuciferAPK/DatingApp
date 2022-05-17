package com.example.csplusapp.fragment.user.profile

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
import com.example.csplusapp.databinding.FragmentEditProfileBinding
import com.example.csplusapp.fragment.user.UserFragment
import java.io.ByteArrayOutputStream

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private var selectedPhotoUri: Uri? = null
    private val imagePickerFragment by lazy {
        ImagePickerFragment(
            onCameraClicked = { clickCamera() },
            onLibraryClicked = { clickLibrary() })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_profile,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedCamera()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UserFragment.Camera_Code) {
            val pic = data?.getParcelableExtra<Bitmap>("data")
            if (pic != null) {
                binding.img1.setImageURI(getImageUriFromBitmap(this.requireContext(), pic))
            }
        }

        if (requestCode == UserFragment.Library_Code) {
            selectedPhotoUri = data?.data
            if (selectedPhotoUri != null) {
                binding.img1.setImageURI(selectedPhotoUri)
            }
        }
    }

    private fun selectedCamera() {
        binding.add1.setOnClickListener {
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
        startActivityForResult(intent, UserFragment.Camera_Code)
        imagePickerFragment.dismiss()
    }

    private fun clickLibrary() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, UserFragment.Library_Code)
        imagePickerFragment.dismiss()
    }

    //convert Bitmap to Uri
    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }
}