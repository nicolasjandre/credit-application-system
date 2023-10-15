package jandre.creditapplicationsystem.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort

interface ICrudService<ReqDto, ResDto, Id> {

    fun save(dto: ReqDto): ResDto

    fun findById(id: Id): ResDto

    fun findAll(page: Int, size: Int, sort: Sort.Direction?, property: String?): Page<ResDto>

    fun updateById(dto: ReqDto, id: Id): ResDto

    fun deleteById(id: Id)
}