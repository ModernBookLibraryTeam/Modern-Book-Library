package gu_android_team.modernbooklibrary.ui.bookreaderscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentBookReaderBinding

class BookReaderFragment : Fragment(R.layout.fragment_book_reader) {
    companion object {
        const val BOOK_LINK_KEY = "BOOK_LINK_KEY"
        private const val GOOGLE_DOCS_URL = "https://docs.google.com/gview?embedded=true&url="
        private const val DBOOKS_PDF_POSTFIX = "pdf/"
        private const val ITBOOKS_LINK_ROOT = "itbook.store"
    }

    private val binding: FragmentBookReaderBinding by viewBinding(
        FragmentBookReaderBinding::bind
    )

    private lateinit var webView: WebView
    private lateinit var bookPdfLink: String
    private lateinit var newBookUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookPdfLink = arguments?.getString(BOOK_LINK_KEY) ?: throw NullPointerException(
            getString(
                R.string.error_fragment_arguments_is_null_message
            )
        )

        webView = binding.bookReaderWebView
        webView.webViewClient = CustomWebViewClient()

        webView.settings.apply {
            setSupportZoom(true)
            loadWithOverviewMode = true
            javaScriptEnabled = true
        }

        loadParsedUrl()
    }

    private fun loadParsedUrl() {

        val stringBuilder = StringBuilder()

        if (bookPdfLink.contains(ITBOOKS_LINK_ROOT)) {
            stringBuilder.append(GOOGLE_DOCS_URL)
            stringBuilder.append(bookPdfLink)

            webView.loadUrl(stringBuilder.toString())
        } else {
            webView.loadUrl(bookPdfLink)
        }
    }

    inner class CustomWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            webView.url?.let {
                newBookUrl = it
            }

            if (!newBookUrl.contains(DBOOKS_PDF_POSTFIX) && !newBookUrl.contains(ITBOOKS_LINK_ROOT)) {
                val stringBuilder = StringBuilder()
                stringBuilder.append(newBookUrl)
                stringBuilder.append(DBOOKS_PDF_POSTFIX)

                webView.loadUrl(stringBuilder.toString())
            } else {
                binding.bookReaderProgressBarContainer.visibility = View.GONE
            }
        }
    }
}