package me.mickgian.remote

import kotlinx.coroutines.runBlocking
import me.mickgian.remote.base.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

class UserServiceTest: BaseTest() {

    @Test
    fun `search top users by name`() {
        mockHttpResponse(mockServer, "search_users.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val users = userService.fetchTopUsersAsync().await()
            assertEquals(1, users.items.size)
            assertEquals("", users.items.first().id)
            assertEquals("", users.items.first().login)
            assertEquals("", users.items.first().avatarUrl)
        }
    }

    @Test(expected = HttpException::class)
    fun `search top users by name and fail`() {
        mockHttpResponse(mockServer,"search_users.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            userService.fetchTopUsersAsync().await()
        }
    }

    // ---

    @Test
    fun `fetch user's detail`() {
        mockHttpResponse(mockServer,"user_detail.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val user = userService.fetchUserDetailsAsync("").await()
            assertEquals("", user.id)
            assertEquals("", user.login)
            assertEquals("", user.avatarUrl)
            assertEquals("", user.name)
            assertEquals("", user.blog)
            assertEquals("", user.company)
        }
    }

    @Test(expected = HttpException::class)
    fun `fetch user's detail and fail`() {
        mockHttpResponse(mockServer,"user_detail.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            userService.fetchUserDetailsAsync("").await()
        }
    }
}