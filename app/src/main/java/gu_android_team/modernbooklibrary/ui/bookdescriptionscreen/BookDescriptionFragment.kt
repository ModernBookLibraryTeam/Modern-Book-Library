package gu_android_team.modernbooklibrary.ui.bookdescriptionscreen

import android.os.Bundle
import android.view.View
import android.view.View.generateViewId
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentBookDescriptionBinding
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.NavigationController
import gu_android_team.modernbooklibrary.domain.Screen
import gu_android_team.modernbooklibrary.ui.bookreaderscreen.BookReaderFragment.Companion.BOOK_LINK_KEY
import gu_android_team.modernbooklibrary.utils.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDescriptionFragment : Fragment(R.layout.fragment_book_description), Screen {

    companion object {
        const val BOOK_ISBN13_KEY = "BOOK_ISBN13_KEY"
        const val LINK_MARGIN_TOP = 16
    }

    private val binding: FragmentBookDescriptionBinding by viewBinding(
        FragmentBookDescriptionBinding::bind
    )

    private var isInFavorite = false
    private lateinit var currentBook: Book
    private lateinit var bookIsbn13: String

    private val controller by lazy {
        activity as NavigationController
    }

    private val descriptionViewModel: BookDescriptionScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookIsbn13 = arguments?.getString(BOOK_ISBN13_KEY) ?: throw NullPointerException(
            getString(
                R.string.error_fragment_arguments_is_null_message
            )
        )

        subscribeToLiveData()
        updateData()
    }

    private fun updateData() {
        descriptionViewModel.checkIfBookIsInLocalDb(bookIsbn13)
        descriptionViewModel.getBookInfo(bookIsbn13)
    }

    private fun subscribeToLiveData() {
        descriptionViewModel.livedataToObserve.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        descriptionViewModel.livedataForCheckFavorites.observe(viewLifecycleOwner) { bookStatus ->
            isInFavorite = bookStatus
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.AppStateSuccess<*> -> {
                showStandardScreen()

                currentBook = appState.value as Book
                fillBookDescription(currentBook)
                initAddToFavoritesButtonListener()
            }

            is AppState.AppStateError -> {
                showError(appState.error)
            }

            is AppState.AppStateLoading -> {
                showProgress()
            }
        }
    }

    private fun initAddToFavoritesButtonListener() {
        binding.bookAddToFavoritesImageView.setOnClickListener {

            isInFavorite = if (!isInFavorite) {
                binding.bookAddToFavoritesImageView.load(R.drawable.ic_baseline_favorite_24)
                descriptionViewModel.addBookToFavorites(currentBook)
                true
            } else {
                binding.bookAddToFavoritesImageView.load(R.drawable.ic_baseline_favorite_border_24)
                descriptionViewModel.deleteBookFromFavorites(currentBook)
                false
            }
        }
    }

    private fun fillBookDescription(book: Book) {
        with(binding) {
            bookTitleTextview.text = book.title

            if (book.subtitle.isNotEmpty()) {
                bookSubtitleTextview.text = book.subtitle
            } else {
                bookSubtitleTextview.visibility = View.GONE
            }

            bookAuthorTextview.text = book.authors

            bookCoverImageView.load(book.image) {
                placeholder(R.drawable.ic_baseline_image_24)
            }

            if (isInFavorite) {
                binding.bookAddToFavoritesImageView.load(R.drawable.ic_baseline_favorite_24)
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

            book.pdfLinksList?.let { pdfList ->

                binding.bookDownloadsLabelTextView.visibility = View.VISIBLE

                var isFirstLink = true
                var previousViewId: Int
                val constraintSet = ConstraintSet()

                pdfList.forEach { entity ->

                    val labelTextView = TextView(context)
                    labelTextView.text = entity.key

                    val pdfUrlButton = MaterialButton(
                        requireContext()
                    ).apply {
                        text = entity.key
                        id = generateViewId()
                        previousViewId = id
                        setTextColor(resources.getColor(R.color.white, context.theme))
                        setOnClickListener {
                            val bundle = putPdfLinkToBundle(entity.value)
                            controller.openBookReaderScreen(bundle)
                        }
                    }

                    binding.bookMainContainerConstraintLayout.addView(pdfUrlButton)
                    constraintSet.clone(binding.bookMainContainerConstraintLayout)

                    if (isFirstLink) {
                        constraintSet.connect(
                            pdfUrlButton.id,
                            ConstraintSet.TOP,
                            binding.bookDownloadsLabelTextView.id,
                            ConstraintSet.BOTTOM,
                            LINK_MARGIN_TOP
                        )
                        isFirstLink = false
                    } else {
                        constraintSet.connect(
                            pdfUrlButton.id,
                            ConstraintSet.TOP,
                            previousViewId,
                            ConstraintSet.BOTTOM,
                            LINK_MARGIN_TOP
                        )
                    }
                }

                constraintSet.applyTo(binding.bookMainContainerConstraintLayout)

            }
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

    private fun putPdfLinkToBundle(link: String): Bundle {
        val bundle = Bundle()
        bundle.putString(BOOK_LINK_KEY, link)
        return bundle
    }
}