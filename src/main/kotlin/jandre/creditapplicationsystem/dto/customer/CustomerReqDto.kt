package jandre.creditapplicationsystem.dto.customer

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import jakarta.validation.constraints.Size
import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.validation.AddressReqDtoSaveValidation
import jandre.creditapplicationsystem.validation.customer.CustomerReqDtoSaveValidation
import jandre.creditapplicationsystem.validation.customer.CustomerReqDtoUpdateValidation
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal

@NoArgsConstructor
@AllArgsConstructor
@Data
data class CustomerReqDto(

    // in kotlin we need the @field:annotation
    @field:NotBlank(groups = [CustomerReqDtoSaveValidation::class], message = "Field [firstName] should not be null or empty")
    var firstName: String?,

    @field:NotBlank(groups = [CustomerReqDtoSaveValidation::class], message = "Field [lastName] should not be null or empty")
    var lastName: String?,

    @field:NotBlank(groups = [CustomerReqDtoSaveValidation::class], message = "Field [cpf] should not be null or empty")
    @field:Size(groups = [CustomerReqDtoSaveValidation::class], message = "Field [cpf] should've 11 characters", min = 11, max = 11)
    @field:Null(groups = [CustomerReqDtoUpdateValidation::class], message = "Field [cpf] can not be updated")
    val cpf: String?,

    @field:NotBlank(groups = [CustomerReqDtoSaveValidation::class], message = "Field [email] should not be null or empty")
    var email: String?,

    @field:NotNull(groups = [CustomerReqDtoSaveValidation::class], message = "Field [income] should not be null or empty")
    var income: BigDecimal?,

    @field:NotBlank(groups = [CustomerReqDtoSaveValidation::class], message = "Field [password] should not be null or empty")
    var password: String?,

    var address: AddressReqDto?,
){
    init {
        // Manually trigger validation for the nested AddressReqDto
        if (address != null) {
            val validator = Validation.buildDefaultValidatorFactory().validator
            val violations = validator.validate(address, AddressReqDtoSaveValidation::class.java)
            if (violations.isNotEmpty()) {
                throw ConstraintViolationException(violations)
            }
        }
    }
}