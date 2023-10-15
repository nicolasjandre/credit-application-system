package jandre.creditapplicationsystem.service.impl

import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.dto.address.AddressResDto
import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.exception.BadRequestException
import jandre.creditapplicationsystem.exception.NotFoundException
import jandre.creditapplicationsystem.integration.ViaCepIntegration
import jandre.creditapplicationsystem.mapper.AddressMapper
import jandre.creditapplicationsystem.repository.AddressRepository
import jandre.creditapplicationsystem.service.IAddressService
import jandre.creditapplicationsystem.utils.JandreUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository,
    private val viaCepIntegration: ViaCepIntegration
): IAddressService {

    override fun save(dto: AddressReqDto): AddressResDto {
        try {
            checkIfExists(dto.cep!!)

            throw BadRequestException("This address already exists")
        } catch (e: NotFoundException) {
            val address: Address = getAddressUsingViaCep(dto.cep!!)
            return AddressMapper.EntityToResDto(addressRepository.save(address))
        }
    }

    override fun findById(id: String): AddressResDto {
        val address = checkIfExists(id)
        return AddressMapper.EntityToResDto(address)
    }

    override fun findAll(page: Int, size: Int, sort: Sort.Direction?, property: String?): Page<AddressResDto> {
        val pageable: Pageable = JandreUtils.create(page, size, sort, property)

        return addressRepository.findAllAdressesPaginated(pageable)
    }

    override fun deleteById(id: String) {
        checkIfExists(id)
        addressRepository.deleteById(id)
    }

    override fun updateById(dto: AddressReqDto, id: String): AddressResDto {
        val existingAddress: Address = checkIfExists(id)
        val newAddress: Address = AddressMapper.patchExistingAddressUsingReqDto(dto, existingAddress)
        return AddressMapper.EntityToResDto(addressRepository.save(newAddress))
    }

    override fun returnFromDbOrElseFromViaCep(cep: String): Address {
        val address: Address = addressRepository.findById(cep).orElseGet {
            return@orElseGet getAddressUsingViaCep(cep)
        }
        return address
    }

    private fun checkIfExists(cep: String): Address {
        val address: Address = addressRepository.findById(cep).orElseThrow{
            throw NotFoundException("CEP [$cep] não encontrado na base de dados")
        }
        return address
    }

    private fun getAddressUsingViaCep(cep: String): Address {
        val address: Address = viaCepIntegration.consultarCep(cep)

        if (address.cep.isNullOrBlank()) {
            throw NotFoundException("CEP [${cep}] não encontrado na base de dados do ViaCep")
        }

        address.cep = address.cep!!.replace("-", "")

        return address
    }
}