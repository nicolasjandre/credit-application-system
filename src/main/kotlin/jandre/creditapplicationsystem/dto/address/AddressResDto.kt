package jandre.creditapplicationsystem.dto.address

import jandre.creditapplicationsystem.entity.Address
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
@Data
data class AddressResDto(

    val cep: String? = null,

    var logradouro: String? = null,

    var complemento: String? = null,

    var bairro: String? = null,

    var localidade: String? = null,

    var uf: String? = null,

    var ibge: String? = null,

    var gia: String? = null,

    var ddd: String? = null,

    var siafi: String? = null
) {
    constructor(address: Address)
            : this(address.cep, address.logradouro, address.complemento,
                address.bairro, address.localidade, address.uf, address.ibge, address.gia,
                address.ddd, address.siafi)
}
