package jandre.creditapplicationsystem.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
class Customer(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ctm_cd_id")
        val id: Long,

        @Column(name = "ctm_tx_first_name", nullable = false)
        var firstName: String,

        @Column(name = "ctm_tx_last_name", nullable = false)
        var lastName: String,

        @Column(name = "ctm_tx_cpf", nullable = false, unique = true)
        val cpf: String,

        @Column(name = "ctm_tx_email", nullable = false, unique = true)
        var email: String,

        @Column(name = "ctm_tx_password", nullable = false)
        var password: String,

        @ManyToOne
        @JoinColumn(name = "ctm_fk_address", nullable = false)
        var address: Address = Address(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "customer")
        var credits: List<Credit> = mutableListOf()
)
