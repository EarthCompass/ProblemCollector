package com.example.administrator.a1

import android.Manifest
import android.content.Context
import android.hardware.camera2.CameraManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_camera.*

class Camera : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val Manager = getSystemService(Context.CAMERA_SERVICE)
        requestPermissions(arrayOf(Manifest.permission.CAMERA),202)

    }

}
