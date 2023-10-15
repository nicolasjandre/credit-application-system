package jandre.creditapplicationsystem.dto.credit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jandre.creditapplicationsystem.dto.customer.CustomerResDto
import jandre.creditapplicationsystem.entity.Credit
import jandre.creditapplicationsystem.enums.Status
import jandre.creditapplicationsystem.mapper.CustomerMapper
import lombok.AllArgsConstructor
import lombok.Data
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@AllArgsConstructor
@Data
data class CreditResDto(

    val id: Long,

    val creditCode: UUID,

    val creditValue: BigDecimal,

    val dayFirstInstallment: LocalDate,

    val numberOfInstallments: Int,

    val status: Status,

    @get:JsonIgnoreProperties(value = ["credits"])
    val customer: CustomerResDto,
) {
    @Suppress("unused")
    constructor(credit: Credit) : this(credit.id!!, credit.creditCode!!, credit.creditValue,
        credit.dayFirstInstallment, credit.numberOfInstallments, credit.status!!, CustomerMapper.EntityToResDto(credit.customer!!))
}