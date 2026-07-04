package com.rivi.truesparrowbrowser.core.utils

import android.util.Patterns
import java.net.URLEncoder

object SearchUtils {

    fun String.toUrlOrSearch(): String {
        val query = this.trim()
        if (query.isBlank()) return query
        if (Patterns.WEB_URL.matcher(query)
                .matches() && (query.startsWith("http://") || query.startsWith("https://"))
        ) {
            return query
        }

        val isDomain =
            query.isNotBlank() && query.contains(".") && Patterns.WEB_URL.matcher(query).matches()
        return if (isDomain) {
            "https://$query"
        } else {
            "https://www.google.com/search?q=${URLEncoder.encode(query, "UTF-8")}"
        }
    }
}


