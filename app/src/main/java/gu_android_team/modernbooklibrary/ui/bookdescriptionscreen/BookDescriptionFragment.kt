package gu_android_team.modernbooklibrary.ui.bookdescriptionscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentBookDescriptionBinding
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Screen
import gu_android_team.modernbooklibrary.utils.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDescriptionFragment : Fragment(R.layout.fragment_book_description), Screen {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BookDescriptionFragment {
            val fragment = BookDescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }

        const val BOOK_ISBN13_KEY = "BOOK_ISBN13_KEY"
    }

    private val binding: FragmentBookDescriptionBinding by viewBinding(
        FragmentBookDescriptionBinding::bind
    )

    private val descriptionViewModel: BookDescriptionScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        updateData()
    }

    private fun subscribeToLiveData() {
        descriptionViewModel.livedataToObserve.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.AppStateSuccess<*> -> {
                showStandardScreen()
                val currentBook = appState.value as Book
                fillBookDescription(currentBook)
            }

            is AppState.AppStateError -> {
                showError(appState.error)
            }

            is AppState.AppStateLoading -> {
                showProgress()
            }
        }
    }

    private fun fillBookDescription(book: Book) {
        with(binding) {
            bookTitleTextview.text = book.title
            bookSubtitleTextview.text = book.subtitle
            bookAuthorTextview.text = book.authors
            bookAddToFavoritesImageView
            bookCoverImageView.load(book.image) {
                placeholder(R.drawable.ic_baseline_image_24)
            }
            bookPublicationDateTextView.text = book.year
            bookNumberOfPagesTextView.text = book.pages
            bookRatingTextView.text = book.rating
            if (book.rating.isEmpty()) {
                bookRatingStarImageView.visibility = View.GONE
            }
            bookIsbn10TextView.text = book.isbn10
            bookIsbn13TextView.text = book.isbn13
            bookPublisherTextView.text = book.publisher
            bookDescriptionTextView.text = book.desc
        }
    }

    override fun showProgress() {
        with(binding) {
            bookProgressBar.visibility = View.VISIBLE
            bookMainContainerConstraintLayout.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            bookProgressBar.visibility = View.GONE
            bookMainContainerConstraintLayout.visibility = View.VISIBLE
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
        val bookIsbn13 = arguments?.getString(BOOK_ISBN13_KEY) ?: throw NullPointerException(
            getString(
                R.string.error_fragment_arguments_is_null_message
            )
        )
        descriptionViewModel.getBookInfo(bookIsbn13)
    }
}