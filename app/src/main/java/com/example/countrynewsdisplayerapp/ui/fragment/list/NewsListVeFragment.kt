package com.example.countrynewsdisplayerapp.ui.fragment.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrynewsdisplayerapp.R
import com.example.countrynewsdisplayerapp.base.BaseExtraData
import com.example.countrynewsdisplayerapp.base.BaseState
import com.example.countrynewsdisplayerapp.base.noInternetConnectivity
import com.example.countrynewsdisplayerapp.data.Article
import com.example.countrynewsdisplayerapp.databinding.FragmentNewsListBinding
import com.example.countrynewsdisplayerapp.databinding.FragmentNewsVeListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import retrofit2.HttpException
import java.net.UnknownHostException

class NewsListVeFragment : Fragment() {

    lateinit var binding: FragmentNewsVeListBinding

    private val viewModel: NewsListViewModel by viewModels()

    lateinit var adapter: NewsListAdapter

    lateinit var myList: List<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsVeListBinding.inflate(inflater, container, false)

        setupView()

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
        viewModel.requestInformation("ve")

        return binding.root
    }
    /**
     * Setup view elements
     */
    private fun setupView() {
        // Set recycler view
        adapter = NewsListAdapter(listOf(), requireActivity()) { item ->
            findNavController().navigate(NewsListVeFragmentDirections.actionNewsListVeFragmentToNewsDetailFragment(item))
        }
        binding.myRecyclerView.apply {
            adapter = this@NewsListVeFragment.adapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        // Set swipe refresh gesture
        binding.fragmentListSwipeRefreshLayout.setOnRefreshListener {
            adapter.updateList(listOf())
            viewModel.requestInformation("ve")
        }

    }


    /**
     * Normal state, everything works!
     */
    private fun updateToNormalState(data: NewsListState) {
        binding.fragmentListProgressBar.visibility = View.GONE
        binding.fragmentListSwipeRefreshLayout.isRefreshing = false
        adapter.updateList(data.newsList)
        binding.outlinedTextField.editText?.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadList(s.toString(),data.newsList)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    /**
     * function to look for an specific new by text
     */
    private fun loadList(textToSearch: String = "", myList: List<Article>) {
        val newList = myList.filter { item ->
            item.title.contains(textToSearch, ignoreCase = true)
        }
        adapter.updateList(newList)
    }

    /**
     * Loading state, wait until the results...
     */
    private fun updateToLoadingState(dataLoading: BaseExtraData?) {
        binding.fragmentListProgressBar.visibility = View.VISIBLE
    }

    /**
     * Error state :(
     */
    private fun updateToErrorState(dataError: Throwable) {
        adapter.updateList(listOf())
        binding.fragmentListProgressBar.visibility = View.GONE
        val msg = when (dataError) {
            is HttpException -> "Fatal error: " + dataError.code().toString()
            is UnknownHostException -> "No tienes conexión a internet"
            is noInternetConnectivity -> "Encienda la Wifi o los datos moviles e intente de nuevo"
            else -> "Error genérico"
        }
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Reintentar") { dialog, which ->
                viewModel.requestInformation("ve")
            }
            .show()
    }

}