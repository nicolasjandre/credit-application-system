package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
}