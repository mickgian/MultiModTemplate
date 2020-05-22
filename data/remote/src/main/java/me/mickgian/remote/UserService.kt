package me.mickgian.remote

import kotlinx.coroutines.Deferred
import me.mickgian.model.ApiResult
import me.mickgian.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    fun fetchTopUsersAsync(@Query("q") query: String = "mick",
                           @Query("sort") sort: String = "followers"): Deferred<ApiResult<User>>

    @GET("users/{login}")
    fun fetchUserDetailsAsync(@Path("login") login: String): Deferred<User>

}