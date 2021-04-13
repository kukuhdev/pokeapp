package com.codelite.pokeapp.view.detail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codelite.pokeapp.databinding.FragmentStatsBinding
import com.codelite.pokeapp.decoration.VerticalItemDecoration
import com.codelite.pokeapp.view.detail.adapter.StatAdapter
import com.codelite.pokeapp.view.detail.response.DetailResponse
import com.codelite.pokeapp.view.dialog.LoadingDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder

private const val TAG = "StatsFragment"
private const val INSTANCE_DATA = "INSTANCE_DATA"
private const val INSTANCE_POSITION = "INSTANCE_POSITION"

class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter: StatAdapter
    private lateinit var detailData: DetailResponse

    private var gson: Gson? = null

    // TODO: Rename and change types of parameters
    private var strData: String? = null
    private var intPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gson = GsonBuilder().serializeNulls().create()
        arguments?.let {
            strData = it.getString(INSTANCE_DATA)
            intPosition = it.getInt(INSTANCE_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailData = gson!!.fromJson(strData, DetailResponse::class.java)
        Log.e(TAG, "onViewCreated: ${detailData?.id}")
        initView()
    }

    private fun initView() {
        initLoadingDialog()
        initRecyclerView()
        setData()
    }

    private fun initRecyclerView() {
        adapter = StatAdapter(requireContext(), listOf())
        val itemDecoration = VerticalItemDecoration(2)
        binding.rvStat.addItemDecoration(itemDecoration)
        binding.rvStat.adapter = adapter
    }

    private fun initLoadingDialog() {
        loadingDialog = LoadingDialog.newInstance()
    }

    private fun showLoadingDialog() {
        loadingDialog.show(childFragmentManager, loadingDialog.toString())
    }

    private fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun setData(){
        showLoadingDialog()
        adapter.items = detailData.stats!!
        adapter.notifyDataSetChanged()
        hideLoadingDialog()
    }

    companion object {
        @JvmStatic
        fun newInstance(strData: String, position: Int) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString(INSTANCE_DATA, strData)
                    putInt(INSTANCE_POSITION, position)
                }
            }
    }
}