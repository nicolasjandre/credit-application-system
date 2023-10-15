package jandre.creditapplicationsystem.dto.error

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@AllArgsConstructor
@NoArgsConstructor
@Data
data class ErrorResponseDto(
    var message: String,
    var status: HttpStatus,
    var statusCode: Int,
    var timestamp: LocalDateTime = LocalDateTime.now()
)