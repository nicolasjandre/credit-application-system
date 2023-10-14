package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.entity.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<Address, String> {
}