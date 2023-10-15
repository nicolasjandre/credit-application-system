package jandre.creditapplicationsystem.service

import jandre.creditapplicationsystem.dto.customer.CustomerReqDto
import jandre.creditapplicationsystem.dto.customer.CustomerResDto

interface ICustomerService : ICrudService<CustomerReqDto, CustomerResDto, Long>{

    fun checkIfExists(id: Long): jandre.creditapplicationsystem.entity.Customer
}
