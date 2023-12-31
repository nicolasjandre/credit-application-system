package jandre.creditapplicationsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CreditApplicationSystemApplication

fun main(args: Array<String>) {
	runApplication<CreditApplicationSystemApplication>(*args)
}
