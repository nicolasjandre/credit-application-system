package jandre.creditapplicationsystem.service

import java.util.*

interface ICreditService<Req, Res> {
    fun save(credit: Req): Res

    fun findAllByCustomer(customerId: Long): List<Res>

    fun findByCreditCode(creditCode: UUID): Res
}
