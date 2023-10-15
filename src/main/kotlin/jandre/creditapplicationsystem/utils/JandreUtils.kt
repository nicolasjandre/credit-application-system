package jandre.creditapplicationsystem.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

object JandreUtils {

    fun create(page: Int, size: Int, sort: Sort.Direction?, property: String?): Pageable {
        return sort?.let {
            PageRequest.of(page, size, Sort.by(it, property))
        } ?: PageRequest.of(page, size)
    }
}