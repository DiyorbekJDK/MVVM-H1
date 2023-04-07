package org.diyorbek.mvvm_h1.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import org.diyorbek.mvvm_h1.R
import org.diyorbek.mvvm_h1.activity.MainActivity
import org.diyorbek.mvvm_h1.adapter.PhotoAdapter
import org.diyorbek.mvvm_h1.databinding.FragmentDetailBinding
import org.diyorbek.mvvm_h1.databinding.FragmentHomeBinding
import org.diyorbek.mvvm_h1.model.PhotoItem
import org.diyorbek.mvvm_h1.model.PhotoResponse
import org.diyorbek.mvvm_h1.network.RetroInstance
import org.diyorbek.mvvm_h1.util.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment(R.layout.fragment_detail) {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    private val viewModel by lazy { (activity as MainActivity).viewModel }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getParcelable<PhotoItem>("id")
        Log.d("@@@", "ID: ${id.toString()}")

        RetroInstance.retrofitInstance().getOnePhoto(id?.id  ?: 0).enqueue(object : Callback<PhotoItem>{
            override fun onResponse(call: Call<PhotoItem>, response: Response<PhotoItem>) {
                Log.d("@@@", "RESPONCE : ${response.errorBody().toString()}")

                if (response.isSuccessful){
                    snackBar("asdasdadsda")
                    Glide.with(binding.img)
                        .load(response.body()?.src?.original)
                        .into(binding.img)

                }else{
                    snackBar("Something went wrong")
                }
            }

            override fun onFailure(call: Call<PhotoItem>, t: Throwable) {
                snackBar(t.message.toString())
                Log.d("@@@", "onFailure: ${t.message.toString()}")
            }
        })

    }


}