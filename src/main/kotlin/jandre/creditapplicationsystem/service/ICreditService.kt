package jandre.creditapplicationsystem.service

import jandre.creditapplicationsystem.dto.credit.CreditReqDto
import jandre.creditapplicationsystem.dto.credit.CreditResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import java.util.*

interface ICreditService : ICrudService<CreditReqDto, CreditResDto, Long> {

    fun findAllByCustomer(page: Int, size: Int, sort: Sort.Direction?, property: String?, customerId: Long): Page<CreditResDto>

    fun findByCreditCode(creditCode: UUID): CreditResDto
}
