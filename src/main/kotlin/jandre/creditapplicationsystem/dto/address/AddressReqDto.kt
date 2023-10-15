package jandre.creditapplicationsystem.dto.address

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jandre.creditapplicationsystem.validation.AddressReqDtoSaveValidation
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
@Data
data class AddressReqDto(

    @field:NotBlank(groups = [AddressReqDtoSaveValidation::class], message = "Field [cep] can not be blank or null")
    @field:Size(groups = [AddressReqDtoSaveValidation::class], message = "Field [cep] should contain 8 characters", min = 8, max = 8)
    val cep: String?,

    var logradouro: String?,

    var complemento: String?,

    var bairro: String?,

    var localidade: String?,

    var uf: String?,

    var ibge: String?,

    var gia: String?,

    var ddd: String?,

    var siafi: String?
)
