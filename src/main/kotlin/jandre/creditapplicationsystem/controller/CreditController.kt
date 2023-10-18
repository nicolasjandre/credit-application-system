package jandre.creditapplicationsystem.controller

import jandre.creditapplicationsystem.dto.credit.CreditReqDto
import jandre.creditapplicationsystem.dto.credit.CreditResDto
import jandre.creditapplicationsystem.service.ICreditService
import jandre.creditapplicationsystem.validation.credit.CreditReqDtoPatchValidation
import jandre.creditapplicationsystem.validation.credit.CreditReqDtoSaveValidation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/credit")
class CreditController(
    private val creditService: ICreditService
) {

    @PostMapping
    fun saveCredit(@RequestBody @Validated(CreditReqDtoSaveValidation::class) creditDto: CreditReqDto): ResponseEntity<CreditResDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(creditService.save(creditDto))
    }

    @GetMapping
    fun findAllCreditsPaginated(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam(required = false) sort: Sort.Direction?,
        @RequestParam(required = false) property: String?
    ): ResponseEntity<Page<CreditResDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(creditService.findAll(page, size, sort, property))
    }

    @GetMapping("/customer/{customerId}")
    fun findAllCreditsPaginatedByCustomer(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam(required = false) sort: Sort.Direction?,
        @RequestParam(required = false) property: String?,
        @PathVariable customerId: Long
    ): ResponseEntity<Page<CreditResDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(creditService.findAllByCustomer(page, size, sort, property, customerId))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id")  id: Long
    ): ResponseEntity<CreditResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(creditService.findById(id))
    }

    @GetMapping("/creditCode/{creditCode}")
    fun findByCreditCode(@PathVariable("creditCode")  creditCode: UUID
    ): ResponseEntity<CreditResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(creditService.findByCreditCode(creditCode))
    }

    @PatchMapping("/{id}")
    fun updateById(@RequestBody @Validated(CreditReqDtoPatchValidation::class) dto: CreditReqDto, @PathVariable("id")
     id: Long): ResponseEntity<CreditResDto> {
        return ResponseEntity.status(HttpStatus.OK).body(creditService.updateById(dto, id))
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id")  id: Long): ResponseEntity<CreditResDto> {
        creditService.deleteById(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}