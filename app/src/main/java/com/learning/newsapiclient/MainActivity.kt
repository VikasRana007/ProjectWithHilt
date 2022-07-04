package com.learning.newsapiclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.learning.newsapiclient.databinding.ActivityMainBinding
import com.learning.newsapiclient.presentation.adapter.NewsAdapter
import com.learning.newsapiclient.presentation.viewmodel.NewsViewModel
import com.learning.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    @Inject
    lateinit var factory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.bnvNews.setupWithNavController(navController)

//        with(activityMainBinding) {
//            val navController =(activityMainBinding.navHostFragmentContainer.getFragment() as NavHostFragment).findNavController()
//            bnvNews.setupWithNavController(navController)
//        }
        viewModel = ViewModelProvider(this,factory)[NewsViewModel::class.java]
    }
}