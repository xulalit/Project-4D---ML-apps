package com.codemobiles.sneakerrecognition

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import pl.aprilapps.easyphotopicker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadImage : AppCompatActivity() {

    lateinit var mEasyImage: EasyImage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        checkRuntimePermission()

    }


    private fun checkRuntimePermission() {

        val _permissions =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        Dexter.withActivity(this)
            .withPermissions(*_permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            getApplicationContext(),
                            "Permission Granted",
                            Toast.LENGTH_LONG
                        ).show()

                        mEasyImage = EasyImage.Builder(applicationContext)
                            .setCopyImagesToPublicGalleryFolder(true)
                            .setFolderName("EasyImage")
                            .allowMultiple(true)
                            .build()
                    } else {
                        finish()
                    }
                }


            })
            .check()
    }

    fun onClickCamera(view: View) {

        mEasyImage.openCameraForImage(this)
    }

    fun onClickGallery(view: View) {
        mEasyImage.openGallery(this)
    }
}