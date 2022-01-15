package ru.meseen.dev.mtptest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.meseen.dev.mtptest.databinding.MainFragmentBinding
import ru.meseen.dev.mtptest.ui.main.Action.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()

    private var action = DEFAULT

    private var _vb: MainFragmentBinding? = null
    private val vb: MainFragmentBinding get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = MainFragmentBinding.inflate(inflater)
        vb.createFile.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            action = CREATE_FILE
        }
        vb.createDir.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            action = CREATE_DIR
        }
        vb.createDirWithFile.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            action = CREATE_DIR_AND_FILE
        }
        vb.clearFiles.setOnClickListener { viewModel.clearFiles() }
        vb.scan.setOnClickListener { viewModel.scan() }
        vb.title.setOnClickListener { viewModel.clearLog() }

        lifecycleScope.launch {
            viewLifecycleOwner.whenCreated {
                viewModel.logs.collectLatest { log ->
                    vb.logs.text = log
                }
            }
        }
        return vb.root
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                when (action) {
                    CREATE_FILE -> viewModel.createFile()
                    CREATE_DIR -> viewModel.createDir()
                    CREATE_DIR_AND_FILE -> viewModel.createDirWithFile()
                    DEFAULT -> {}
                }
                action = DEFAULT
            } else {
                Snackbar.make(vb.root, "Предоставьте права на запись", Snackbar.LENGTH_SHORT).show()
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}

enum class Action {
    CREATE_FILE, CREATE_DIR, CREATE_DIR_AND_FILE, DEFAULT
}