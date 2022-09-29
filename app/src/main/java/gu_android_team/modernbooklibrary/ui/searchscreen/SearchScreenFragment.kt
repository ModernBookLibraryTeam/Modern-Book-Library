package gu_android_team.modernbooklibrary.ui.searchscreen


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.flexbox.*
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.databinding.FragmentSearchScreenBinding
import gu_android_team.modernbooklibrary.di.SEARCH_SCREEN_VIEW_MODEL
import gu_android_team.modernbooklibrary.domain.OpenDescriptionScreenController
import gu_android_team.modernbooklibrary.domain.Screen
import gu_android_team.modernbooklibrary.ui.bookdescriptionscreen.BookDescriptionFragment.Companion.BOOK_ISBN13_KEY
import gu_android_team.modernbooklibrary.utils.KeyBoard
import gu_android_team.modernbooklibrary.utils.ONE_VALUE
import gu_android_team.modernbooklibrary.utils.ZERO_VAL
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import timber.log.Timber

const val TIMEOUT = 2000L

class SearchScreenFragment : Fragment(), Screen, SearchRecyclerViewAdapter.OnBookListener {

    private val searchAdapter = SearchRecyclerViewAdapter(this)
    private val searchViewModel: SearchViewModel by viewModel(named(SEARCH_SCREEN_VIEW_MODEL))
    private val controller by lazy {
        activity as OpenDescriptionScreenController
    }

    private var page = 1
    private val timeOutHandler = Handler(Looper.getMainLooper())
    private val typingTimeOut = Runnable {
        view?.let {
            KeyBoard.hideKeyboard(it, requireContext())
            showProgress()
        }
        page = 1
        searchViewModel.getFirstSearchedBooks(
            binding.searchTextInputEditText.text.toString(),
            "$page"
        )
        searchViewModel.searchedResultWhileTyping.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    showStandardScreen()
                    searchAdapter.setSearchedBooksList(it.data!!)
                }
                is DataState.Error -> {
                    showStandardScreen()
                    showError(it.message.toString())
                }
            }
        }
    }
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
        binding.searchTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //nothing todo
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.searchListRecyclerView.layoutManager?.scrollToPosition(0)
                timeOutHandler.removeCallbacks(typingTimeOut)
                timeOutHandler.postDelayed(typingTimeOut, TIMEOUT)
            }

            override fun afterTextChanged(p0: Editable?) {
                //nothing todo
            }

        })

        binding.searchListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                var pastVisibleItems: Int = ZERO_VAL
                var childVisibleItems: Int = ZERO_VAL
                var totalItemCount: Int = ZERO_VAL
                var previousTotalCount: Int = ZERO_VAL
                val layoutManager =
                    binding.searchListRecyclerView.layoutManager as FlexboxLayoutManager
                if (dy > ZERO_VAL) {
                    binding.searchInputTextLayout.visibility = View.GONE
                    childVisibleItems = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findLastVisibleItemPosition()
                    if (childVisibleItems + pastVisibleItems > totalItemCount - 2 && totalItemCount > previousTotalCount) {

                        previousTotalCount = totalItemCount
                        page += ONE_VALUE
                        searchViewModel.getSearchedBooks(
                            binding.searchTextInputEditText.text.toString(),
                            "$page"
                        )
                        searchViewModel.searchedResult.observe(viewLifecycleOwner) {
                            when (it) {
                                is DataState.Success -> {
                                    searchAdapter.setSearchedBooksNewPage(it.data!!)
                                }
                                is DataState.Error -> {
                                    showError(it.message.toString())
                                }
                            }
                        }
                    }

                } else {
                    binding.searchInputTextLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initSearchRecyclerView() {
        with(binding) {
            searchListRecyclerView.apply {
                layoutManager = FlexboxLayoutManager(context).apply {
                    justifyContent = JustifyContent.CENTER
                    alignItems = AlignItems.CENTER
                    flexDirection = FlexDirection.ROW
                    flexWrap = FlexWrap.WRAP
                    adapter = searchAdapter
                }
            }
        }
    }

    override fun showProgress() {
        with(binding) {
            searchProgressBar.visibility = View.VISIBLE
            searchEmptyImageView.visibility = View.GONE
            searchListRecyclerView.visibility = View.GONE
            searchInputTextLayout.visibility = View.VISIBLE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            searchProgressBar.visibility = View.GONE
            searchEmptyImageView.visibility = View.GONE
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
        searchViewModel.getSearchedBooks(
            binding.searchTextInputEditText.text.toString(),
            "$page"
        )
    }

    override fun onBookClick(position: Int) {
        val bundle = Bundle().apply {
            putString(BOOK_ISBN13_KEY, searchAdapter.searchedBooks[position].isbn13)
        }
        controller.openBookDescriptionScreenFromSearchScreen(bundle)
        Timber.tag("TAG").d("Clicked ${searchAdapter.searchedBooks[position]}")
    }
}