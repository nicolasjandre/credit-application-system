package jandre.creditapplicationsystem.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.dto.address.AddressResDto
import jandre.creditapplicationsystem.dto.customer.CustomerReqDto
import jandre.creditapplicationsystem.dto.customer.CustomerResDto
import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.entity.Credit
import jandre.creditapplicationsystem.entity.Customer
import jandre.creditapplicationsystem.exception.NotFoundException
import jandre.creditapplicationsystem.mapper.CustomerMapper
import jandre.creditapplicationsystem.repository.CustomerRepository
import jandre.creditapplicationsystem.utils.JandreUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    lateinit var customerRepository: CustomerRepository

    @MockK
    lateinit var addressService: AddressServiceImpl

    @InjectMockKs
    lateinit var customerService: CustomerServiceImpl

    @Test
    fun `in save should create customer when valid data provided`() {

        //given
        val fakeCustomerDto: CustomerReqDto = buildCustomerReqDto()
        val fakeCustomer: Customer = buildCustomer()
        every { customerRepository.save(any()) } returns fakeCustomer
        every { addressService.returnFromDbOrElseFromViaCep(any()) } returns Address()

        //when
        val actual: CustomerResDto = customerService.save(fakeCustomerDto)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeCustomer)
        verify(exactly = 1) { addressService.returnFromDbOrElseFromViaCep(any()) }
        verify(exactly = 1) { customerRepository.save(any()) }
    }

    @Test
    fun `in findById should return customer when valid id provided`() {

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)

        every { customerRepository.findById(eq(fakeId)) } returns Optional.of(fakeCustomer)

        //when
        val actual: CustomerResDto = customerService.findById(fakeId)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }
    }

    @Test
    fun `in findById should throw NotFoundException when user with id don't exist`() {

        //given
        val fakeId: Long = Random().nextLong()

        every { customerRepository.findById(eq(fakeId)) } returns Optional.empty()

        //when and then
        assertThrows<NotFoundException> { customerService.findById(fakeId) }
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }
    }

    @Test
    fun `in findAll should return a page of customers when page and size provided`() {

        //given
        val page = 0
        val size = 5

        val customers: List<CustomerResDto> = listOf(
            buildCustomerResDto(id = 1),
            buildCustomerResDto(id = 2, firstName = "Marcos"),
            buildCustomerResDto(id = 3, firstName = "Rogério"))

        val pageable: Pageable = JandreUtils.create(page, size, null, null)
        val pageOfCustomers: Page<CustomerResDto> = PageImpl(customers, pageable, customers.size.toLong())

        every { customerRepository.findAllCustomersPaginated(pageable) } returns pageOfCustomers

        //when
        val actual: Page<CustomerResDto> = customerService.findAll(page, size, null, null)

        //then
        assertThat(actual).isNotNull
        assertThat(actual.content).isEqualTo(customers)
        assertThat(actual.totalPages).isEqualTo(1)
        assertThat(actual.numberOfElements).isEqualTo(customers.size)
        verify(exactly = 1) { customerRepository.findAllCustomersPaginated(any()) }
    }

    @Test
    fun `in findAll should throw IllegalArgumentException when page or size is invalid`() {
        // given
        val invalidPage = -1
        val size = 5

        // when & then
        assertThrows<IllegalArgumentException> {
            customerService.findAll(invalidPage, size, null, null)
        }
        verify(exactly = 0) { customerRepository.findAllCustomersPaginated(any()) }
    }

    @Test
    fun `in findAll should return a page of customers sorted in DESC order when sort direction is specified`() {
        // given
        val page = 1
        val size = 5
        val sortDirection = Sort.Direction.DESC
        val property = "firstName"

        val customers: List<CustomerResDto> = listOf(
            buildCustomerResDto(id = 1, firstName = "Alice"),
            buildCustomerResDto(id = 2, firstName = "Bob"),
            buildCustomerResDto(id = 3, firstName = "Charlie")
        )

        val pageable: Pageable = JandreUtils.create(page, size, sortDirection, property)
        val sortedPage: Page<CustomerResDto> = PageImpl(customers, pageable, customers.size.toLong())
        every { customerRepository.findAllCustomersPaginated(any()) } returns sortedPage

        // when
        val actual: Page<CustomerResDto> = customerService.findAll(page, size, sortDirection, property)

        // then
        assertThat(actual.sort).isNotNull
        assertThat(actual.sort.isSorted).isTrue()
        assertThat(actual.sort.getOrderFor(property)?.direction).isEqualTo(sortDirection)
        verify(exactly = 1) { customerRepository.findAllCustomersPaginated(any()) }
    }

    @Test
    fun `in deleteById should delete customer when valid id provided`() {

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer()

        every { customerRepository.findById(eq(fakeId)) } returns Optional.of(fakeCustomer)
        every { customerRepository.deleteById(eq(fakeId)) } just runs

        //when
        val actual = customerService.deleteById(fakeId)

        //then
        assertThat(actual).isSameAs(Unit)
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }
        verify(exactly = 1) { customerRepository.deleteById(eq(fakeId)) }
    }

    @Test
    fun `in deleteById should throw NotFoundException when user with id don't exist`() {

        //given
        val fakeId: Long = Random().nextLong()

        every { customerRepository.findById(eq(fakeId)) } returns Optional.empty()
        every { customerRepository.deleteById(eq(fakeId)) } just runs

        //when and then
        assertThrows<NotFoundException> { customerService.deleteById(fakeId) }
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }
        verify(exactly = 0) { customerRepository.deleteById(eq(fakeId)) }
    }

    @Test
    fun `in updateById should update customer firstName lastName income password email but should not update cpf`() {

        // given
        val fakeId: Long = Random().nextLong()

        //initial data
        val initialFirstName = "Marcos"
        val initialLastName = "Silva"
        val initialIncome = BigDecimal(3000)
        val initialPassword = "567"
        val initialEmail = "marcos@hotmail.com"
        val initialCpf = "12345678912"

        //changed data
        val changedFirstName = "Rodrigo"
        val changedLastName = "Jordan"
        val changedIncome = BigDecimal(4000)
        val changedPassword = "389"
        val changedEmail = "rodrigo@live.com"
        val changedCpf = "98765432198"

        val fakeExistingCustomer: Customer =
            buildCustomer(
                id = fakeId,
                firstName = initialFirstName,
                lastName = initialLastName,
                income = initialIncome,
                password = initialPassword,
                email = initialEmail,
                cpf = initialCpf
            )

        var fakeCustomerReqDto: CustomerReqDto =
            buildCustomerReqDto(
                firstName = changedFirstName,
                lastName = changedLastName,
                income = changedIncome,
                password = changedPassword,
                email = changedEmail,
                cpf = changedCpf
            )

        //initial fields of the customer
        assertThat(fakeExistingCustomer.firstName).isEqualTo(initialFirstName)
        assertThat(fakeExistingCustomer.lastName).isEqualTo(initialLastName)
        assertThat(fakeExistingCustomer.income).isEqualTo(initialIncome)
        assertThat(fakeExistingCustomer.password).isEqualTo(initialPassword)
        assertThat(fakeExistingCustomer.email).isEqualTo(initialEmail)
        assertThat(fakeExistingCustomer.cpf).isEqualTo(initialCpf)

        every { customerRepository.findById(eq(fakeId)) } returns Optional.of(fakeExistingCustomer)

        //here the mapper update the fakeExistingCustomer and return id to a new var
        CustomerMapper.patchExistingCustomerUsingReqDto(fakeCustomerReqDto, fakeExistingCustomer)

        every { customerRepository.save(fakeExistingCustomer) } returns fakeExistingCustomer

        // when
        val actual: CustomerResDto = customerService.updateById(fakeCustomerReqDto, fakeId)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeExistingCustomer)
        verify(exactly = 1) { customerRepository.save(fakeExistingCustomer) }
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }

        //checking if the fields were changed
        assertThat(fakeExistingCustomer.firstName).isEqualTo(changedFirstName)
        assertThat(fakeExistingCustomer.lastName).isEqualTo(changedLastName)
        assertThat(fakeExistingCustomer.income).isEqualTo(changedIncome)
        assertThat(fakeExistingCustomer.password).isEqualTo(changedPassword)
        assertThat(fakeExistingCustomer.email).isEqualTo(changedEmail)

        //the cpf should remain the same
        assertThat(fakeExistingCustomer.cpf).isEqualTo(initialCpf)
        assertThat(fakeExistingCustomer.cpf).isNotEqualTo(changedCpf)
    }

    @Test
    fun `in checkIfExists should return customer when valid id provided`() {

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)

        every { customerRepository.findById(eq(fakeId)) } returns Optional.of(fakeCustomer)

        //when
        val actual: Customer = customerService.checkIfExists(fakeId)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }
    }

    @Test
    fun `in checkIfExists should throw NotFoundException when user with id don't exist`() {

        //given
        val fakeId: Long = Random().nextLong()

        every { customerRepository.findById(eq(fakeId)) } returns Optional.empty()

        //when and then
        assertThrows<NotFoundException> { customerService.checkIfExists(fakeId) }
        verify(exactly = 1) { customerRepository.findById(eq(fakeId)) }
    }

    private fun buildCustomer(
        id: Long = 1,
        firstName: String = "Nicolas",
        lastName: String = "Jandre",
        cpf: String = "19431624877",
        email: String = "jandre@live.com",
        income: BigDecimal = BigDecimal(1850),
        password: String = "123",
        address: Address = Address("25995290")
    ) = Customer(
        id, firstName, lastName, cpf, email, income, password, address
    )

    private fun buildCustomerReqDto(
        firstName: String = "Nicolas",
        lastName: String = "Jandre",
        cpf: String = "19431624877",
        email: String = "jandre@live.com",
        income: BigDecimal = BigDecimal(1850),
        password: String = "123",
        addressReqDto: AddressReqDto = AddressReqDto("25995290")
    ) = CustomerReqDto(
        firstName, lastName, cpf, email, income, password, addressReqDto
    )

    private fun buildCustomerResDto(
        id: Long = 1,
        firstName: String = "Nicolas",
        lastName: String = "Jandre",
        cpf: String = "19431624877",
        email: String = "jandre@live.com",
        address: AddressResDto = AddressResDto("25995290"),
        credits: List<Credit> = mutableListOf()
    ) = CustomerResDto(
        id, firstName, lastName, cpf, email, address, credits
    )
}