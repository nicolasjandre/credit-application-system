package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.dto.credit.CreditResDto
import jandre.creditapplicationsystem.entity.Credit
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CreditRepository: JpaRepository<Credit, Long>{

    fun findByCreditCode(creditCode: UUID): Optional<Credit>

    @Query(value = "SELECT new jandre.creditapplicationsystem.dto.credit.CreditResDto(c) from Credit c")
    fun findAllPaginated(pageable: Pageable): Page<CreditResDto>

    @Query(value = "SELECT new jandre.creditapplicationsystem.dto.credit.CreditResDto(c) from Credit c WHERE c.customer.id = :customerId")
    fun findAllByCustomerPaginated(pageable: Pageable, customerId: Long): Page<CreditResDto>
}