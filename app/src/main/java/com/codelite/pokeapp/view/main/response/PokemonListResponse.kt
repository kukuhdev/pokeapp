package com.codelite.pokeapp.view.main.response

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
) {
	data class ResultsItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("url")
		val url: String? = null
	)
}
