package jandre.creditapplicationsystem.service

import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.dto.address.AddressResDto
import jandre.creditapplicationsystem.entity.Address

interface IAddressService : ICrudService<AddressReqDto, AddressResDto, String>{
    fun returnFromDbOrElseFromViaCep(cep: String): Address
}
