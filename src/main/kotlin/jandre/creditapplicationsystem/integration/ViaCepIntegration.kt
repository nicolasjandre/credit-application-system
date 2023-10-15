package jandre.creditapplicationsystem.integration

import jandre.creditapplicationsystem.entity.Address
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
fun interface ViaCepIntegration {

    @GetMapping("/{cep}/json/")
    fun consultarCep(@PathVariable("cep") cep: String): Address
}