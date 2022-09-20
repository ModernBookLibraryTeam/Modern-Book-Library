package gu_android_team.modernbooklibrary.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.MainActivity
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentMainScreenBinding
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.OpenDescriptionScreenController
import gu_android_team.modernbooklibrary.domain.Screen
import gu_android_team.modernbooklibrary.ui.bookdescriptionscreen.BookDescriptionFragment.Companion.BOOK_ISBN13_KEY
import gu_android_team.modernbooklibrary.utils.AppState
import gu_android_team.modernbooklibrary.utils.ZERO_VAL
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : Fragment(R.layout.fragment_main_screen), Screen {

    companion object {
        const val FIRST_TITLE_INDEX = 0
        const val SECOND_TITLE_INDEX = 1
        const val THIRD_TITLE_INDEX = 2
        const val FOURTH_TITLE_INDEX = 3
        const val LOAD_MORE_DIFFERENCE = 3
        const val TITLES_REQUIRED_COUNT = 4
        const val CPP_SPECIAL_TITLE_PART = "cpp"
        const val CSHARP_SPECIAL_TITLE_PART = "csharp"
    }


    private val controller by lazy {
        activity as OpenDescriptionScreenController
    }

    private val titles = mutableListOf<String>()
    private val newListAdapter = MainRecyclerViewAdapter { bookIsbn13 ->

        controller.openBookDescriptionScreen(
            putBookIsbn13toBundle(bookIsbn13)
        )
    }

    private val secondListAdapter = MainRecyclerViewAdapter { bookIsbn13 ->

        controller.openBookDescriptionScreen(
            putBookIsbn13toBundle(bookIsbn13)
        )
    }
    private val thirdListAdapter = MainRecyclerViewAdapter { bookIsbn13 ->

        controller.openBookDescriptionScreen(
            putBookIsbn13toBundle(bookIsbn13)
        )
    }
    private val fourthListAdapter = MainRecyclerViewAdapter { bookIsbn13 ->

        controller.openBookDescriptionScreen(
            putBookIsbn13toBundle(bookIsbn13)
        )
    }


    private val binding: FragmentMainScreenBinding by viewBinding(
        FragmentMainScreenBinding::bind
    )

    private val mainViewModel: MainScreenViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity !is OpenDescriptionScreenController) {
            throw IllegalStateException(getString(R.string.wrong_activity_error_message))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            mainViewModel.getLists()
        }

        initRecyclerViews()
        subscribeToLivaData()
    }

    private fun initRecyclerViews() {
        with(binding) {
            mainListNewRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = newListAdapter

            }

            mainSecondListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = secondListAdapter
                initOnScrollListener(this@apply, SECOND_TITLE_INDEX)
            }

            mainThirdListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = thirdListAdapter
                initOnScrollListener(this@apply, THIRD_TITLE_INDEX)
            }

            mainFourthListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = fourthListAdapter
                initOnScrollListener(this@apply, FOURTH_TITLE_INDEX)
            }
        }
    }

    private fun initOnScrollListener(recyclerView: RecyclerView, titleIndex: Int) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var pastVisibleItems: Int = ZERO_VAL
            var visibleItemCount: Int = ZERO_VAL
            var totalItemCount: Int = ZERO_VAL
            var previousTotalCount: Int = ZERO_VAL
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dx > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findLastVisibleItemPosition()



                    if (visibleItemCount + pastVisibleItems > totalItemCount - LOAD_MORE_DIFFERENCE && totalItemCount > previousTotalCount) {

                        previousTotalCount = totalItemCount
                        mainViewModel.loadMoreToSelectedList(
                            titles[titleIndex],
                            titleIndex
                        )
                    }

                }
            }
        })
    }

    private fun subscribeToLivaData() {
        mainViewModel.livedataToObserve.observe(viewLifecycleOwner) { data ->
            data?.let {
                renderData(data)
            } ?: showError(getString(R.string.network_connection_error_message))
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.AppStateSuccess<*> -> {
                showStandardScreen()
                val result = data.value as LinkedHashMap<String, List<Book>>
                if (result.keys.size == TITLES_REQUIRED_COUNT) {
                    fillListTitles(result)
                    fillLists(result)
                } else {
                    showError(getString(R.string.network_connection_error_message))
                }
            }

            is AppState.AppStateError -> {
                showStandardScreen()
                showError(data.error)
            }

            is AppState.AppStateLoading -> {
                showProgress()
            }
        }
    }

    private fun fillListTitles(data: LinkedHashMap<String, List<Book>>?) {

        titles.clear()

        data?.keys?.forEach {
            titles.add(it)
        }
        if (titles.isNotEmpty() && titles.size == TITLES_REQUIRED_COUNT) {
            with(binding) {
                mainListNewTitleTextView.text = titles[FIRST_TITLE_INDEX]
                mainSecondListTitleTextView.text = makeCorrectTitle(titles[SECOND_TITLE_INDEX])
                mainThirdListTitleTextView.text = makeCorrectTitle(titles[THIRD_TITLE_INDEX])
                mainFourthListTitleTextView.text = makeCorrectTitle(titles[FOURTH_TITLE_INDEX])
            }
        } else {
            showError(getString(R.string.network_connection_error_message))
        }
    }

    private fun makeCorrectTitle(newTitle: String): String {
        return when (newTitle) {
            CPP_SPECIAL_TITLE_PART -> {
                this.getString(R.string.main_title_about_cpp)
            }
            CSHARP_SPECIAL_TITLE_PART -> {
                this.getString(R.string.main_title_about_csharp)
            }
            else -> {
                StringBuilder(this.getString(R.string.main_title_about_addition)).apply {
                    append(" ")
                    append(newTitle)
                }.toString()
            }
        }
    }


    private fun fillLists(data: LinkedHashMap<String, List<Book>>?) {

        data?.get(titles[FIRST_TITLE_INDEX])?.let { newListAdapter.updateData(it) }
        data?.get(titles[SECOND_TITLE_INDEX])?.let { secondListAdapter.updateData(it) }
        data?.get(titles[THIRD_TITLE_INDEX])?.let { thirdListAdapter.updateData(it) }
        data?.get(titles[FOURTH_TITLE_INDEX])?.let { fourthListAdapter.updateData(it) }

        val activity = requireActivity() as MainActivity
        activity.setKeepOnScreenCondition(false)
    }


    override fun showProgress() {
        with(binding) {
            mainProgressBar.visibility = View.VISIBLE
            mainContainerNestedScrollView.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            mainProgressBar.visibility = View.GONE
            mainContainerNestedScrollView.visibility = View.VISIBLE
        }
    }

    override fun showError(message: String) {

        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_string) {
                updateData()
            }
            .show()
    }

    private fun updateData() {
        mainViewModel.getLists()
    }

    override fun onStop() {
        super.onStop()

        with(binding) {
            mainListNewRecyclerView.clearOnScrollListeners()
            mainSecondListRecyclerView.clearOnScrollListeners()
            mainThirdListRecyclerView.clearOnScrollListeners()
            mainFourthListRecyclerView.clearOnScrollListeners()
        }
    }

    private fun putBookIsbn13toBundle(bookIsbn13: String): Bundle {
        val bundle = Bundle()
        bundle.putString(BOOK_ISBN13_KEY, bookIsbn13)
        return bundle
    }
}