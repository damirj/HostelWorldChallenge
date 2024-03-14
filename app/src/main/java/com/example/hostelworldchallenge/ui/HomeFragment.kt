package com.example.hostelworldchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostelworldchallenge.databinding.FragmentHomeBinding
import com.example.hostelworldchallenge.models.PropertyPreview
import com.example.hostelworldchallenge.presenters.HomeContract
import com.example.hostelworldchallenge.presenters.HomeModel
import com.example.hostelworldchallenge.presenters.HomePresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContract.View {

    @Inject
    lateinit var homeModel: HomeModel
    private var homePresenter: HomePresenter? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter: PropertiesAdapter by lazy {
        PropertiesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePresenter = HomePresenter(homeView = this, homeModel = homeModel)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.propertyList.layoutManager = layoutManager
        binding.propertyList.adapter = adapter

        // homePresenter?.screenStarted()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showProgress() {
        // TODO("Not yet implemented")
    }

    override fun hideProgress() {
        // TODO("Not yet implemented")
    }

    override fun displayPropertyList(propertyList: List<PropertyPreview>) {
        adapter.loadData(propertyList.toMutableList())
    }

    override fun displayError(message: String) {
        // TODO("Not yet implemented")
    }
}
