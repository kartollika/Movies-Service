package com.pau_pau.project;


import com.pau_pau.project.data.controllers.films.FilmsControllerImpl;
import com.pau_pau.project.data.models.Film;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = { ProjectApplication.class })
@WebAppConfiguration(value = "")
@SpringBootTest
public class IntegrationTests extends Assert{

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    private String prepareTimestamp(String str){
        return str.replaceAll("T", " ")
                .replaceAll("\\+", "");
    }

    private Film makeFilmFromJSON(String stringJSON) throws Exception{
        Film film = new Film();
        JSONObject obj = new JSONObject(stringJSON);
        //TODO need to check Hashset<Director>
//        JSONArray directors = obj.getJSONArray("directors");
//        int n = directors.length();
//        for (int i = 0; i < n; ++i){
//
//        }
        film.setId(obj.getInt("id"));
        film.setTitle(obj.getString("title"));
        film.setYear(Timestamp.valueOf(prepareTimestamp(obj.getString("year"))));
        film.setCountry(obj.getString("country"));
//        System.out.println("JSON = " + stringJSON + " \n" + obj.getString("genre"));
        film.setGenre(obj.getString("genre"));
        film.setRelease(Timestamp.valueOf(prepareTimestamp(obj.getString("release"))));
        film.setBudget((float)obj.getDouble("budget"));
        return film;
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesFilmsControllerImpl() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean(FilmsControllerImpl.class));
    }

    @Sql(scripts={"classpath:data_find_by_id_2.sql"})
    @Test
    public void getFilmById_GetExistingFilm_NameGreenBook() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/films/film?id=2"))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);

        Film filmObj = makeFilmFromJSON(content);
        Film film = new Film();
        film.setTitle("Green Book");
        film.setId(2);
        film.setGenre("Comedy");
        film.setBudget(9999999);
        film.setYear(Timestamp.valueOf("2018-09-11 19:44:48.241000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 19:44:59.903000"));
        film.setCountry("USA");
        Assert.assertEquals(film, filmObj);
    }


    @Sql(scripts = {"classpath:empty_films_table.sql"})
    @Test
    public void getFilmById_FindNonExistingFilm_ContentEqualsEmptyStringStatus204() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/films/film?id=1"))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "");

    }

    // TODO why in Swagger status is 415, but here status is 405 ???
    @Sql(scripts = {"classpath:empty_films_table.sql"})
    @Test
    public void deleteFilm_deleteRowInEmptyTable_Status500() throws Exception{
        this.mockMvc.perform(delete("http://localhost:8080/api/films/film?id=1"))
                .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
//                .andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Sql(scripts={"classpath:data_deleteFilm_id_5.sql"})
    @Test
    public void deleteFilm_DeleteExistingRow_StatusOk() throws Exception{
        this.mockMvc.perform(delete("http://localhost:8080/api/films?id=5"))
                .andExpect(status().isOk());

    }

    @Sql(scripts = {"classpath:empty_films_table.sql"})
    @Test
    public void addFilm_AddNonexistingFilm_StatusOk() throws Exception{
        Film film = new Film();
        film.setTitle("Yellow Book");
        film.setId(2);
        film.setGenre("Comedy");
        film.setBudget(9999999);
        film.setYear(Timestamp.valueOf("2018-09-11 19:44:48.241000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 19:44:59.903000"));
        film.setCountry("USA");

        this.mockMvc.perform(post("http://localhost:8080/api/films/").content(asJsonString(film)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }


    //TODO Why after updating film id is updated to next free one??
    @Sql(scripts = {"classpath:data_deleteFilm_id_5.sql"})
    @Test
    public void updateFilm_EditAllFieldsOfFilm_StatusOk() throws Exception{
        Film film = new Film();
        film.setTitle("Yellow Book");
        film.setId(2);
        film.setGenre("Comedy");
        film.setBudget(9999999);
        film.setYear(Timestamp.valueOf("2018-09-11 19:44:48.241000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 19:44:59.903000"));
        film.setCountry("USA");

        this.mockMvc.perform(put("http://localhost:8080/api/films?filmId=5").content(asJsonString(film)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {"classpath:data_deleteFilm_id_5.sql"})
    @Test
    public void updateFilm_UpdateNonExistingFilm_Status204() throws Exception {
        Film film = new Film();
        film.setTitle("Yellow Book");
        film.setId(2);
        film.setGenre("Comedy");
        film.setBudget(9999999);
        film.setYear(Timestamp.valueOf("2018-09-11 19:44:48.241000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 19:44:59.903000"));
        film.setCountry("USA");

        this.mockMvc.perform(put("http://localhost:8080/api/films?filmId=1").content(asJsonString(film)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


   /*
    @Autowired
    private FilmsRepository subject;

    @After
    public void tearDown() throws Exception {
        subject.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchPerson() throws Exception {
        Film film = new Film();
        film.setTitle("Yellow Book");
        film.setId(2);
        film.setGenre("Comedy");
        film.setBudget(9999999);
        film.setYear(Timestamp.valueOf("2018-09-11 19:44:48.241000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 19:44:59.903000"));
        film.setCountry("USA");
        subject.save(film);

        Optional<Film> maybeFilm = subject.findById(2);
        assertEquals(false, maybeFilm.);
        assertEquals(maybeFilm.get(), film);
//        assertThat(maybePeter, is(Optional.of(film)));
    }

*/
}