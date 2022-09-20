package gu_android_team.modernbooklibrary.ui.favoritesscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentFavoritesScreenBinding
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Screen
import gu_android_team.modernbooklibrary.utils.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesScreenFragment : Fragment(R.layout.fragment_favorites_screen), Screen {

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesScreenFragment()
    }

    private val binding: FragmentFavoritesScreenBinding by viewBinding(
        FragmentFavoritesScreenBinding::bind
    )

    private val viewModel: FavoritesScreenViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        subscribeToLivaData()
        recyclerView = binding.favoritesListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavoritesScreenAdapter(
            onItemClicked = {
                Toast.makeText(requireContext(), it.isbn13, Toast.LENGTH_SHORT).show()
            },
            onItemClickedDelete = {
                viewModel.deleteBookById(it)
            }
        )
        recyclerView.adapter = adapter

    }

    private fun subscribeToLivaData() {
        viewModel.liveData.observe(viewLifecycleOwner) { data ->
            renderData(data)
        }
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.AppStateSuccess<*> -> {
                adapter.submitList(appState.value as List<Book>)
                showStandardScreen()
            }
            is AppState.AppStateError -> {
                showError(appState.error)
            }
            AppState.AppStateLoading -> showProgress()
        }
    }

    override fun showProgress() {
        with(binding) {
            favoritesProgressBar.visibility = View.VISIBLE
            favoritesEmptyImageView.visibility = View.GONE
            favoritesFilterFab.visibility = View.GONE
            favoritesListRecyclerView.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            favoritesProgressBar.visibility = View.GONE
            favoritesEmptyImageView.visibility = View.VISIBLE
            favoritesFilterFab.visibility = View.VISIBLE
            favoritesListRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_string) {
                getFavoritesList()
            }
            .show()
    }

    private fun getFavoritesList() {
        TODO("Not yet implemented")
    }
}