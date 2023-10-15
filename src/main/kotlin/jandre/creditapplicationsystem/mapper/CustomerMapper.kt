package jandre.creditapplicationsystem.mapper

import jandre.creditapplicationsystem.dto.customer.CustomerReqDto
import jandre.creditapplicationsystem.dto.customer.CustomerResDto
import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.entity.Customer

object CustomerMapper {

    fun ReqDtoToEntity(reqDto: CustomerReqDto, address: Address): Customer {
        return Customer(null, reqDto.firstName!!, reqDto.lastName!!, reqDto.cpf!!, reqDto.email!!, reqDto.income!!, reqDto.password!!, address, mutableListOf())
    }

    fun EntityToResDto(customer: Customer): CustomerResDto {
        return CustomerResDto(customer.id!!, customer.firstName, customer.lastName, customer.cpf, customer.email, customer.address, customer.credits)
    }

    fun updateExistingCustomerUsingReqDto(reqDto: CustomerReqDto, existingEntity: Customer): Customer {
        return existingEntity.apply {
            // Update only non-null fields from the DTO
            firstName = reqDto.firstName ?: firstName
            lastName = reqDto.lastName ?: lastName
            email = reqDto.email ?: email
            income = reqDto.income ?: income
            password = reqDto.password ?: password
        }
    }
}