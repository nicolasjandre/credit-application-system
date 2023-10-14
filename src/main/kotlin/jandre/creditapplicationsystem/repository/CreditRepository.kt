package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository

interface CreditRepository: JpaRepository<Credit, Long>{
}