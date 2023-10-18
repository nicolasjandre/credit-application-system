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
    var cep: String? = null,

    var logradouro: String? = null,

    var complemento: String? = null,

    var bairro: String? = null,

    var localidade: String? = null,

    var uf: String? = null,

    var ibge: String? = null,

    var gia: String? = null,

    var ddd: String? = null,

    var siafi: String? = null
)
