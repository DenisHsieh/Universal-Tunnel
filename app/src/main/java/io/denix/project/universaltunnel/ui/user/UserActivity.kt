package io.denix.project.universaltunnel.ui.user

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.denix.project.universaltunnel.ui.MainActivity
import io.denix.project.universaltunnel.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var titleContent: TextView
    private lateinit var characterName: TextView

    private lateinit var imageViewHulk: ImageView
    private lateinit var imageViewIronMan: ImageView
    private lateinit var imageViewCaptain: ImageView
    private lateinit var imageViewAntMan: ImageView

    private lateinit var buttonReady: Button

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        titleContent = binding.textViewTitle
        characterName = binding.textViewCharacterName

        imageViewHulk = binding.imageViewHulk
        imageViewIronMan = binding.imageViewIronman
        imageViewCaptain = binding.imageViewCaptain
        imageViewAntMan = binding.imageViewAntman

        buttonReady = binding.buttonReady

        viewModel = UserViewModel(this.assets)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        hide()
        chooseCharacterAndReadyToGo()
    }

    override fun onResume() {
        super.onResume()

        viewModel.onViewReady()
    }

    private fun chooseCharacterAndReadyToGo() {
        imageViewHulk.setOnClickListener {
            imageViewHulk.imageAlpha = 255
            imageViewIronMan.imageAlpha = 100
            imageViewCaptain.imageAlpha = 100
            imageViewAntMan.imageAlpha = 100

            characterName.text = "Bruce Banner"
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
        }
        imageViewIronMan.setOnClickListener {
            imageViewHulk.imageAlpha = 100
            imageViewIronMan.imageAlpha = 255
            imageViewCaptain.imageAlpha = 100
            imageViewAntMan.imageAlpha = 100

            characterName.text = "Tony Stark"
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
        }
        imageViewCaptain.setOnClickListener {
            imageViewHulk.imageAlpha = 100
            imageViewIronMan.imageAlpha = 100
            imageViewCaptain.imageAlpha = 255
            imageViewAntMan.imageAlpha = 100

            characterName.text = "Steve Rogers"
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
        }
        imageViewAntMan.setOnClickListener {
            imageViewHulk.imageAlpha = 100
            imageViewIronMan.imageAlpha = 100
            imageViewCaptain.imageAlpha = 100
            imageViewAntMan.imageAlpha = 255

            characterName.text = "Scott Lang"
            characterName.visibility = View.VISIBLE

            buttonReady.visibility = View.VISIBLE
        }

        buttonReady.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun hide() {
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
}