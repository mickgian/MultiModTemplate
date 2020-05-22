package me.mickgian.common.extension

import android.content.res.Resources
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import me.mickgian.common.R
import me.mickgian.common.utils.Event

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(snackbarText: String, timeLength: Int) {
    activity?.let { Snackbar.make(it.findViewById<View>(android.R.id.content), snackbarText, timeLength).show() }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun Fragment.setupSnackbar(lifecycleOwner: LifecycleOwner, snackbarEvent: LiveData<Event<Int>>, timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showSnackbar(it.getString(res), timeLength) }
        }
    })
}

/**
 * Animation: DialogFragment Enter from bottom
 *
 * ----- How to use it -----
 * In your [DialogFragment] that extends [BaseDialogFragment], after binding has been
 * completed in the fun onCreateView, override the fun onActivityCreated and add:
 * 'binding.root.enterFromBottom(animationDuration)' where animationDuration is a [Long]
 * -------------------------
 */
fun View.enterFromBottom(duration: Long): ViewPropertyAnimator? {
    val heightPixels = Resources.getSystem().displayMetrics.heightPixels    // get device height

    val y = this.y    // store initial y
    this.y = heightPixels.toFloat()   // move to bottom

    return animate()
        .y(y)
        .setDuration(duration)
}

/**
 * Animation: DialogFragment Exit to bottom
 *
 * ----- Premise -----
 * You'll use this animation when you want to close a [DialogFragment] to the bottom.
 * This implies that after closing has happened, you need to navigate (using Navigation Architecture component)
 * somewhere else. But the navigation usually happens instantly, so we need a way to delay the navigation till
 * the animation has ended.
 * -------------------
 *
 * ----- How to use it ---------------------------------
 * In your [ViewModel] that extends [BaseViewModel] you need a function, bound to the xml file (to the closing icon image
 * or where you want the user should click to close the [DialogFragment]. Something like:
 *
 *  fun onClosingDialog(view: View){
 *      bottomExitAnimation(animationDuration)    -------> will trigger the animation passing the duration as parameter
 *      Timer("waitAnimationEnd", false).schedule(animationDuration){   -------> will delay the navigation of the same amount of time of the animation duration
 *          navigateToProductsAndBundles()  ----------> now that animation has completed we can navigate where we need to
 *      }
 *  }
 *
 *  private fun navigateToProductsAndBundles(){
 *      navigateFromBackgroundThread(ManageBundleDialogFragmentDirections.actionManageBundleFragmentToProductFragment())  ------> here we are in a background thread as we are using [java.util.Timer]
 *  }
 *
 * ----------------------------------------------------
 *
 */
fun View.exitToBottom(duration: Long): ViewPropertyAnimator? {
    val heightPixels = Resources.getSystem().displayMetrics.heightPixels    // get device height

    return animate()
        .y(heightPixels.toFloat())
        .setDuration(duration)
}


/**
 * Fullscreen DialogFragment
 *
 * ----- Premise -----
 * [DialogFragment], in Android applications, will always leave an empty space on all sides.
 * If we want to remove that empty space to allow our [DialogFragment] to fill the whole screen, we need to call, from
 * our DialogFragment implementation instance (that MUST extend [BaseDialogFragment]), 2 different Extension Functions
 * defined in this file: setFullscreenStyle() and applyFullscreenLayout().
 *
 *  * ----- How to use it ---------------------------------
 *  1 - First, we override onCreate() and we call setFullscreenStyle() from it.
 *  That function will change the default style of the [DialogFragment]:
 *
 *          override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setFullscreenStyle()
}

 *  2 - Second, in the onCreateView(), just before returning the bound view, we call applyFullscreenLayout():
 *
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
super.onCreateView(inflater, container, savedInstanceState)

binding = FragmentDialogManageBundleBinding.inflate(inflater, container,false)
binding.viewModel = viewModel
binding.lifecycleOwner = viewLifecycleOwner

viewModel.initManageBundleDialogView(args.subscriberID)

//making the DialogFragment fullscreen
applyFullscreenLayout()

return binding?.root
}
 *
 * ---------------------------------------------------------
 */

/**
 * This function apply a fullscreen style to the [DialogFragment]. Check comment above on how to use it.
 */
fun DialogFragment.setFullscreenStyle() {
    setStyle(DialogFragment.STYLE_NORMAL, R.style.ModalFullscreenDialog)
}

/**
 * This function makes the [DialogFragment] layout width fullscreen. Check comment above on how to use it.
 */
inline fun DialogFragment.applyFullWidthLayout() {
    dialog?.window?.apply {
        //keeping the DialogFragment fullscreen
        decorView.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //setting dialog sizes
        }
    }
}

/**
 * This function makes the [DialogFragment] layout fullscreen. Check comment above on how to use it.
 */
fun DialogFragment.applyFullscreenLayout() {
    dialog?.window?.apply {
        //keeping the DialogFragment fullscreen
        decorView.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) //setting dialog sizes
        }
    }
}

fun DialogFragment.alignToBottom() {
    dialog?.window?.apply {
        setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
        decorView.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //setting dialog sizes
        }
    }
}