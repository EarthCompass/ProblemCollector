package com.example.administrator.a1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.view.Surface
import android.view.TextureView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_camera.*
import android.hardware.camera2.CameraDevice
import android.support.annotation.NonNull
import android.hardware.camera2.CameraAccessException


class Camera : AppCompatActivity(), TextureView.SurfaceTextureListener {
    var camera: CameraDevice? = null
    var mtext: SurfaceTexture? = null
    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        mtext = surface
        wsw()
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        Toast.makeText(applicationContext, "SurfaceTextureUpdated", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        Toast.makeText(applicationContext, "SurfaceTextureUpdated", Toast.LENGTH_SHORT).show()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(cameraDevice: CameraDevice) {
            camera = cameraDevice
            val cHandler: Handler? = null
            findViewById<TextureView>(R.id.textureView).surfaceTextureListener = this@Camera
            //val texture = findViewById<TextureView>(R.id.textureView).surfaceTexture
            val texture = mtext
            texture?.setDefaultBufferSize(480, 320)

            val surface = Surface(texture)
            //val surfaceholder = surfaceView.holder
            val mPreviewRequestBuilder = camera?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW) as CaptureRequest.Builder
            mPreviewRequestBuilder!!.addTarget(surface)
            camera?.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(cameraCaptureSession: CameraCaptureSession?) {
                    val mCaptureSession: CameraCaptureSession? = cameraCaptureSession
                    val mPreviewRequest = mPreviewRequestBuilder!!.build()
                    mCaptureSession!!.setRepeatingRequest(mPreviewRequest, captureCallback, cHandler)
                }

                override fun onConfigureFailed(p0: CameraCaptureSession?) {
                    Toast.makeText(applicationContext, "ConfigureFailed", Toast.LENGTH_SHORT).show()
                }
            }, cHandler)
        }

        override fun onError(p0: CameraDevice?, p1: Int) {
            Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
        }

        override fun onDisconnected(p0: CameraDevice?) {
            Toast.makeText(applicationContext, "dis", Toast.LENGTH_SHORT).show()
        }

        override fun onClosed(camera: CameraDevice?) {
            super.onClosed(camera)
        }
    }


    private val captureCallback = object : CameraCaptureSession.CaptureCallback() {
        override fun onCaptureProgressed(session: CameraCaptureSession?, request: CaptureRequest?, partialResult: CaptureResult?) {
            super.onCaptureProgressed(session, request, partialResult)
        }
    }


    fun wsw() {
        val cHandler: Handler? = null
        val Manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Manager.openCamera(Manager.cameraIdList[0], stateCallback, cHandler)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        requestPermissions(arrayOf(Manifest.permission.CAMERA), 202)
        textureView.surfaceTextureListener = this
    }

}
