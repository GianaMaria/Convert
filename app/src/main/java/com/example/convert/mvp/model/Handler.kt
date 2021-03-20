package com.example.convert.mvp.model

import android.R.attr
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.example.convert.App
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Completable
import java.io.*


class Handler {
    private val context = App().getAppContext()
    private var name: String? = null

    var picture: Picture? = null
        private set

    fun savePNG(image: String, count: Int): Completable =
        Completable.create { emitter ->
            try {
                val uri = image.toUri()
                val bitmap = Bitmap
                    .createBitmap(Picasso.get().load(uri).get())
                val stream = ByteArrayOutputStream()

                Log.v("save", "bitmap.toDrawable(R.drawable.\"picture${count}.png\")")
                name = "picture${count}.png"
                context?.openFileOutput(name, Context.MODE_PRIVATE)
                    .use {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        picture = Picture(bitmap)
                        it?.write(stream.toByteArray())
                    }
                emitter.onComplete()
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
}