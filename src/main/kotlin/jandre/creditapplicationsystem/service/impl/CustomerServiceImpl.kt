package jandre.creditapplicationsystem.service.impl

import jandre.creditapplicationsystem.dto.customer.CustomerReqDto
import jandre.creditapplicationsystem.dto.customer.CustomerResDto
import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.entity.Customer
import jandre.creditapplicationsystem.exception.NotFoundException
import jandre.creditapplicationsystem.mapper.CustomerMapper
import jandre.creditapplicationsystem.repository.CustomerRepository
import jandre.creditapplicationsystem.service.IAddressService
import jandre.creditapplicationsystem.service.ICustomerService
import jandre.creditapplicationsystem.utils.JandreUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val addressService: IAddressService,
): ICustomerService {
    override fun save(dto: CustomerReqDto): CustomerResDto {
        val address: Address = addressService.returnFromDbOrElseFromViaCep(dto.address?.cep!!)
        val customer = CustomerMapper.ReqDtoToEntity(dto, address)
        return CustomerMapper.EntityToResDto(customerRepository.save(customer))
    }

    override fun findById(id: Long): CustomerResDto {
        val customer: Customer = checkIfExists(id)
        return CustomerMapper.EntityToResDto(customer)
    }

    override fun findAll(page: Int, size: Int, sort: Sort.Direction?, property: String?): Page<CustomerResDto> {
        val pageable: Pageable = JandreUtils.create(page, size, sort, property)
        return customerRepository.findAllCustomersPaginated(pageable)
    }

    override fun deleteById(id: Long) {
        checkIfExists(id)
        customerRepository.deleteById(id)
    }

    override fun updateById(dto: CustomerReqDto, id: Long): CustomerResDto {
        val existingCustomer: Customer = checkIfExists(id)
        val updatedCustomer: Customer = CustomerMapper.updateExistingCustomerUsingReqDto(dto, existingCustomer)
        return CustomerMapper.EntityToResDto(customerRepository.save(updatedCustomer))
    }

    private fun checkIfExists(id: Long): Customer {
        val customer: Customer = customerRepository.findById(id).orElseThrow {
            NotFoundException("Customer with ID=[$id] not found")
        }
        return customer
    }
}