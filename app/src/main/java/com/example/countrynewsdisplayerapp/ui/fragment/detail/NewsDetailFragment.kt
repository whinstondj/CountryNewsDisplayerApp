package com.example.countrynewsdisplayerapp.ui.fragment.detail

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.countrynewsdisplayerapp.R
import com.example.countrynewsdisplayerapp.base.BaseExtraData
import com.example.countrynewsdisplayerapp.base.BaseState
import com.example.countrynewsdisplayerapp.databinding.FragmentNewsDetailBinding
import com.example.countrynewsdisplayerapp.databinding.ItemNewsListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.HttpException
import java.net.UnknownHostException
import android.app.Activity

class NewsDetailFragment : Fragment() {

    lateinit var binding: FragmentNewsDetailBinding

    val viewModel: NewsDetailViewModel by viewModels()

    val args: NewsDetailFragmentArgs by navArgs()

    private var buttonURL: String? = null

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
       setupView()

       viewModel.getState().observe(viewLifecycleOwner, { state ->
           when (state) {
               is BaseState.Error -> {
                   updateToErrorState(state.dataError)
               }
               is BaseState.Loading -> {
                   //state.dataLoading
                   updateToLoadingState(state.dataLoading)
               }
               is BaseState.Normal -> {
                   //state.data
                   updateToNormalState(state.data as NewsDetailState)
               }

           }

       })

       viewModel.setupParams (args.article)
       return binding.root
    }

    /**
     * Setting Elements
     */
    private fun setupView() {
        binding.readMoreButton.setOnClickListener {
            buttonURL?.let {
                searchWeb(it)
            } ?: run {
                Toast.makeText(requireActivity(), "No existe una URL", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Function to search on Internet
     */
    private fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            requireActivity().startActivity(intent)
        }
    }


    /**
     * Normal estate, everything works!
     */
    private fun updateToNormalState(data: NewsDetailState) {

        (data.article?.title ?: "No hay titulo").also { binding.newsDetailTitleText.text = it }
        (data.article?.content ?: "No hay Contenido").also {binding.newsDetailContentText.text = it}
        (data.article?.author ?: "No hay Autor Conocido").also { binding.newsDetailAuthorText.text = it }
            Glide
                .with(requireActivity())
                .load(data.article?.urlToImage ?: "https://ryrsupport.net/wp-content/uploads/2018/05/que-significa-el-codigo-de-error-http-404-not-foun.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_no_photos)
                .into(binding.newsDetailImageView)

        buttonURL = data.article?.url

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
            }
            .show()
    }

}