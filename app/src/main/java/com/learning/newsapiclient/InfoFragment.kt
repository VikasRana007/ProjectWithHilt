package com.learning.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.learning.newsapiclient.databinding.FragmentInfoBinding
import com.learning.newsapiclient.presentation.viewmodel.NewsViewModel

class InfoFragment : Fragment() {

    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var viewModel : NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        // now get received arguments
        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        // write code to display the article on web view
        fragmentInfoBinding.wvInfo.apply {
            webViewClient = WebViewClient()
            if (article.url != null) {
                loadUrl(article.url)
            }
        }
        fragmentInfoBinding.fabSave.setOnClickListener{
           viewModel.saveArticle(article)
            Snackbar.make(view,"Saved Successfully!",Snackbar.LENGTH_LONG).show()
            // Room also allows us to get the inserted row id as a return value. We could have used
            // that for the verification...

        }
    }
}