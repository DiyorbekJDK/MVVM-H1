package org.diyorbek.mvvm_h1.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.button.MaterialButton
import org.diyorbek.mvvm_h1.R
import org.diyorbek.mvvm_h1.activity.MainActivity
import org.diyorbek.mvvm_h1.adapter.PhotoAdapter
import org.diyorbek.mvvm_h1.databinding.FragmentHomeBinding
import org.diyorbek.mvvm_h1.model.PhotoItem
import org.diyorbek.mvvm_h1.model.PhotoResponse
import org.diyorbek.mvvm_h1.network.RetroInstance
import org.diyorbek.mvvm_h1.util.NetworkSupport
import org.diyorbek.mvvm_h1.util.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { (activity as MainActivity).viewModel }
    private val photoAdapter by lazy { PhotoAdapter() }
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
        allCode()
    }

    private fun allCode() {

        photoAdapter.onClick = {
            val bundle = bundleOf("id" to it)
            findNavController().navigate(R.id.action_navigation_home_to_detailFragment,bundle)
        }


        binding.recyclerView3.apply {
            adapter = photoAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        val networkHelper = NetworkSupport(requireContext())
        if (networkHelper.isInternetAvailable()) {
            loadData()
        } else {
            showAlertDialog()
        }
    }

    private fun checkInternet() {
        val network = NetworkSupport(requireContext())
        if (network.isInternetAvailable()) {
            loadData()
            snackBar("Online mode")
        } else {
            snackBar("Offline mode")
            showAlertDialog()
        }
    }

    private fun loadData() {
        RetroInstance.retrofitInstance().getAllPhotos().enqueue(object : Callback<PhotoResponse> {
            override fun onResponse(
                call: Call<PhotoResponse>,
                response: Response<PhotoResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.isVisible = false
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

    private fun showAlertDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.offline_mode_dialog_layout, null)
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setView(dialogView)
        val btn: MaterialButton = dialogView.findViewById(R.id.btnRefresh)
        val btn2: MaterialButton = dialogView.findViewById(R.id.btnExit)
        btn2.setOnClickListener {
            activity?.finish()
            alertDialog.dismiss()
        }
        btn.setOnClickListener {
            checkInternet()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}