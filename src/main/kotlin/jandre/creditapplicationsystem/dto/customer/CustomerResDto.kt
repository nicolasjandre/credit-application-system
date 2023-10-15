package jandre.creditapplicationsystem.dto.customer

import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.entity.Credit
import jandre.creditapplicationsystem.entity.Customer
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
@Data
data class CustomerResDto(

    val id: Long,

    var firstName: String,

    var lastName: String,

    val cpf: String,

    var email: String,

    var address: Address,

    var credits: List<Credit>

) {
    constructor(customer: Customer)
            : this(customer.id!!, customer.firstName, customer.lastName,
        customer.cpf, customer.email, customer.address, customer.credits)
}