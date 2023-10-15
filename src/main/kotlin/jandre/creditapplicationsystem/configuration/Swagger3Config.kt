package jandre.creditapplicationsystem.configuration

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Swagger3Config {

        @Bean
        fun openAPI(): OpenAPI {
            return OpenAPI()
                .info(Info()
                    .title("Credit System Application")
                    .description("RESTful API made in Kotlin to practice the sintaxe and Spring Boot concepts")
                    .version("1.0")
                    .termsOfService("https://www.github.com/nicolasjandre")
                    .license(License()
                        .name("MIT")
                        .url("https://www.mit.edu/~amini/LICENSE.md")))
                .externalDocs(
                    ExternalDocumentation()
                        .description("Check on GitHub")
                        .url("https://github.com/nicolasjandre/credit-application-system"))
        }
}