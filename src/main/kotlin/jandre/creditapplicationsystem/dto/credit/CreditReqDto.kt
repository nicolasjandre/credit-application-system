package jandre.creditapplicationsystem.dto.credit

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jandre.creditapplicationsystem.enums.Status
import jandre.creditapplicationsystem.validation.credit.CreditReqDtoPatchValidation
import jandre.creditapplicationsystem.validation.credit.CreditReqDtoSaveValidation
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal
import java.time.LocalDate


@AllArgsConstructor
@NoArgsConstructor
@Data
data class CreditReqDto(

    @field:NotNull(groups = [CreditReqDtoSaveValidation::class], message = "Field [creditValue] should not be null")
    val creditValue: BigDecimal?,

    @field:NotNull(groups = [CreditReqDtoSaveValidation::class], message = "Field [dayFirstInstallment] should not be null")
    @field:Future(groups = [CreditReqDtoSaveValidation::class], message = "Field [dayFirstInstallment] should contain a future date")
    val dayFirstInstallment: LocalDate?,

    @field:NotNull(groups = [CreditReqDtoSaveValidation::class], message = "Field [numberOfInstallments] should not be null")
    @field:Min(groups = [CreditReqDtoSaveValidation::class], message = "Field [numberOfInstallments] should be between 1 and 48", value = 1)
    @field:Max(groups = [CreditReqDtoSaveValidation::class], message = "Field [numberOfInstallments] should be between 1 and 48", value = 48)
    val numberOfInstallments: Int?,

    @field:NotNull(groups = [CreditReqDtoPatchValidation::class], message = "Field [numberOfInstallments] should not be blank or null")
    val status: Status?,

    @field:NotNull(groups = [CreditReqDtoSaveValidation::class], message = "Field [customerId] should not be null")
    val customerId: Long?,
)