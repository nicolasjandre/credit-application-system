package jandre.creditapplicationsystem.mapper

import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.dto.address.AddressResDto
import jandre.creditapplicationsystem.entity.Address

object AddressMapper {

    fun EntityToResDto(address: Address): AddressResDto {
        return AddressResDto(address.cep, address.logradouro, address.complemento, address.bairro,
            address.localidade, address.uf, address.ibge, address.gia, address.ddd, address.siafi)
    }

    fun patchExistingAddressUsingReqDto(reqDto: AddressReqDto, existingEntity: Address?): Address {
        return existingEntity?.apply {
            // Update only non-null fields from the DTO
            cep = reqDto.cep ?: cep
            logradouro = reqDto.logradouro ?: logradouro
            complemento = reqDto.complemento ?: complemento
            bairro = reqDto.bairro ?: bairro
            localidade = reqDto.localidade ?: localidade
            uf = reqDto.uf ?: uf
            ibge = reqDto.ibge ?: ibge
            gia = reqDto.gia ?: gia
            ddd = reqDto.ddd ?: ddd
            siafi = reqDto.siafi ?: siafi
        } ?: Address(reqDto.cep, reqDto.logradouro, reqDto.complemento, reqDto.bairro,
            reqDto.localidade, reqDto.uf, reqDto.ibge, reqDto.gia, reqDto.ddd, reqDto.siafi)
    }
}