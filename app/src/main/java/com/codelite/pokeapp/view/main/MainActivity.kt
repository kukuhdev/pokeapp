package com.codelite.pokeapp.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.codelite.pokeapp.R
import com.codelite.pokeapp.api.ApiCallback
import com.codelite.pokeapp.databinding.ActivityMainBinding
import com.codelite.pokeapp.decoration.VerticalItemDecoration
import com.codelite.pokeapp.utils.RecentUtils
import com.codelite.pokeapp.view.dialog.LoadingDialog
import com.codelite.pokeapp.view.main.adapter.PokemonAdapter
import com.codelite.pokeapp.view.main.response.MainRepository
import com.codelite.pokeapp.view.main.response.MainViewModel
import com.codelite.pokeapp.view.main.response.PokemonListResponse

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter: PokemonAdapter

    @Suppress("UNCHECKED_CAST")
    private val mainViewModel: MainViewModel by viewModels {
        val repository = MainRepository.getInstance()
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(
                repository
            ) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        initLoadingDialog()
        initRecyclerView()
        getPokemons()
    }

    private fun initRecyclerView() {
        adapter = PokemonAdapter(this, listOf())
        val itemDecoration = VerticalItemDecoration(2)
        binding.rvPokemon.addItemDecoration(itemDecoration)
        binding.rvPokemon.adapter = adapter
    }

    private fun initLoadingDialog() {
        loadingDialog = LoadingDialog.newInstance()
    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, loadingDialog.toString())
    }

    private fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun getPokemons() {
        showLoadingDialog()

        mainViewModel.getPokemons().observe(this) {
            when (it) {
                is ApiCallback.OnSuccess -> {
                    hideLoadingDialog()
                    processData(it.data!!)
                }
                is ApiCallback.OnError -> {
                    hideLoadingDialog()
                    RecentUtils.dialogDefault(this, it.message)
                }
            }
        }
    }

    private fun processData(pokemonListResponse: PokemonListResponse) {
        adapter.items = pokemonListResponse.results!!
        adapter.notifyDataSetChanged()
    }
}