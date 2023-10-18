package jandre.creditapplicationsystem.repository

import jandre.creditapplicationsystem.dto.credit.CreditResDto
import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.entity.Credit
import jandre.creditapplicationsystem.entity.Customer
import jandre.creditapplicationsystem.utils.JandreUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer;
    private lateinit var credit1: Credit;
    private lateinit var credit2: Credit;

    @BeforeEach fun setup() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `in findByCreditCode should find credit by credit code`() {

        //given
        val creditCode1 = UUID.randomUUID()
        val creditCode2 = UUID.randomUUID()
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        //when
        val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1).get()
        val fakeCredit2: Credit = creditRepository.findByCreditCode(creditCode2).get()

        assertThat(fakeCredit1).isNotNull
        assertThat(fakeCredit2).isNotNull
        assertThat(fakeCredit1).isSameAs(credit1)
        assertThat(fakeCredit2).isSameAs(credit2)
    }

    @Test
    fun `in findAllByCustomerPaginated should find credit by customer id and return paginated`() {

        //given
        val customerId = 1L;
        val pageable: Pageable = JandreUtils.create(0, 3, null, null);

        //when
        val fakeCreditPage: Page<CreditResDto> = creditRepository.findAllByCustomerPaginated(pageable, customerId)

        assertThat(fakeCreditPage.totalElements).isEqualTo(2)
        assertThat(fakeCreditPage.content[0]).usingRecursiveComparison().isEqualTo(credit1)
        assertThat(fakeCreditPage.content[1]).usingRecursiveComparison().isEqualTo(credit2)
    }

    @Test
    fun `in findAllPaginated should find all credits and return paginated`() {

        //given
        val pageable: Pageable = JandreUtils.create(0, 3, null, null);

        //when
        val allFakeCreditsPage: Page<CreditResDto> = creditRepository.findAllPaginated(pageable)
        val allFakeCredits: List<Credit> = creditRepository.findAll();

        assertThat(allFakeCreditsPage.totalElements.toInt()).usingRecursiveComparison().isEqualTo(allFakeCredits.size)
        assertThat(allFakeCreditsPage.content[0]).usingRecursiveComparison().isEqualTo(credit1)
        assertThat(allFakeCreditsPage.content[1]).usingRecursiveComparison().isEqualTo(credit2)
    }

    private fun buildCredit(
        creditCode: UUID = UUID.randomUUID(),
        creditValue: BigDecimal = BigDecimal(10000),
        dayFirstInstallment: LocalDate = LocalDate.of(2024, 1, 17),
        numberOfInstallments: Int = 12,
        customer: Customer
    ): Credit = Credit(
        null,
        creditCode,
        creditValue,
        dayFirstInstallment,
        numberOfInstallments,
        customer = customer
    )

    private fun buildCustomer(
        firstName: String = "Customer",
        lastName: String = "Teste",
        cpf: String = "12345678912",
        email: String = "teste@customer.com",
        income: BigDecimal = BigDecimal(3000),
        password: String = "123",
        address: Address = Address(cep = "25995290")
    ): Customer = Customer(
        null, firstName, lastName, cpf, email, income, password, address
    )
}