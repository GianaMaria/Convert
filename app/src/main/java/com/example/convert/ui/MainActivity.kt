package com.example.convert.ui

import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.example.convert.R
import com.example.convert.mvp.model.Handler
import com.example.convert.mvp.presenter.MainPresenter
import com.example.convert.mvp.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.io.File

const val REQUEST = 1

class MainActivity : MvpAppCompatActivity(), MainView {
    private var selectedImage: Uri? = null
    private var bitmapPNG: Bitmap? = null

    private val presenter: MainPresenter by moxyPresenter {
        MainPresenter(AndroidSchedulers.mainThread(), Handler())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST -> {
                selectedImage = data?.data
                presenter.update()
            }
        }
    }

    override fun init() {
        button_get_picture.setOnClickListener {
            val intent = Intent(ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST)
            presenter.countPicture++
        }

        image_view_jpg.setImageURI(selectedImage)
        presenter.savePNG(selectedImage.toString())

        button_convert.setOnClickListener {
            bitmapPNG = presenter.getPNG()?.bitmap
            presenter.update()
        }

        image_view_png.setImageBitmap(bitmapPNG)

        button_save.setOnClickListener {
            bitmapPNG?.let { it1 -> presenter.saveImage(it1)
            presenter.update()}
        }
    }
}
