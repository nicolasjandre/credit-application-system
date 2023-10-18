package jandre.creditapplicationsystem.controller

import jandre.creditapplicationsystem.dto.customer.CustomerReqDto
import jandre.creditapplicationsystem.dto.customer.CustomerResDto
import jandre.creditapplicationsystem.service.ICustomerService
import jandre.creditapplicationsystem.validation.customer.CustomerReqDtoSaveValidation
import jandre.creditapplicationsystem.validation.customer.CustomerReqDtoUpdateValidation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val customerService: ICustomerService
) {
    @PostMapping
    fun saveCustomer(@RequestBody @Validated(CustomerReqDtoSaveValidation::class) customerDto: CustomerReqDto): ResponseEntity<CustomerResDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerDto))
    }

    @GetMapping
    fun findAllCustomersPaginated(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam(required = false) sort: Sort.Direction?,
        @RequestParam(required = false) property: String?
    ): ResponseEntity<Page<CustomerResDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(page, size, sort, property))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findById(id))
    }

    @PatchMapping("/{id}")
    fun updateById(@RequestBody @Validated(CustomerReqDtoUpdateValidation::class) dto: CustomerReqDto,
                   @PathVariable id: Long): ResponseEntity<CustomerResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateById(dto, id))
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<CustomerResDto> {
        customerService.deleteById(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}