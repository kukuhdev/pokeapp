package com.codelite.pokeapp.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object RecentUtils {
    fun dialogDefault(context: Context, message: String?, title: String? = null) {
        val dialog = MaterialAlertDialogBuilder(context)
        if (!title.isNullOrEmpty()) {
            dialog.setTitle(title)
        } else {
            dialog.setTitle("Pesan")
        }
        dialog.setMessage(message)
        dialog.setPositiveButton("OK", null)
        dialog.show()
    }

    fun getImagePokemon(url: String): String{
        var image = ""
         try {
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + url.split(
                "v2/pokemon/"
            )[1].replace("/", "") + ".png"
        } catch (e: Exception) {
            e.printStackTrace()
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/0.png"
        }
        return image
    }
}