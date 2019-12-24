package aop.demo.jetpack.android.databingding.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import aop.demo.jetpack.android.databingding.R
import aop.demo.jetpack.android.databingding.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity
        val sharePreferences = ViewModelProviders.of(this, SavedStateViewModelFactory((context as Activity).application, this))
                .get(ViewModelSharePreferences::class.java)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val bind = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


        bind.data = homeViewModel
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
////            textView.text = it
//        })
        return bind.root
    }
}