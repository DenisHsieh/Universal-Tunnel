package io.denix.project.universaltunnel.ui.media

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.databinding.FragmentMediaBinding
import io.denix.project.universaltunnel.ui.media.play.ExoPlayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaFragment : Fragment() {

    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!

    private lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val mediaViewModel = ViewModelProvider(this)[MediaViewModel::class.java]

        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewExoPlayer.setOnClickListener {
            val intent = Intent(this.context, ExoPlayerActivity::class.java)
            this.context?.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        glide = Glide.with(this)
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                glide.load(R.drawable.exoplayer)
                     .into(binding.imageViewExoPlayer)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}