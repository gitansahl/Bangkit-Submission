package com.gitan.mystoryapp.view.add_story

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.gitan.mystoryapp.data.UserPreference
import com.gitan.mystoryapp.data.response.GeneralResponse
import com.gitan.mystoryapp.data.retrofit.ApiConfig
import com.gitan.mystoryapp.databinding.ActivityAddStoryBinding
import com.gitan.mystoryapp.uriToFile
import com.gitan.mystoryapp.view.ViewModelFactory
import com.gitan.mystoryapp.view.camera.CameraActivity
import com.gitan.mystoryapp.view.main.dataStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class AddStoryActivity : AppCompatActivity() {
    private var getFile: File? = null
    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var addStoryViewModel: AddStoryViewModel

    companion object {
        const val CAMERA_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val REQUEST_CODE_PERMISSIONS = 10
        private const val MAXIMAL_SIZE = 1000000
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupAction()
        setupViewModel()
    }

    private fun setupAction() {
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.buttonAdd.setOnClickListener { uploadStory() }
    }

    private fun setupViewModel() {
        addStoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this@AddStoryActivity)
        )[AddStoryViewModel::class.java]
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcerIntentCamera.launch(intent)
    }

    private val launcerIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECIATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun rotateFile(file: File, isBackCamera: Boolean = false) {
        val matrix = Matrix()
        val bitmap = BitmapFactory.decodeFile(file.path)
        val rotation = if (isBackCamera) 90f else -90f
        matrix.postRotate(rotation)
        if (!isBackCamera) {
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        }
        val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddStoryActivity)
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

    private fun uploadStory() {
        val description = binding.edAddDescription.text.toString()

        if (getFile == null) {
            Toast.makeText(this@AddStoryActivity, "Masukkan gambar", Toast.LENGTH_SHORT).show()
        } else if (description.isEmpty()) {
            binding.edAddDescription.error = "Masukkan deskripsi"
        } else {
            showLoading(true)
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val apiService = ApiConfig.getApiService()
            val token = addStoryViewModel.getToken()
            val uploadStoryRequest = apiService.uploadStory(
                "Bearer $token",
                imageMultipart,
                description.toRequestBody("text/plain".toMediaType())
            )
            uploadStoryRequest.enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    showLoading(false)
                    if (response.body() != null && response.body()?.error == false) {
                        Toast.makeText(this@AddStoryActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddStoryActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(this@AddStoryActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun reduceFileImage(file: File) : File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > MAXIMAL_SIZE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    private fun showLoading (visible: Boolean) {
        binding.pbCircular.visibility = if (visible) View.VISIBLE else View.GONE
    }
}