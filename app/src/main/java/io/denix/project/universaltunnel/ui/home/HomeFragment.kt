package io.denix.project.universaltunnel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.common.SharedPrefsUtil
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var userId = 0
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPrefsUtil = SharedPrefsUtil()
        userId = sharedPrefsUtil.getLoginUserId(this.requireContext())
        initializeViewModel()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeUi() {
        val textView: TextView = binding.textHome
        homeFragmentViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val userIcon = when (userId) {
            1 -> R.drawable.user_hulk
            2 -> R.drawable.user_ironman
            3 -> R.drawable.user_captain
            4 -> R.drawable.user_antman
            else -> R.mipmap.ic_launcher_round
        }
        binding.imageViewUserIcon.setImageResource(userIcon)
    }

    private fun initializeViewModel() {
        val application = activity?.application
        val utRoomDatabase = (application as UtApplication).database
        val userDao = utRoomDatabase.userDao()

        homeFragmentViewModel = ViewModelProvider(this,
            HomeFragmentViewModelFactory(application, userId, userDao, application.assets)
        )[HomeFragmentViewModel::class.java]
    }
}