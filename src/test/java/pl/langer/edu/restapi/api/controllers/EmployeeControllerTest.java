package pl.langer.edu.restapi.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.langer.edu.restapi.StartRestApi;
import pl.langer.edu.restapi.services.models.employee.EmployeeInModel;
import pl.langer.edu.restapi.services.models.shared.AddressModel;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Damian Langer on 01.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = {StartRestApi.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class EmployeeControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void findOne() throws Exception {
        this.mockMvc.perform(get("/api/employee/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isNotEmpty())
                .andExpect(jsonPath("content.id").value(1))
                .andExpect(jsonPath("content.name").value("DAMIAN1"))
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("id").description("Employee id (number)")
                                ),
                                responseHeaders(
                                        headerWithName("Content-Type").description("Always "+ MediaType.APPLICATION_JSON_UTF8_VALUE)
                                ),
                                responseFields(
                                        fieldWithPath("source_path").type(JsonFieldType.STRING).description("Request " +
                                                "path"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("Human " +
                                                "readable message"),
                                        fieldWithPath("content").type(JsonFieldType.OBJECT).description("Employee"),
                                        fieldWithPath("content.id").type(JsonFieldType.NUMBER).description("Employee " +
                                                "id"),
                                        fieldWithPath("content.name").type(JsonFieldType.STRING).description
                                                ("Employee name, should be unique"),
                                        fieldWithPath("content.address").type(JsonFieldType.OBJECT).description
                                                ("Employee address").optional(),
                                        fieldWithPath("content.address.street").type(JsonFieldType.STRING).description
                                                ("Street address (without number)").optional(),
                                        fieldWithPath("content.address.street_no").type(JsonFieldType.VARIES)
                                                .description
                                                ("Street number").optional(),
                                        fieldWithPath("content.address.city").type(JsonFieldType.STRING).description
                                                ("City").optional(),
                                        fieldWithPath("content.address.country").type(JsonFieldType.STRING).description
                                                ("Country").optional(),
                                        fieldWithPath("content.email").type(JsonFieldType.STRING).description
                                                ("Employee email address").optional(),
                                        fieldWithPath("content.phone").type(JsonFieldType.STRING).description
                                                ("Employee phone number").optional(),
                                        fieldWithPath("content.offices").type(JsonFieldType.ARRAY).description
                                                ("Employee workplaces collection"),
                                        fieldWithPath("content.offices[].id").type(JsonFieldType.NUMBER).description
                                                ("Office id"),
                                        fieldWithPath("content.offices[].name").type(JsonFieldType.STRING).description
                                                ("Unique office name"),
                                        fieldWithPath("content.offices[].address").type(JsonFieldType.OBJECT)
                                                .description
                                                ("Office address").optional(),
                                        fieldWithPath("content.offices[].address.street").type(JsonFieldType.STRING)
                                                .description
                                                ("Street address (without number)").optional(),
                                        fieldWithPath("content.offices[].address.street_no").type(JsonFieldType.STRING)
                                                .description
                                                        ("Street number").optional(),
                                        fieldWithPath("content.offices[].address.city").type(JsonFieldType.STRING)
                                                .description
                                                ("City").optional(),
                                        fieldWithPath("content.offices[].address.country").type(JsonFieldType.STRING)
                                                .description
                                                ("Country").optional()
                                )));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void addNewEmployee() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        EmployeeInModel newEmployee = new EmployeeInModel();
        newEmployee.setName("new employee");
        newEmployee.setEmail("new@email.pl");
        newEmployee.setAddress(new AddressModel());
        newEmployee.getAddress().setStreet("new street");
        
        this.mockMvc.perform(post("/api/employee")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) 
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("content").value(5))
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseHeaders(
                                        headerWithName("Content-Type").description("Always "+ MediaType.APPLICATION_JSON_UTF8_VALUE)
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description
                                                ("Employee name, should be unique"),
                                        fieldWithPath("address").type(JsonFieldType.OBJECT).description
                                                ("Employee address").optional(),
                                        fieldWithPath("address.street").type(JsonFieldType.VARIES).description
                                                ("Street address (without number)").optional(),
                                        fieldWithPath("address.street_no").type(JsonFieldType.VARIES)
                                                .description
                                                        ("Street number").optional(),
                                        fieldWithPath("address.city").type(JsonFieldType.VARIES).description
                                                ("City").optional(),
                                        fieldWithPath("address.country").type(JsonFieldType.VARIES).description
                                                ("Country").optional(),
                                        fieldWithPath("email").type(JsonFieldType.VARIES).description
                                                ("Employee email address").optional(),
                                        fieldWithPath("phone").type(JsonFieldType.VARIES).description
                                                ("Employee phone number").optional()
                                ),
                                responseFields(
                                        fieldWithPath("source_path").type(JsonFieldType.STRING).description("Request " +
                                                "path"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("Human " +
                                                "readable message"),
                                        fieldWithPath("content").type(JsonFieldType.NUMBER).description("Created " +
                                                "employee id")
                                )));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void findAllEmployees() throws Exception {
        this.mockMvc.perform(get("/api/employees")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isNotEmpty())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content").value(Matchers.hasSize(4)))
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseHeaders(
                                        headerWithName("Content-Type").description("Always "+ MediaType.APPLICATION_JSON_UTF8_VALUE)
                                ),
                                responseFields(
                                        fieldWithPath("source_path").type(JsonFieldType.STRING).description("Request " +
                                                "path"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("Human " +
                                                "readable message"),
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("Employees " +
                                                "collection"),
                                        fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description
                                                ("Employee id"),
                                        fieldWithPath("content[].name").type(JsonFieldType.STRING).description
                                                ("Employee name, should be unique"),
                                        fieldWithPath("content[].email").type(JsonFieldType.STRING).description
                                                ("Employee email address").optional()
                                )));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void updateEmployee() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        EmployeeInModel newEmployee = new EmployeeInModel();
        newEmployee.setName("new employee");
        newEmployee.setEmail("new@email.pl");
        newEmployee.setAddress(new AddressModel());
        newEmployee.getAddress().setStreet("new street");

        this.mockMvc.perform(put("/api/employee/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("content").isEmpty())
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseHeaders(
                                        headerWithName("Content-Type").description("Always "+ MediaType.APPLICATION_JSON_UTF8_VALUE)
                                ),
                                pathParameters(
                                        parameterWithName("id").description("Employee id")
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description
                                                ("Ignored during update process"),
                                        fieldWithPath("address").type(JsonFieldType.OBJECT).description
                                                ("Employee address").optional(),
                                        fieldWithPath("address.street").type(JsonFieldType.VARIES).description
                                                ("Street address (without number)").optional(),
                                        fieldWithPath("address.street_no").type(JsonFieldType.VARIES)
                                                .description
                                                        ("Street number").optional(),
                                        fieldWithPath("address.city").type(JsonFieldType.VARIES).description
                                                ("City").optional(),
                                        fieldWithPath("address.country").type(JsonFieldType.VARIES).description
                                                ("Country").optional(),
                                        fieldWithPath("email").type(JsonFieldType.VARIES).description
                                                ("Employee email address").optional(),
                                        fieldWithPath("phone").type(JsonFieldType.VARIES).description
                                                ("Employee phone number").optional()
                                ),
                                responseFields(
                                        fieldWithPath("source_path").type(JsonFieldType.STRING).description("Request " +
                                                "path"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("Human " +
                                                "readable message"),
                                        fieldWithPath("content").type(JsonFieldType.NULL).description("Always null")
                                )
                        ));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void assignOffice() throws Exception {
        this.mockMvc.perform(post("/api/employee/{id}/workplace/{wid}", 4, 1)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("content").value(true))
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseHeaders(
                                        headerWithName("Content-Type").description("Always "+ MediaType.APPLICATION_JSON_UTF8_VALUE)
                                ),
                                pathParameters(
                                        parameterWithName("id").description("Employee id"),
                                        parameterWithName("wid").description("Office id")
                                ),
                                responseFields(
                                        fieldWithPath("source_path").type(JsonFieldType.STRING).description("Request " +
                                                "path"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("Human " +
                                                "readable message"),
                                        fieldWithPath("content").type(JsonFieldType.BOOLEAN).description("Success " +
                                                "indicator")
                                )
                        ));
    }

}
