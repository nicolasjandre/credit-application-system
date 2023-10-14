package jandre.creditapplicationsystem.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
class Address(

        @Id
        @Column(name = "adr_cd_zip_code", columnDefinition = "VARCHAR(8)")
        val cep: String? = null,

        @Column(name = "ctm_tx_address")
        var logradouro: String? = null,

        @Column(name = "ctm_tx_complement")
        var complemento: String? = null,

        @Column(name = "ctm_tx_neighborhood")
        var bairro: String? = null,

        @Column(name = "ctm_tx_locality")
        var localidade: String? = null,

        @Column(name = "ctm_tx_state", columnDefinition = "VARCHAR(2)")
        var uf: String? = null,

        @Column(name = "ctm_tx_ibge")
        var ibge: String? = null,

        @Column(name = "ctm_tx_gia")
        var gia: String? = null,

        @Column(name = "ctm_tx_ddd")
        var ddd: String? = null,

        @Column(name = "ctm_tx_siafi")
        var siafi: String? = null
)
