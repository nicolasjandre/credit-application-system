package jandre.creditapplicationsystem.entity

import jakarta.persistence.*
import jandre.creditapplicationsystem.enums.Status
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "credit")
@AllArgsConstructor
@NoArgsConstructor
@Data
class Credit (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "crd_cd_id")
        val id: Long,

        @Column(name = "crd_uid_credit_code", nullable = false, unique = true)
        val creditCode: UUID = UUID.randomUUID(),

        @Column(name = "crd_big_credit_value", nullable = false)
        val creditValue: BigDecimal = BigDecimal.ZERO,

        @Column(name = "crd_dt_day_first_installment", nullable = false)
        val dayFirstInstallment: LocalDate,

        @Column(name = "crd_int_number_of_installments", nullable = false)
        val numberOfInstallments: Int = 0,

        @Enumerated
        @Column(name = "crd_enm_status", nullable = false)
        val status: Status = Status.IN_PROGRESS,

        @ManyToOne
        @JoinColumn(name = "crd_fk_customer", nullable = false)
        val customer: Customer? = null,
)
