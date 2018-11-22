package kr.puze.onlinerecruit.Server

import kr.puze.onlinerecruit.Data.Repo
import kr.puze.onlinerecruit.Data.User
import retrofit2.http.*

interface RetrofitService {
    @GET("/user/{username}")
    fun get_user(
            @Path("username") username: String
    ): retrofit2.Call<User>

    @GET("/user/{username}/repos")
    fun get_repo(
            @Path("username") username: String
    ): retrofit2.Call<Repo>
}