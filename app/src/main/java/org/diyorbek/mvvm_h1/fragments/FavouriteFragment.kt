package org.diyorbek.mvvm_h1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.diyorbek.mvvm_h1.R
import org.diyorbek.mvvm_h1.activity.MainActivity
import org.diyorbek.mvvm_h1.adapter.PhotoAdapter
import org.diyorbek.mvvm_h1.databinding.FragmentNotificationsBinding
import org.diyorbek.mvvm_h1.util.snackBar

class FavouriteFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val photoAdapter by lazy { PhotoAdapter() }
    private val viewModel by lazy { (activity as MainActivity).viewModel }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv3.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getAllNotes().observe(viewLifecycleOwner) {
            photoAdapter.submitList(it)
        }
        photoAdapter.onClick = {
            val bundle = bundleOf("id" to it)
            findNavController().navigate(
                R.id.action_navigation_notifications_to_detailFragment,
                bundle
            )
        }
        photoAdapter.onLongClick = {
            viewModel.deletePhoto(it)
            snackBar("Deleted!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}