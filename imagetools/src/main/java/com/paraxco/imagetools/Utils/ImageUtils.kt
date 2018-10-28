package com.paraxco.imagetools.Utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import com.paraxco.imagetools.blurbehind.util.Blur
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by Amin on 11/6/2017.
 */

object ImageUtils {
    //    public static void loadCircleWithGlide(final Context context, String url, final ImageView imageView) {
    //        Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
    //            @Override
    //            protected void setResource(Bitmap resource) {
    //                RoundedBitmapDrawable circularBitmapDrawable =
    //                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
    //                circularBitmapDrawable.setCircular(true);
    //                imageView.setImageDrawable(circularBitmapDrawable);
    //            }
    //
    //            @Override
    //            public void onLoadFailed(Exception e, Drawable errorDrawable) {
    //                RoundedBitmapDrawable circularBitmapDrawable =
    //                        RoundedBitmapDrawableFactory.create(context.getResources(), getdefaultProfile(context));
    //                circularBitmapDrawable.setCircular(true);
    //                imageView.setImageDrawable(circularBitmapDrawable);
    //            }
    //        });
    //    }

    private fun getdefaultProfile(context: Context): Bitmap? {
        return null
        //        return getBitmapFromVectorDrawable(context,  R.drawable.default_profile);
    }

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableId)

        return getBitmap(drawable)
    }

    fun getBitmap(drawable: Drawable?): Bitmap {
        var drawable = drawable
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }

        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    //    public static void loadWithGlide(final Context context, String url, final ImageView imageView) {
    //        Glide.with(context).load(url)
    //                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
    ////                .placeholder(R.drawable.logo)
    //                .into(imageView);
    //    }
    //
    //    public static void loadWithGlide(final Context context, String url, int width, int height, final ImageView imageView) {
    //        Glide.with(context).load(url)
    //                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
    ////                .placeholder(R.drawable.logo)
    //                .override(width, height)
    //                .into(imageView);
    //    }

    fun takeScreenShot(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val b1 = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = activity.windowManager.defaultDisplay.width
        val height = activity.windowManager.defaultDisplay.height

        val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return b
    }
    fun blurView(BackgroundActivity: Activity, view: View) {
        doAsync {
            val map = ImageUtils.takeScreenShot(BackgroundActivity)

            val fastBitmap = Blur.apply(BackgroundActivity, map)

            val draw = BitmapDrawable(BackgroundActivity.getResources(), fastBitmap)
            uiThread {
                view.background = draw

            }
        }
    }

}
