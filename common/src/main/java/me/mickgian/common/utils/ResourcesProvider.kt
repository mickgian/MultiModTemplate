package me.mickgian.common.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import org.koin.core.KoinComponent
import java.io.InputStream

/**
 * This class serves as a Provider for Strings, Drawables and colors for ViewModels.
 * This approach allows to not reference Context into ViewModels to avoid memory leaks.
 * The implementation of the ResourcesProvider will contain a reference to the Context but
 * the ViewModel will only refer to the ResourcesProvider.
 */

class ResourcesProvider (ctx: Context): KoinComponent {

    val context = ctx

    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }

    fun getDrawable(@DrawableRes drawableResource: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableResource)
    }

    fun getColor(@ColorRes colorResource: Int): Int? {
        return ContextCompat.getColor(context, colorResource)
    }

    fun getRawResource(@RawRes rawResource: Int) : InputStream {
        return context.resources.openRawResource(rawResource)
    }
}