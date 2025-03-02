package me.mickgian.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.mickgian.common.base.BaseViewModel
import me.mickgian.common.utils.Event
import me.mickgian.detail.domain.GetUserDetailUseCase
import me.mickgian.model.User
import me.mickgian.repository.AppDispatchers
import me.mickgian.repository.utils.Resource

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [DetailFragment].
 */
class DetailViewModel(private val getUserDetailUseCase: GetUserDetailUseCase,
                      private val dispatchers: AppDispatchers
): BaseViewModel() {

    // PRIVATE DATA
    private lateinit var argsLogin: String
    private var userSource: LiveData<Resource<User>> = MutableLiveData()

    private val _user = MediatorLiveData<User>()
    val user: LiveData<User> get() = _user
    private val _isLoading = MutableLiveData<Resource.Status>()
    val isLoading: LiveData<Resource.Status> get() = _isLoading

    // PUBLIC ACTIONS ---
    fun loadDataWhenActivityStarts(login: String) {
        argsLogin = login
        getUserDetail(false)
    }

    fun reloadDataWhenUserRefreshes()
            = getUserDetail(true)

    fun userClicksOnAvatarImage(user: User)
            = navigate(DetailFragmentDirections.actionDetailFragmentToImageDetailFragment(user.avatarUrl))

    // ---

    private fun getUserDetail(forceRefresh: Boolean) = viewModelScope.launch(dispatchers.main) {
        _user.removeSource(userSource) // We make sure there is only one source of livedata (allowing us properly refresh)
        withContext(dispatchers.io) { userSource = getUserDetailUseCase(forceRefresh = forceRefresh, login = argsLogin) }
        _user.addSource(userSource) {
            _user.value = it.data
            _isLoading.value = it.status
            if (it.status == Resource.Status.ERROR) _snackbarError.value = Event(R.string.an_error_happened)
        }
    }
}