package jandre.creditapplicationsystem.controller

import jakarta.validation.constraints.Size
import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.dto.address.AddressResDto
import jandre.creditapplicationsystem.service.IAddressService
import jandre.creditapplicationsystem.validation.AddressReqDtoSaveValidation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/address")
@Validated
class AddressController(
    private val addressService: IAddressService
) {

    @PostMapping
    fun saveCustomer(@RequestBody @Validated(AddressReqDtoSaveValidation::class) addressDto: AddressReqDto): ResponseEntity<AddressResDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(addressDto))
    }

    @GetMapping
    fun findAllAddressesPaginated(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam(required = false) sort: Sort.Direction?,
        @RequestParam(required = false) property: String?
    ): ResponseEntity<Page<AddressResDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll(page, size, sort, property))
    }

    @GetMapping("/{cep}")
    fun findById(@PathVariable("cep") @Size(min = 8, max = 8, message = "CEP should've 8 characters") cep: String
    ): ResponseEntity<AddressResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(cep))
    }

    @PatchMapping("/{cep}")
    fun updateById(@RequestBody dto: AddressReqDto, @PathVariable("cep")
    @Size(min = 8, max = 8, message = "CEP should've 8 characters") cep: String): ResponseEntity<AddressResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.updateById(dto, cep))
    }

    @DeleteMapping("/{cep}")
    fun deleteById(@PathVariable("cep") @Size(min = 8, max = 8,
        message = "CEP should've 8 characters") cep: String): ResponseEntity<AddressResDto> {
        addressService.deleteById(cep)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}