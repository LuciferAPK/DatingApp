package com.example.csplusapp.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.csplusapp.R
import com.example.csplusapp.databinding.ActivitySignUpBinding
import com.example.csplusapp.fragment.user.User
import com.example.csplusapp.fragment.user.profile.ImagePickerFragment
import com.example.csplusapp.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class SignUpActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "EmailPassword"
        const val Camera_Code = 123
        const val Library_Code = 456
    }

    private val imagePickerFragment by lazy {
        ImagePickerFragment(
            onCameraClicked = { clickCamera() },
            onLibraryClicked = { clickLibrary() })
    }
    private var count = 0
    private var imageCheck: Uri? = null
    private var selectedPhotoUri: Uri? = null
    private lateinit var binding: ActivitySignUpBinding
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val  progressDialog by lazy {
        ProgressDialog(this)
    }
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        clickSignUp()
        clickCancel()
        selectedCamera()
    }

    private fun clickSignUp() {
        binding.btnSignup.setOnClickListener {
            if (binding.suEmail.text?.trim().toString().isNotEmpty()
                && binding.suPassword.text?.trim().toString().isNotEmpty()
                && binding.suCfpassword.text?.trim().toString().isNotEmpty()
            ) {
                if (!isEmailValid(binding.suEmail.text?.trim().toString())) {
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                } else {
                    if (binding.suPassword.text?.trim()
                            .toString() != binding.suCfpassword.text?.trim().toString()
                    ) {
                        Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                    } else {
                        if (binding.suPassword.text?.trim().toString().length <= 5
                            || binding.suCfpassword.text?.trim().toString().length <= 5
                        ) {
                            Toast.makeText(
                                this, "Password must have at least 6 characters", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if(count == 0) {
                                createAccount(
                                    binding.userName.text?.trim().toString(),
                                    binding.suEmail.text?.trim().toString(),
                                    binding.suPassword.text?.trim().toString(),
                                    ""
                                )
                            } else {
                                createAccount(
                                    binding.userName.text?.trim().toString(),
                                    binding.suEmail.text?.trim().toString(),
                                    binding.suPassword.text?.trim().toString(),
                                    imageCheck.toString()
                                )
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all information!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String, imgUrl: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name, email, uid, imgUrl))
    }

    private fun createAccount(name: String, email: String, password: String, imgUrl: String) {
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name, email, mAuth.uid!!, imgUrl)
                    val intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("user_name", binding.userName.text?.trim().toString())
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss()
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    val check = task.exception is FirebaseAuthUserCollisionException
                    if (check) {
                        Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            baseContext, "Xác thực thất bại",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Camera_Code) {
            val pic = data?.getParcelableExtra<Bitmap>("data")
            if (pic != null) {
                binding.imageProfile.setImageURI(getImageUriFromBitmap(this, pic))
            }
            uploadImage(getImageUriFromBitmap(this, pic!!))
        }

        if (requestCode == Library_Code) {
            selectedPhotoUri = data?.data
            if (selectedPhotoUri != null) {
                binding.imageProfile.setImageURI(selectedPhotoUri)
            }
            uploadImage(selectedPhotoUri!!)
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

    private fun selectedCamera() {
        binding.camera.setOnClickListener {
            count++
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 2507
            )
            imagePickerFragment.show(supportFragmentManager, null)
        }
    }

    private fun uploadImage(uri: Uri) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().reference.child("/images/$filename")
        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                imageCheck = it
            }
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

    private fun clickCancel() {
        binding.tvCancle.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    //check mail
    private fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
    }
}