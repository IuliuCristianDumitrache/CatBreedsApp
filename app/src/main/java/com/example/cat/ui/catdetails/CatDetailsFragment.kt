package com.example.cat.ui.catdetails

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cat.R
import com.example.cat.databinding.FragmentCatDetailsBinding
import com.example.cat.extensions.loadWithUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatDetailsFragment : Fragment() {

    private var views: FragmentCatDetailsBinding? = null

    private val args by navArgs<CatDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cat_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentCatDetailsBinding.bind(view)

        initUi()
        initListeners()
    }

    private fun initListeners() {
        views?.btnClose?.setOnClickListener {
            popBackStack()
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun initUi() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        views?.image?.loadWithUri(args.cat.image?.url)

        views?.tvName?.text = args.cat.name
        views?.tvDescription?.text = args.cat.description
        views?.tvCountryCode?.text = args.cat.countryCode
        views?.tvTemperament?.text = args.cat.temperament
        views?.tvLink?.text = args.cat.wikipediaLink
    }
}