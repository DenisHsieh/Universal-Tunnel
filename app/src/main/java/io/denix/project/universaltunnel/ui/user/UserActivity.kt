package io.denix.project.universaltunnel.ui.user

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.common.SharedPrefsUtil
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.common.UtRoomDatabase
import io.denix.project.universaltunnel.ui.main.MainActivity
import io.denix.project.universaltunnel.databinding.ActivityUserBinding
import io.denix.project.universaltunnel.network.util.ConnectivityManagerNetworkMonitor
import kotlinx.coroutines.*

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var titleContent: TextView
    private lateinit var characterName: TextView

    private lateinit var imageViewHulk: ImageView
    private lateinit var imageViewIronMan: ImageView
    private lateinit var imageViewCaptain: ImageView
    private lateinit var imageViewAntMan: ImageView

    private lateinit var textViewTitle: TextView
    private lateinit var buttonReady: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: UserViewModel
    private lateinit var utRoomDatabase: UtRoomDatabase

    private var userId: Int? = null

    private var connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeUi()
        initializeViewModel()
        hideSystemBars()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        chooseCharacterAndReadyToGo()
        animateProgressBarAndShowCharacters()

        // 判斷是否有網路
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManagerNetworkMonitor = ConnectivityManagerNetworkMonitor(connectivityManager)

        lifecycleScope.launch {
            connectivityManagerNetworkMonitor!!.isOnline.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { isOnline ->
                when(isOnline) {
                    true -> {
                        Snackbar.make(binding.root, getString(R.string.network_status_online), Snackbar.LENGTH_LONG).show()
                    }
                    false -> {
                        Snackbar.make(binding.root, getString(R.string.network_status_offline), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.onViewReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManagerNetworkMonitor = null
    }

    private fun initializeUi() {
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        titleContent = binding.textViewTitle
        characterName = binding.textViewCharacterName

        imageViewHulk = binding.imageViewHulk
        imageViewIronMan = binding.imageViewIronman
        imageViewCaptain = binding.imageViewCaptain
        imageViewAntMan = binding.imageViewAntman

        textViewTitle = binding.textViewTitle
        textViewTitle.text = getString(R.string.choose_character)

        buttonReady = binding.buttonReady
        progressBar = binding.progressBar
    }

    private fun initializeViewModel() {
        utRoomDatabase = (application as UtApplication).database
        val userDao = utRoomDatabase.userDao()
        val loginDao = utRoomDatabase.loginDao()
        viewModel = UserViewModel(userDao, loginDao, this.assets)
    }

    private fun hideSystemBars() {
        // App bar = Action bar = Title bar
        supportActionBar?.hide()

        // Remove the status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            titleContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            titleContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    private fun chooseCharacterAndReadyToGo() {
        imageViewHulk.setOnClickListener {
            imageViewHulk.imageAlpha = 255
            imageViewIronMan.imageAlpha = 100
            imageViewCaptain.imageAlpha = 100
            imageViewAntMan.imageAlpha = 100

            characterName.text = getString(R.string.hulk)
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
            userId = 1
        }
        imageViewIronMan.setOnClickListener {
            imageViewHulk.imageAlpha = 100
            imageViewIronMan.imageAlpha = 255
            imageViewCaptain.imageAlpha = 100
            imageViewAntMan.imageAlpha = 100

            characterName.text = getString(R.string.iron_man)
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
            userId = 2
        }
        imageViewCaptain.setOnClickListener {
            imageViewHulk.imageAlpha = 100
            imageViewIronMan.imageAlpha = 100
            imageViewCaptain.imageAlpha = 255
            imageViewAntMan.imageAlpha = 100

            characterName.text = getString(R.string.captain_america)
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
            userId = 3
        }
        imageViewAntMan.setOnClickListener {
            imageViewHulk.imageAlpha = 100
            imageViewIronMan.imageAlpha = 100
            imageViewCaptain.imageAlpha = 100
            imageViewAntMan.imageAlpha = 255

            characterName.text = getString(R.string.ant_man)
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
            userId = 4
        }

        buttonReady.setOnClickListener {
            // 使用者登入
            if (userId == null || userId == 0) {
                Snackbar.make(binding.root, getString(R.string.please_choose_character), Snackbar.LENGTH_LONG).show()
            } else {
                viewModel.userLogin(userId!!)

                val sharedPrefsUtil = SharedPrefsUtil()
                sharedPrefsUtil.setLoginUserId(this, userId!!)

                // 進入主畫面
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun animateProgressBarAndShowCharacters() {
        lifecycleScope.launch {
            viewModel.progressBarFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { progressStatus ->
                progressBar.progress = progressStatus

                if (progressStatus == 100) {
                    delay(500)
                    showCharacters()
                }
            }
        }
    }

    private fun showCharacters() {
        runOnUiThread(Runnable {
            progressBar.visibility = View.INVISIBLE
            imageViewHulk.visibility = View.VISIBLE
            imageViewIronMan.visibility = View.VISIBLE
            imageViewCaptain.visibility = View.VISIBLE
            imageViewAntMan.visibility = View.VISIBLE
        })
    }
}