package kr.puze.onlinerecruit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kr.puze.onlinerecruit.Adapter.RecyclerViewAdapter
import kr.puze.onlinerecruit.Data.Repo
import kr.puze.onlinerecruit.Data.User
import kr.puze.onlinerecruit.Server.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var retrofitService: RetrofitService
        lateinit var progressDialog: ProgressDialog
        lateinit var mAdapter: RecyclerViewAdapter
        lateinit var userCall: Call<User>
        lateinit var repoCall: Call<Repo>
        lateinit var path: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitSetting()
        setView()
    }

    private fun setView(){
        mAdapter = RecyclerViewAdapter()
        mAdapter.RecyclerViewAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        main_recycler_view.adapter = mAdapter
        main_recycler_view.setHasFixedSize(true)
        main_recycler_view.layoutManager = layoutManager

        userCall = retrofitService.get_user(path)
        userCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                progressDialog.dismiss()
                if (response?.code() == 200) {
                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(this@MainActivity, "유저 로딩 성공 : " + response!!.code().toString(), Toast.LENGTH_LONG).show()
                        var owner_image = user.user_image
                        var owner_name = user.user_name
                    }
                } else {
                    Toast.makeText(this@MainActivity, "유저 로딩 실패 : " + response!!.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "서버 연동 실패", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setProgressDialog(text: String) {
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage(text)
        progressDialog.show()
    }

    private fun checkNetwork(): Boolean {
        val cm = this@MainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork.isConnectedOrConnecting
    }

    private fun retrofitSetting() {
        path = intent.data.path
        val gson: Gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }
}
