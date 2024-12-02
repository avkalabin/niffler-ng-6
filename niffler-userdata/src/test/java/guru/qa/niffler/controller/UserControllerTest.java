package guru.qa.niffler.controller;

import guru.qa.niffler.data.CurrencyValues;
import guru.qa.niffler.data.UserEntity;
import guru.qa.niffler.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository usersRepository;

  @Test
  void currentUserEndpoint() throws Exception {
    UserEntity userDataEntity = new UserEntity();
    userDataEntity.setUsername("dima");
    userDataEntity.setCurrency(CurrencyValues.RUB);
    usersRepository.save(userDataEntity);

    mockMvc.perform(get("/internal/users/current")
            .contentType(MediaType.APPLICATION_JSON)
            .param("username", "dima")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("dima"));
  }

  @Test
  void allUserEndpoint() throws Exception {
    UserEntity userDataEntity1 = new UserEntity();
    UserEntity userDataEntity2 = new UserEntity();
    userDataEntity1.setUsername("test");
    userDataEntity1.setCurrency(CurrencyValues.RUB);
    userDataEntity2.setUsername("duck");
    userDataEntity2.setCurrency(CurrencyValues.KZT);
    userDataEntity2.setUsername("duck");
    userDataEntity2.setCurrency(CurrencyValues.KZT);

    usersRepository.save(userDataEntity1);
    usersRepository.save(userDataEntity2);

    mockMvc.perform(get("/internal/users/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("username", "test")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username").value("duck"));
  }

  @Test
  void updateEndpoint() throws Exception {
    UserEntity userDataEntity = new UserEntity();
    userDataEntity.setUsername("guru");
    userDataEntity.setCurrency(CurrencyValues.RUB);
    usersRepository.save(userDataEntity);

    mockMvc.perform(post("/internal/users/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"guru\", \"currency\":\"KZT\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currency").value("KZT"));
  }
}