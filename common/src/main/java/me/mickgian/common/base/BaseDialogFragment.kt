package me.mickgian.common.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import me.mickgian.common.extension.exitToBottom
import me.mickgian.common.extension.setupSnackbar
import me.mickgian.navigation.NavigationCommand

/**
 * A top-level DialogFragment class that extends Android [DialogFragment].
 * All DialogFragment we want to navigate to MUST implement this class.
 * It has a lifecycle-aware Observer that observes the navigation property of the [BaseViewModel].
 * As the 'navigation' property is a LiveData, we don't need Interfaces to let the
 * BaseViewModel communicates with the BaseDialogFragment
 */
abstract class BaseDialogFragment: DialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeCloseWithBottomExitAnimation(getViewModel())
        observeNavigation(getViewModel())
        setupSnackbar(this, getViewModel().snackBarError, Snackbar.LENGTH_LONG)
    }

    /**Here, we tie the [BaseDialogFragment] to the [BaseViewModel]. We don't need a more general class/interface as
     * we're using [LiveData] into the [BaseViewModel] and those [LiveData], that are life-cycle aware,
     * are just being observed from the[BaseDialogFragment]
     */
    abstract fun getViewModel(): BaseViewModel

    // UTILS METHODS ---

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination
     */
    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions, getExtras())
                    is NavigationCommand.Back -> findNavController().navigateUp()
                    is NavigationCommand.BackTo -> findNavController().popBackStack(command.destinationId, false)
                    is NavigationCommand.ToRoot -> Unit
                }
            }
        })
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()


    /**
     * Observe a [Long] [Event] [LiveData].
     * When this [LiveData] is updated, [DialogFragment] will close to the bottom with a duration specified
     */
    private fun observeCloseWithBottomExitAnimation(viewModel: BaseViewModel){
        viewModel.closeWithBottomExitAnimation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let{ duration ->
                this.view?.exitToBottom(duration)
            }
        })
    }
}