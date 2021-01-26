package com.example.convert.mvp.presenter

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.example.convert.mvp.model.Handler
import com.example.convert.mvp.model.Picture
import com.example.convert.mvp.view.MainView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.io.File
import java.io.FileOutputStream

class MainPresenter(
    private val mainThread: Scheduler,
    private val pictureHandler: Handler
) : MvpPresenter<MainView>() {
    var countPicture = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun getPNG(): Picture? {
        return pictureHandler.picture
    }

    fun savePNG(uri: String) {
        pictureHandler.savePNG(uri, countPicture)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread)
            .subscribe({
            }, { e ->
                Log.v("savePNGPres", "savePNG(uri: String) $e")
            })
    }

    fun saveImage(bitmap: Bitmap) {
        try {
            val root = Environment.DIRECTORY_PICTURES
            val file = File("$root/YourDirectory/myImagesDGS.jpg")
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun update() {
        viewState.init()
    }
}