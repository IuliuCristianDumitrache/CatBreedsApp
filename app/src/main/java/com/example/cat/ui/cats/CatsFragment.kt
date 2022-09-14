package com.example.cat.ui.cats

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cat.R
import com.example.cat.databinding.CatListItemBinding
import com.example.cat.databinding.FragmentCatsBinding
import com.example.cat.extensions.disposeIfNotAlready
import com.example.cat.extensions.navigateSafely
import com.example.cat.extensions.observe
import com.example.cat.extensions.onQueryTextChanged
import com.example.cat.models.CatModel
import com.example.cat.network.RxBus
import com.example.cat.network.rxmessages.ReloadCatList
import com.example.cat.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class CatsFragment : Fragment(), CatsAdapter.OnCatItemListener {

    private val viewModel: CatsViewModel by viewModels()
    private var views: FragmentCatsBinding? = null
    private val catsAdapter = CatsAdapter(this)
    private lateinit var searchView: SearchView

    private var reloadSubscriber: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentCatsBinding.bind(view)

        initRecyclerView()

        observe(viewModel.catsList) { listOfCats ->
            catsAdapter.submitList(listOfCats)
        }

        observe(viewModel.showProgress) { showProgress ->
            views!!.progress.isVisible = showProgress
        }

        observe(viewModel.showError) { message ->
            Toast.makeText(
                context, message,
                Toast.LENGTH_LONG
            ).show()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            if (viewModel.catsList.value == null) {
                viewModel.getCatsList()
            }
        }

        initSubscriber()
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setSupportActionBar(views!!.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cat, menu)

        val search = menu.findItem(R.id.action_search)
        searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by)

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            search.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }

        observe(viewModel.catsFiltered) { cats ->
            catsAdapter.submitList(cats)
        }
    }

    private fun initSubscriber() {
        reloadSubscriber = RxBus.listen(ReloadCatList::class.java).subscribe {
            if (it.reload) {
                viewModel.getCatsList()
            }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        views?.rvCats?.layoutManager = linearLayoutManager
        views?.rvCats?.adapter = catsAdapter
    }

    override fun onDestroy() {
        reloadSubscriber?.disposeIfNotAlready()
        super.onDestroy()
    }

    override fun onCatItemClicked(binding: CatListItemBinding, cat: CatModel) {
        val extras = FragmentNavigatorExtras(
            binding.image to getString(R.string.image_transition),
            binding.tvName to getString(R.string.name_transition),
            binding.tvDescription to getString(R.string.description_transition),
            binding.root to getString(R.string.root_transition)
        )

        val catDirection = CatsFragmentDirections.actionCatsFragmentToCatDetailFragment(
            cat = cat
        )
        findNavController().navigateSafely(
            catDirection, extras
        )
    }
}