package gu_android_team.modernbooklibrary.ui.bookdescriptionscreen

import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentBookDescriptionBinding
import gu_android_team.modernbooklibrary.domain.Screen

class BookDescriptionFragment : Fragment(R.layout.fragment_book_description), Screen {

    companion object {
        @JvmStatic
        fun newInstance() = BookDescriptionFragment()
    }

    private val binding: FragmentBookDescriptionBinding by viewBinding(
        FragmentBookDescriptionBinding::bind
    )

    override fun showProgress() {
        with(binding) {
            bookProgressBar.visibility = View.VISIBLE
            bookTitleTextview.visibility = View.GONE
            bookSubtitleTextview.visibility = View.GONE
            bookAuthorLabelTextview.visibility = View.GONE
            bookAuthorTextview.visibility = View.GONE
            bookAddToFavoritesImageView.visibility = View.GONE
            bookCoverImageView.visibility = View.GONE
            bookNumberOfPagesLabelTextView.visibility = View.GONE
            bookNumberOfPagesTextView.visibility = View.GONE
            bookRatingLabelTextView.visibility = View.GONE
            bookRatingTextView.visibility = View.GONE
            bookRatingStarImageView.visibility = View.GONE
            bookIsbn10LabelTextView.visibility = View.GONE
            bookIsbn10TextView.visibility = View.GONE
            bookIsbn13LabelTextView.visibility = View.GONE
            bookIsbn13TextView.visibility = View.GONE
            bookPublisherLabelTextView.visibility = View.GONE
            bookPublisherTextView.visibility = View.GONE
            bookDescriptionTextView.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            bookProgressBar.visibility = View.GONE
            bookTitleTextview.visibility = View.VISIBLE
            bookSubtitleTextview.visibility = View.VISIBLE
            bookAuthorLabelTextview.visibility = View.VISIBLE
            bookAuthorTextview.visibility = View.VISIBLE
            bookAddToFavoritesImageView.visibility = View.VISIBLE
            bookCoverImageView.visibility = View.VISIBLE
            bookNumberOfPagesLabelTextView.visibility = View.VISIBLE
            bookNumberOfPagesTextView.visibility = View.VISIBLE
            bookRatingLabelTextView.visibility = View.VISIBLE
            bookRatingTextView.visibility = View.VISIBLE
            bookRatingStarImageView.visibility = View.VISIBLE
            bookIsbn10LabelTextView.visibility = View.VISIBLE
            bookIsbn10TextView.visibility = View.VISIBLE
            bookIsbn13LabelTextView.visibility = View.VISIBLE
            bookIsbn13TextView.visibility = View.VISIBLE
            bookPublisherLabelTextView.visibility = View.VISIBLE
            bookPublisherTextView.visibility = View.VISIBLE
            bookDescriptionTextView.visibility = View.VISIBLE
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
        TODO("Not yet implemented")
    }
}