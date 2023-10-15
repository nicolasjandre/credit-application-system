package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.dto.customer.CustomerResDto
import jandre.creditapplicationsystem.entity.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CustomerRepository: JpaRepository<Customer, Long> {

    @Query(value = "SELECT new jandre.creditapplicationsystem.dto.customer.CustomerResDto(c) from Customer c")
    fun findAllCustomersPaginated(pageable: Pageable): Page<CustomerResDto>
}