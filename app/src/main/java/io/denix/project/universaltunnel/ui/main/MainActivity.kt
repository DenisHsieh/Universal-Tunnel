package io.denix.project.universaltunnel.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.common.SharedPrefsUtil
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.common.UtRoomDatabase
import io.denix.project.universaltunnel.databinding.ActivityMainBinding
import io.denix.project.universaltunnel.databinding.NavHeaderMainBinding
import io.denix.project.universaltunnel.ui.user.UserActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    private lateinit var utRoomDatabase: UtRoomDatabase
    private lateinit var viewModel: MainViewModel

    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeUi()
        userId = intent.getIntExtra("userId", 0)
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
        if (userId == 0) {
            // 取得 login data，提取登入 user 資訊
            GlobalScope.launch(Dispatchers.IO) {
                val user = viewModel.getUserFromLoginData()
                navHeaderMainBinding.textViewUserName.text = "${user.firstName} ${user.lastName}"
                navHeaderMainBinding.textViewUserMail.text = "${user.firstName}@tunnel.com"
            }
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                val user = viewModel.getUserData(userId)
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
}