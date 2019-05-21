package com.pau_pau.project;

import com.google.gson.Gson;
import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.films.Film;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = { ProjectApplication.class })
@WebAppConfiguration(value = "")
@SpringBootTest
public class IntegrationFilmControllerTests {

    private static Gson gson = new Gson();
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
        HashSet<Integer> directorsId = new HashSet<>();
        directorsId.add(1);
        film.setDirectorsId(directorsId);
        HashSet<Director> directors = new HashSet<>();
        directors.add(new Director(1, "Peter Farrelly", "USA", null));
        film.setDirectors(directors);

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/films/film/2"))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Film filmObj = makeFilmFromJSON(content);

        Assert.assertEquals(film, filmObj);
    }

    @Sql(scripts = {"classpath:empty_tables.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void getFilmById_FindNonExistingFilm_ContentEqualsEmptyStringStatus204() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/films/film/1"))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(content, "");
    }

    @Sql(scripts={"classpath:data_find_by_id_2.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void deleteFilm_DeleteExistingRow_StatusOk() throws Exception{
        this.mockMvc.perform(delete("http://localhost:8080/api/films/film/2"))
                .andExpect(status().isOk());
    }

    @Sql(scripts = {"classpath:empty_tables.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void deleteFilm_deleteRowInEmptyTable_Status500() throws Exception{

        this.mockMvc.perform(delete("http://localhost:8080/api/films/film/1"))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
//                .andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
    }

    @Sql(scripts = {"classpath:empty_tables.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void addFilm_AddNonexistingFilm_StatusOk() throws Exception{
        Film film = new Film();
        film.setTitle("Yellow Book");
        film.setGenre("Comedy");
        film.setYear(Timestamp.valueOf("2018-09-11 19:44:48.241000"));
        film.setRelease(Timestamp.valueOf("2019-02-25 19:44:59.903000"));
        film.setCountry("USA");

        this.mockMvc.perform(post("http://localhost:8080/api/films/")
                .content(gson.toJson(film))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    /*@Sql(scripts = {"classpath:empty_tables.sql"})
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void addFilm_AddExistingFilm_Status() throws Exception{

    }*/




    private String prepareTimestamp(String str){
        return str.replaceAll("T", " ")
                .replaceAll("\\+", "");
    }

    private Film makeFilmFromJSON(String stringJSON) throws Exception{
        Film film = new Film();
        JSONObject obj = new JSONObject(stringJSON);
        //TODO need to check Hashset<Director>
        HashSet<Director> directors = new HashSet<>();
        JSONArray jsonArray = obj.getJSONArray("directors");
        for (int i = 0; i < jsonArray.length(); ++i){
            JSONObject directorObj = (JSONObject) jsonArray.get(i);
            directors.add(new Director(directorObj.getInt("id"), directorObj.getString("name"), directorObj.getString("country"), null));
        }
        film.setDirectors(directors);

        HashSet<Integer> directorsId = new HashSet<>();
        jsonArray = obj.getJSONArray("directorsId");
        for (int i = 0; i < jsonArray.length(); ++i) {
            directorsId.add(jsonArray.getInt(i));
        }
        film.setDirectorsId(directorsId);
//        JSONArray directors = obj.getJSONArray("directors");
//        int n = directors.length();
//        for (int i = 0; i < n; ++i){
//
//        }
        film.setId(obj.getInt("id"));
        film.setTitle(obj.getString("title"));
        film.setPoster(obj.getString("poster"));
        film.setDescription(obj.getString("description"));
        film.setActors(obj.getString("actors"));
        film.setYear(Timestamp.valueOf(prepareTimestamp(obj.getString("year"))));
        film.setCountry(obj.getString("country"));
        film.setGenre(obj.getString("genre"));
        film.setRelease(Timestamp.valueOf(prepareTimestamp(obj.getString("release"))));
        film.setCreationDate(Timestamp.valueOf(prepareTimestamp(obj.getString("creationDate"))));
        return film;
    }

}
