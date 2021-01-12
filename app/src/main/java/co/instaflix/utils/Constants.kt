package co.instaflix.utils



class Constants {

    interface keys {
        companion object {
            const val CONTENT_ID = "CONTENT_ID"
            const val SERIES_DETAIL = "SERIES_DETAIL"
            const val MOVIES_DETAIL = "MOVIES_DETAIL"
        }
    }

    interface categories {
        companion object {
            const val POPULAR = "popular"
            const val ON_AIR = "on_the_air"
            const val TOP = "top_rated"
        }
    }
}