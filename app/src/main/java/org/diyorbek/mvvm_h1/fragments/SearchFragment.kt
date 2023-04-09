package org.diyorbek.mvvm_h1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.diyorbek.mvvm_h1.R
import org.diyorbek.mvvm_h1.adapter.PhotoAdapter
import org.diyorbek.mvvm_h1.databinding.FragmentDashboardBinding
import org.diyorbek.mvvm_h1.model.PhotoResponse
import org.diyorbek.mvvm_h1.network.RetroInstance
import org.diyorbek.mvvm_h1.util.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val photoAdapter by lazy { PhotoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCode()
    }

    private fun allCode() {

        photoAdapter.onClick = {
            val bundle = bundleOf("id" to it.id)
            findNavController().navigate(R.id.action_navigation_dashboard_to_detailFragment, bundle)
        }
        binding.rv2.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.btn.setOnClickListener {
            loadData()
        }

    }

    private fun loadData() {
        RetroInstance.retrofitInstance().searchPhoto(binding.input.text.toString())
            .enqueue(object : Callback<PhotoResponse> {
                override fun onResponse(
                    call: Call<PhotoResponse>,
                    response: Response<PhotoResponse>
                ) {
                    if (response.isSuccessful) {
                        binding.progressBar2.isVisible = false
                        photoAdapter.submitList(response.body()?.photos!!)


                    } else {
                        Log.d("@@@Response error", "onResponse: ${response.errorBody().toString()}")
                    }
                }

                override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                    snackBar("There is some error")
                    Log.d("@@@Response error", "onFailure: ${t.message}")
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}