package me.mickgian.common.base

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import me.mickgian.common.utils.Event
import me.mickgian.navigation.NavigationCommand
import java.util.*

abstract class BaseViewModel: ViewModel() {

    // FOR ERROR HANDLER
    protected val _snackbarError = MutableLiveData<Event<Int>>()
    val snackBarError: LiveData<Event<Int>> get() = _snackbarError

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    private val _closeWithBottomExitAnimation = MutableLiveData<Event<Long>>()
    val closeWithBottomExitAnimation: LiveData<Event<Long>> = _closeWithBottomExitAnimation

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
     fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }

    /**
     * navigation from a [ViewModel] when in a background [Thread] like [Runnable]
     * or [java.util.Timer]
     */
    fun navigateFromBackgroundThread(directions: NavDirections) {
        _navigation.postValue(Event(NavigationCommand.To(directions)))
    }

    /**
     * navigates back from a [ViewModel]
     */
    fun navigateBack(destinationId: Int) {
        _navigation.value = Event(NavigationCommand.BackTo(destinationId))
    }

    /**
     * navigating back from a [ViewModel]
     */
    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    /**
     *
     * navigates back from a [ViewModel] when in a background [Thread] like [Runnable]
     * or [java.util.Timer]
     */
    fun navigateBackFromBackgroundThread(destinationId: Int) {
        _navigation.postValue(Event(NavigationCommand.BackTo(destinationId)))
    }

    /**
     * animation of a [DialogFragment]. When closing it it will exit towards the bottom.
     * to be used only for [DialogFragment]
     */
    //setting the animation for closing a DialogFragment at the bottom
    fun bottomExitAnimation(animationDuration: Long) {
        _closeWithBottomExitAnimation.value = Event(animationDuration)
    }

    /**
     * Same as bottomExitAnimation() but from a background thread ([Runnable], [Thread], [Timer])
     */
    //setting the animation for closing a DialogFragment at the bottom
    fun bottomExitAnimationFromBackgroundThread(animationDuration: Long) {
        _closeWithBottomExitAnimation.postValue(Event(animationDuration))
    }
}