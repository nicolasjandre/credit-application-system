package jandre.creditapplicationsystem.mapper

import jandre.creditapplicationsystem.dto.credit.CreditReqDto
import jandre.creditapplicationsystem.dto.credit.CreditResDto
import jandre.creditapplicationsystem.entity.Credit
import jandre.creditapplicationsystem.entity.Customer
import jandre.creditapplicationsystem.enums.Status
import java.util.*

object CreditMapper {

    fun ReqDtoToEntity(reqDto: CreditReqDto, customer: Customer): Credit {
        return Credit(null, UUID.randomUUID(), reqDto.creditValue!!, reqDto.dayFirstInstallment!!,
            reqDto.numberOfInstallments!!, Status.IN_PROGRESS, customer)
    }

    fun EntityToResDto(credit: Credit): CreditResDto {
        return CreditResDto(credit.id!!, credit.creditCode!!, credit.creditValue, credit.dayFirstInstallment,
            credit.numberOfInstallments, credit.status!!, CustomerMapper.EntityToResDto(credit.customer!!))
    }

}