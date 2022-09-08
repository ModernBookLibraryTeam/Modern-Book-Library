package gu_android_team.modernbooklibrary.ui.searchscreen

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.databinding.FragmentSearchScreenBinding
import gu_android_team.modernbooklibrary.di.SEARCH_SCREEN_VIEW_MODEL
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Screen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchScreenFragment : Fragment(), Screen {

    companion object {
        @JvmStatic
        fun newInstance() = SearchScreenFragment()
    }

    private val searchAdapter = SearchRecyclerViewAdapter()
    private val searchViewModel: SearchViewModel by viewModel(named(SEARCH_SCREEN_VIEW_MODEL))

    private val binding: FragmentSearchScreenBinding by viewBinding(
        FragmentSearchScreenBinding::bind
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchRecyclerView()
        binding.searchTextInputEditText.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    closeKeyboard()
                    showProgress()
                    searchViewModel.getSearchedBooks(
                        binding.searchTextInputEditText.text.toString(),
                        "1"
                    )
                    searchViewModel.searchedResult.observe(viewLifecycleOwner) {
                        when (it) {
                            is DataState.Success -> {
                                showStandardScreen()
                                searchAdapter.searchedBooks = it.data ?: emptyList()
                            }
                            is DataState.Error -> {
                                showStandardScreen()
                                showError(it.message.toString())
                            }
                        }
                    }
                    return true
                }
                return false
            }

        })
    }

    private fun closeKeyboard() {
        val view = view?.findFocus()
        if (view != null) {
            val manager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initSearchRecyclerView() {
        with(binding) {
            searchListRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = searchAdapter
            }
        }
    }

    override fun showProgress() {
        with(binding) {
            searchProgressBar.visibility = View.VISIBLE
            searchEmptyImageView.visibility = View.GONE
            searchListRecyclerView.visibility = View.GONE
            searchInputTextLayout.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            searchProgressBar.visibility = View.GONE
            searchEmptyImageView.visibility = View.VISIBLE
            searchListRecyclerView.visibility = View.VISIBLE
            searchInputTextLayout.visibility = View.VISIBLE
        }
    }

    override fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_string) {
                updateResultList()
            }
            .show()
    }

    private fun updateResultList() {
        TODO("Not yet implemented")
    }
}