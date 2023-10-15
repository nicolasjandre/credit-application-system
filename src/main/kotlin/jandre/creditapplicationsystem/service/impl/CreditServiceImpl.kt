package jandre.creditapplicationsystem.service.impl

import jandre.creditapplicationsystem.dto.credit.CreditReqDto
import jandre.creditapplicationsystem.dto.credit.CreditResDto
import jandre.creditapplicationsystem.entity.Credit
import jandre.creditapplicationsystem.exception.NotFoundException
import jandre.creditapplicationsystem.mapper.CreditMapper
import jandre.creditapplicationsystem.repository.CreditRepository
import jandre.creditapplicationsystem.service.ICreditService
import jandre.creditapplicationsystem.service.ICustomerService
import jandre.creditapplicationsystem.utils.JandreUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditServiceImpl(
    private val creditRepository: CreditRepository,
    private val customerService: ICustomerService
) : ICreditService {
    override fun findAllByCustomer(page: Int, size: Int, sort: Sort.Direction?, property: String?, customerId: Long): Page<CreditResDto> {
        customerService.checkIfExists(customerId)
        val pageable: Pageable = JandreUtils.create(page, size, sort, property)
        return creditRepository.findAllByCustomerPaginated(pageable, customerId)
    }

    override fun findByCreditCode(creditCode: UUID): CreditResDto {
        val credit: Credit = creditRepository.findByCreditCode(creditCode).orElseThrow{
            throw NotFoundException("Credit of ID [$id] not found")
        }
        return CreditMapper.EntityToResDto(credit)
    }

    override fun save(dto: CreditReqDto): CreditResDto {
        val customer = customerService.checkIfExists(dto.customerId!!)
        val newCredit: Credit = CreditMapper.ReqDtoToEntity(dto, customer)
        return CreditMapper.EntityToResDto(creditRepository.save(newCredit))
    }

    override fun findById(id: Long): CreditResDto {
        val credit: Credit = checkIfExistsAndReturn(id)
        return CreditMapper.EntityToResDto(credit)
    }

    override fun findAll(page: Int, size: Int, sort: Sort.Direction?, property: String?): Page<CreditResDto> {
        val pageable: Pageable = JandreUtils.create(page, size, sort, property)
        return creditRepository.findAllPaginated(pageable)
    }

    override fun updateById(dto: CreditReqDto, id: Long): CreditResDto {
        val existingCredit: Credit = checkIfExistsAndReturn(id)
        existingCredit.status = dto.status ?: existingCredit.status
        return CreditMapper.EntityToResDto(creditRepository.save(existingCredit))
    }

    override fun deleteById(id: Long) {
        checkIfExistsAndReturn(id)
        creditRepository.deleteById(id)
    }

    private fun checkIfExistsAndReturn(id: Long): Credit {
        return creditRepository.findById(id).orElseThrow{
            throw NotFoundException("Credit of ID [$id] not found")
        }
    }
}