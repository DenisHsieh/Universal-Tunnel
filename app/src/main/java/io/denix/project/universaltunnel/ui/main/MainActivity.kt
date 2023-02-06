package io.denix.project.universaltunnel.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.common.ScreenSizeUtil
import io.denix.project.universaltunnel.common.SharedPrefsUtil
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.common.UtRoomDatabase
import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.databinding.ActivityMainBinding
import io.denix.project.universaltunnel.databinding.NavHeaderMainBinding
import io.denix.project.universaltunnel.ui.user.UserActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    private lateinit var utRoomDatabase: UtRoomDatabase
    private lateinit var viewModel: MainViewModel

    private var userId: Int = 0

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "FCM can't post notifications without POST_NOTIFICATIONS permission",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun subscribeToFCMTopic() {
        Firebase.messaging.subscribeToTopic("Ut")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchFCMRegistrationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeUi()
        userId = intent.getIntExtra("userId", 0)

        val screenSizeInfo = ScreenSizeUtil.calculateScreenSize(this.resources)
        Log.d("screenSizeInfo", "${screenSizeInfo.widthDp}, ${screenSizeInfo.heightDp}")
        Log.d("screenSizeInfo", "${screenSizeInfo.widthPixels}, ${screenSizeInfo.heightPixels}")

        subscribeToFCMTopic()
        fetchFCMRegistrationToken()

        askNotificationPermission()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        initializeViewModel()
    }

    override fun onResume() {
        super.onResume()

        // 更新 navHeader 內的使用者名稱 & mail
        showUserInfo()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_change_character -> {
                val builder = AlertDialog.Builder(this)
                    .setMessage(getString(R.string.choose_character_again))
                    .setPositiveButton(getString(R.string.sure)) { _, _ -> reChooseCharacter() }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                builder.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeUi() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_health, R.id.nav_finance, R.id.nav_media, R.id.nav_note
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navHeaderMainBinding = NavHeaderMainBinding.bind(navView.getHeaderView(0))
    }

    private fun initializeViewModel() {
        utRoomDatabase = (application as UtApplication).database
        val loginDao = utRoomDatabase.loginDao()
        val userDao = utRoomDatabase.userDao()

        viewModel = MainViewModel(loginDao, userDao)
    }

    private fun showUserInfo() {
        var user: User
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                user = if (userId == 0) {
                    // 取得 login data，提取登入 user 資訊
                    viewModel.getUserFromLoginData()
                } else {
                    viewModel.getUserData(userId)
                }
            }
            withContext(Dispatchers.Main) {
                navHeaderMainBinding.textViewUserName.text = "${user.firstName} ${user.lastName}"
                navHeaderMainBinding.textViewUserMail.text = "${user.firstName}@tunnel.com"
            }
        }
    }

    private fun reChooseCharacter() {
        // 登出 & 清除資料
        viewModel.logout(userId)
        viewModel.cleanUpLoginData()

        val sharedPrefsUtil = SharedPrefsUtil()
        sharedPrefsUtil.removeLoginUserId(this)

        // 導回 userActivity
        val userIntent = Intent(this, UserActivity::class.java)
        startActivity(userIntent)
        finish()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}