package jandre.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jandre.creditapplicationsystem.dto.address.AddressReqDto
import jandre.creditapplicationsystem.dto.customer.CustomerReqDto
import jandre.creditapplicationsystem.entity.Address
import jandre.creditapplicationsystem.entity.Customer
import jandre.creditapplicationsystem.mapper.CustomerMapper
import jandre.creditapplicationsystem.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerControllerTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var addressRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    companion object {
        const val URL = "/customer"
    }

    @BeforeEach
    fun setupCustomer() = customerRepository.deleteAll()

    @BeforeEach
    fun setupAddress() = addressRepository.deleteAll()
    @AfterEach
    fun tearDownCustomer() = customerRepository.deleteAll()

    @AfterEach
    fun tearDownAddress() = addressRepository.deleteAll()

    @Test
    fun `in saveCustomer should create a customer and return 201 created`() {
        val customerReqDto: CustomerReqDto = buildCustomerReqDto()
        val customerReqDtoAsString: String = objectMapper.writeValueAsString(customerReqDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerReqDtoAsString))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.firstName").value("Nicolas"))
            .andExpect(jsonPath("$.lastName").value("Jandre"))
            .andExpect(jsonPath("$.cpf").value("19431624877"))
            .andExpect(jsonPath("$.email").value("jandre@live.com"))
            .andExpect(jsonPath("$.address.cep").value("25995290"))
    }

    @Test
    fun `in saveCustomer when cpf already exists should throw DataIntegrityViolationException and return 409 conflict`() {
        customerRepository.save(CustomerMapper.ReqDtoToEntity(buildCustomerReqDto(), Address(cep = "25995290")))
        val customerReqDto: CustomerReqDto = buildCustomerReqDto()
        val customerReqDtoAsString: String = objectMapper.writeValueAsString(customerReqDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerReqDtoAsString))
            .andExpect(status().isConflict)
    }

    @Test
    fun `in saveCustomer when firstName is empty should throw MethodArgumentNotValidException and return 400 bad request`() {
        val customerReqDto: CustomerReqDto = buildCustomerReqDto(firstName = "")
        val customerReqDtoAsString: String = objectMapper.writeValueAsString(customerReqDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerReqDtoAsString))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("[Field [firstName] should not be null or empty]"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.timestamp").exists())
    }

    @Test
    fun `in findById when given valid id should return the customer and 200 ok`() {
        val customerReqDto: CustomerReqDto = buildCustomerReqDto()
        val customer: Customer = customerRepository.save(CustomerMapper.ReqDtoToEntity(customerReqDto, Address(cep = "25995290")))

        mockMvc.perform(MockMvcRequestBuilders.get("${URL}/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.firstName").value("Nicolas"))
            .andExpect(jsonPath("$.lastName").value("Jandre"))
            .andExpect(jsonPath("$.cpf").value("19431624877"))
            .andExpect(jsonPath("$.email").value("jandre@live.com"))
            .andExpect(jsonPath("$.address.cep").value("25995290"))
    }

    @Test
    fun `in findById when given an id that don't exist should throw NotFoundException and return 404`() {
        mockMvc.perform(MockMvcRequestBuilders.get("${URL}/2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("Customer with ID=[2] not found"))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
            .andExpect(jsonPath("$.statusCode").value(404))
    }

    @Test
    fun `in delete when given valid id should return 204 no content`() {
        val customerReqDto: CustomerReqDto = buildCustomerReqDto()
        val customer: Customer = customerRepository.save(CustomerMapper.ReqDtoToEntity(customerReqDto, Address(cep = "25995290")))

        mockMvc.perform(MockMvcRequestBuilders.delete("${URL}/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `in delete when given an id that don't exist should throw NotFoundException and return 404`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("${URL}/2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("Customer with ID=[2] not found"))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
            .andExpect(jsonPath("$.statusCode").value(404))
    }

    @Test
    fun `in updateById when trying to update cpf should throw bad request and return 400`() {
        val customerReqDto: CustomerReqDto = buildCustomerReqDto()
        val customerReqDtoAsString: String = objectMapper.writeValueAsString(customerReqDto)

        mockMvc.perform(MockMvcRequestBuilders.patch("${URL}/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerReqDtoAsString))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("[Field [cpf] can not be updated]"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.statusCode").value(400))
    }

    @Test
    fun `in updateById when id doesn't exists should throw NotFoundException and return 404`() {
        val customerReqDto: CustomerReqDto = buildCustomerReqDto(cpf = null)
        val customerReqDtoAsString: String = objectMapper.writeValueAsString(customerReqDto)

        mockMvc.perform(MockMvcRequestBuilders.patch("${URL}/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerReqDtoAsString))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("Customer with ID=[2] not found"))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
            .andExpect(jsonPath("$.statusCode").value(404))
    }

    @Test
    fun `in updateById when id exists should update the user and return 200 ok`() {
        customerRepository.save(buildCustomer())
        val customerReqDto: CustomerReqDto = buildCustomerReqDto(cpf = null)
        val customerReqDtoAsString: String = objectMapper.writeValueAsString(customerReqDto)

        mockMvc.perform(MockMvcRequestBuilders.patch("${URL}/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerReqDtoAsString))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("Nicolas"))
            .andExpect(jsonPath("$.lastName").value("Jandre"))
            .andExpect(jsonPath("$.cpf").value("99999999999"))
            .andExpect(jsonPath("$.email").value("jandre@live.com"))
            .andExpect(jsonPath("$.address.cep").value("25995290"))
            .andExpect(jsonPath("$.address.logradouro").doesNotExist())
            .andExpect(jsonPath("$.credits").isArray())
            .andExpect(jsonPath("$.credits").isEmpty())

    }

    private fun buildCustomerReqDto(
        firstName: String = "Nicolas",
        lastName: String = "Jandre",
        cpf: String? = "19431624877",
        email: String = "jandre@live.com",
        income: BigDecimal = BigDecimal(1850),
        password: String = "123",
        addressReqDto: AddressReqDto = AddressReqDto("25995290")
    ) = CustomerReqDto(
        firstName, lastName, cpf, email, income, password, addressReqDto
    )

    private fun buildCustomer(
        firstName: String = "Romario",
        lastName: String = "Silva",
        cpf: String = "99999999999",
        email: String = "romario@live.com",
        income: BigDecimal = BigDecimal(3000),
        password: String = "1234",
        address: Address = Address("25995290")
    ) = Customer(
        null, firstName, lastName, cpf, email, income, password, address
    )
}