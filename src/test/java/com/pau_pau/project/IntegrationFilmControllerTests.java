package com.pau_pau.project;

import com.pau_pau.project.models.films.Film;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = { ProjectApplication.class })
@WebAppConfiguration(value = "")
@SpringBootTest
public class IntegrationFilmControllerTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Sql(scripts={"classpath:data_find_by_id_2.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void getFilmById_GetExistingFilm_NameGreenBook() throws Exception{
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/films/film/2"))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);

        Film filmObj = makeFilmFromJSON(content);
        Film film = new Film();
        film.setTitle("Green Book");
        film.setId(2);
        film.setGenre("Comedy");
        film.setYear(Timestamp.valueOf("2018-09-11 00:00:00.000000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 00:00:00.000000"));
        film.setCreationDate(Timestamp.valueOf("2019-05-15 00:00:00.000000"));
        film.setCountry("USA");
        film.setActors("Mahershala Ali");
        film.setDescription("description");
        film.setPoster("poster");
        System.out.println("\nHELLO\n"+ film.getCreationDate() + "  " + filmObj.getCreationDate());
        System.out.println(film.getRelease() + "  " + filmObj.getRelease());
        System.out.println(film.getYear() + "  " + filmObj.getYear());
        Assert.assertEquals(film, filmObj);
    }

    @Sql(scripts = {"classpath:empty_films_table.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void getFilmById_FindNonExistingFilm_ContentEqualsEmptyStringStatus204() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/films/film/1"))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "");
    }

    private static String prepareTimestamp(String str){
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
        System.out.println("OLD = " + obj.getString("year"));
        System.out.println("TIMESTAMP = " + Timestamp.valueOf(prepareTimestamp(obj.getString("year"))));
        film.setYear(Timestamp.valueOf(prepareTimestamp(obj.getString("year"))));
        film.setCountry(obj.getString("country"));
//        System.out.println("JSON = " + stringJSON + " \n" + obj.getString("genre"));
        film.setGenre(obj.getString("genre"));
        film.setRelease(Timestamp.valueOf(prepareTimestamp(obj.getString("release"))));
        return film;
    }

}
