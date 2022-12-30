package io.denix.project.universaltunnel.ui.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.denix.project.universaltunnel.common.SharedPrefsUtil
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.databinding.FragmentNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel

    private lateinit var recyclerViewNote: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeViewModel()

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViewModel() {
        val application = activity?.application
        val utRoomDatabase = (application as UtApplication).database
        val noteDao = utRoomDatabase.noteDao()
        val loginDao = utRoomDatabase.loginDao()

        noteViewModel = ViewModelProvider(this,
            NoteViewModelFactory(application, noteDao, loginDao, application.assets))[NoteViewModel::class.java]
    }

    private fun initializeRecyclerView() {
        recyclerViewNote = binding.recyclerViewNote
        recyclerViewNote.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        // 取得 userId
        var userId = 0
        val sharedPrefsUtil = SharedPrefsUtil()
        this.context?.let { userId = sharedPrefsUtil.getLoginUserId(it) }

        lifecycleScope.launch(Dispatchers.IO) {
            // 使用 userId 查詢 noteList
            val noteList = noteViewModel.getNotesByUser(userId)
            noteAdapter = NoteAdapter(noteList)
            recyclerViewNote.adapter = noteAdapter
        }
    }
}