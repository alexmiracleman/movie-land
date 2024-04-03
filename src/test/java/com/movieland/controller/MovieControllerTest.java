package com.movieland.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovieControllerTest extends AbstractBaseITest {

    //ENDPOINTS
    private static final String ALL_MOVIES_API = "/api/v1/movies";
    private static final String ALL_MOVIES_RATING_DESC_API = "/api/v1/movies?rating=desc";
    private static final String ALL_MOVIES_PRICE_ASC_API = "/api/v1/movies?price=asc";
    private static final String ALL_MOVIES_PRICE_DESC_API = "/api/v1/movies?price=desc";
    private static final String ALL_MOVIES_BY_GENRE_CRIME_API = "/api/v1/movies/genre/2";
    private static final String ALL_MOVIES_BY_GENRE_DRAMA_PRICE_ASC_API = "/api/v1/movies/genre/1?price=asc";
    private static final String ALL_MOVIES_BY_GENRE_DRAMA_PRICE_DESC_API = "/api/v1/movies/genre/1?price=desc";
    private static final String ALL_MOVIES_BY_GENRE_DRAMA_RATING_DESC_API = "/api/v1/movies/genre/1?rating=desc";
    private static final String FIND_MOVIE_BY_ID_API = "/api/v1/movies/1";

    //JSONs
    private static final String ALL_MOVIES_JSON = "response/movies/all-movies.json";
    private static final String ALL_MOVIES_RATING_DESC_JSON = "response/movies/all-movies-rating-desc.json";
    private static final String ALL_MOVIES_PRICE_ASC_JSON = "response/movies/all-movies-price-asc.json";
    private static final String ALL_MOVIES_PRICE_DESC_JSON = "response/movies/all-movies-price-desc.json";
    private static final String ALL_MOVIES_BY_GENRE_CRIME_JSON = "response/movies/all-crime-movies.json";
    private static final String ALL_MOVIES_BY_GENRE_DRAMA_PRICE_ASC_JSON = "response/movies/all-drama-movies-price-asc.json";
    private static final String ALL_MOVIES_BY_GENRE_DRAMA_PRICE_DESC_JSON = "response/movies/all-drama-movies-price-desc.json";
    private static final String ALL_MOVIES_BY_GENRE_DRAMA_RATING_DESC_JSON = "response/movies/all-drama-movies-rating-desc.json";
    private static final String FIND_MOVIE_BY_ID_ONE_JSON = "response/movies/movie-by-id-1.json";



    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void getAllMovies() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_JSON)));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMoviesRatingDesc() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_RATING_DESC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_RATING_DESC_JSON), true));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMoviesPriceAsc() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_PRICE_ASC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_PRICE_ASC_JSON), true));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMoviesPriceDesc() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_PRICE_DESC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_PRICE_DESC_JSON), true));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetThreeRandomMovies() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_PRICE_DESC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_PRICE_DESC_JSON)));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testMoviesByGenre() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_BY_GENRE_CRIME_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_BY_GENRE_CRIME_JSON)));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testMoviesByGenrePriceAscending() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_BY_GENRE_DRAMA_PRICE_ASC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_BY_GENRE_DRAMA_PRICE_ASC_JSON), true));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testMoviesByGenrePriceDescending() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_BY_GENRE_DRAMA_PRICE_DESC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_BY_GENRE_DRAMA_PRICE_DESC_JSON), true));
    }

    @Test
    @DataSet(value = "datasets/movies_genres.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testMoviesByGenreRatingDescending() throws Exception {

        mockMvc.perform(get(ALL_MOVIES_BY_GENRE_DRAMA_RATING_DESC_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(ALL_MOVIES_BY_GENRE_DRAMA_RATING_DESC_JSON), true));
    }

    @Test
    @DataSet(value = "datasets/movies_genres_countries_reviews_users.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetMovieById() throws Exception {

        mockMvc.perform(get(FIND_MOVIE_BY_ID_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(FIND_MOVIE_BY_ID_ONE_JSON), true));
    }

}
