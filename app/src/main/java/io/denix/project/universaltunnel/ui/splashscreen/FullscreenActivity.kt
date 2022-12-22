package io.denix.project.universaltunnel.ui.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.widget.TextView
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.common.UtRoomDatabase
import io.denix.project.universaltunnel.databinding.ActivityFullscreenBinding
import io.denix.project.universaltunnel.ui.main.MainActivity
import io.denix.project.universaltunnel.ui.user.UserActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FullscreenActivity : AppCompatActivity() {
    companion object {
        private const val DELAY = 1500
    }

    private lateinit var binding: ActivityFullscreenBinding
    private lateinit var fullscreenContent: TextView

    private val hideHandler = Handler(Looper.myLooper()!!)
    private val hideRunnable = Runnable { hideSystemBars() }

    private lateinit var utRoomDatabase: UtRoomDatabase
    private lateinit var viewModel: FullscreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fullscreenContent = binding.fullscreenContent

        // initialize Database and ViewModel
        utRoomDatabase = (application as UtApplication).database
        viewModel = FullscreenViewModel(utRoomDatabase.loginDao())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, 0)
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.IO) {
            delay(DELAY.toLong())
            // 檢查是否有角色登入
            if(viewModel.checkLoginStatus()) {
                // 有，導入主頁面
                goToMainPage()
            } else {
                // 沒有，導入角色選擇頁面
                goToUserPage()
            }
            finish()
        }
    }

    private fun goToUserPage() {
        val userIntent = Intent(this, UserActivity::class.java)
        startActivity(userIntent)
    }

    private fun goToMainPage() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    private fun hideSystemBars() {
        // App bar = Action bar = Title bar
        supportActionBar?.hide()

        // Remove the status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            fullscreenContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            fullscreenContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}