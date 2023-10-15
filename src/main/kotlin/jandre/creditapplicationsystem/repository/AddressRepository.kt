package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.dto.address.AddressResDto
import jandre.creditapplicationsystem.entity.Address
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AddressRepository: JpaRepository<Address, String> {

    @Query(value = "SELECT new jandre.creditapplicationsystem.dto.address.AddressResDto(a) from Address a")
    fun findAllAdressesPaginated(pageable: Pageable): Page<AddressResDto>
}