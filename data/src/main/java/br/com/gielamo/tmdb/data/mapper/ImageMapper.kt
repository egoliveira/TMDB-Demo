package br.com.gielamo.tmdb.data.mapper

import javax.inject.Inject

class ImageMapper @Inject constructor() {
    fun fromImagePath(path: String?, size: ImageSize = ImageSize.ORIGINAL): String? {
        return if (!path.isNullOrBlank()) {
            String.format(
                TMDB_IMAGE_URL_TEMPLATE, size.sizeName, path
            )
        } else null
    }

    companion object {
        private const val TMDB_IMAGE_URL_TEMPLATE = "https://image.tmdb.org/t/p/%1\$s%2\$s"
    }

    enum class ImageSize(val sizeName: String) {
        ORIGINAL("original"), W185("w185"), W300("w300")
    }
}