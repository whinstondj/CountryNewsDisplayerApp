package com.example.countrynewsdisplayerapp.ui.fragment.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.countrynewsdisplayerapp.R
import com.example.countrynewsdisplayerapp.base.BaseExtraData
import com.example.countrynewsdisplayerapp.base.BaseState
import com.example.countrynewsdisplayerapp.databinding.FragmentNewsListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.HttpException
import java.net.UnknownHostException

class NewsListFragment : Fragment() {

    lateinit var binding: FragmentNewsListBinding

    private val viewModel: NewsListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)

        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is BaseState.Error -> {
                    updateToErrorState(state.dataError)
                }
                is BaseState.Loading -> {
                    updateToLoadingState(state.dataLoading)
                }
                is BaseState.Normal -> {
                    updateToNormalState(state.data as NewsListState)
                }
            }
        })
        viewModel.requestInformation("us")

        return binding.root
    }
    /**
     * Normal state, everything works!
     */
    private fun updateToNormalState(data: NewsListState) {
        binding.reviewResponse.text = data.newsList.toString()
    }

    /**
     * Loading state, wait until the results...
     */
    private fun updateToLoadingState(dataLoading: BaseExtraData?) {
    }

    /**
     * Error state :(
     */
    private fun updateToErrorState(dataError: Throwable) {
        val msg = when (dataError) {
            is HttpException -> "Fatal error: " + dataError.code().toString()
            is UnknownHostException -> "No tienes conexión a internet"
            else -> "Error genérico"
        }
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Reintentar") { dialog, which ->
                viewModel.requestInformation("us")
            }
            .show()
    }

}