package com.movieland.service;

import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.entity.Poster;
import com.movieland.repository.GenreRepository;
import com.movieland.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParseAndPersist {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    //Accessible through the test
    public void persistMovieList(String posterPath, String moviePath, String genrePath) {
        List<Movie> parsedMovies = parseMovies(posterPath, moviePath);
        List<Genre> parsedGenres = parseGenres(genrePath);
        genreRepository.saveAll(parsedGenres);
        movieRepository.saveAll(parsedMovies);
    }

    public List<Movie> parseMovies(String posterPath, String moviePath) {

        List<Poster> posterList = parsePosters(posterPath);

        List<Movie> movieList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(moviePath));
            String line;
            String rus = null;
            String eng = null;
            String year = null;
            String genre = null;
            String rating = null;
            String price = null;
            while ((line = reader.readLine()) != null) {
                while (line.isEmpty()) {
                    line = reader.readLine();
                }
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                if (line.contains("/")) {
                    String[] dataSplit = line.split("/");
                    rus = dataSplit[0];
                    eng = dataSplit[1];
                    line = reader.readLine();
                    while (line.isEmpty()) {
                        line = reader.readLine();
                    }
                }
                if (line.length() == 4 && line.matches(".*\\d.*")) {
                    year = line;
                    line = reader.readLine();
                    while (line.isEmpty()) {
                        line = reader.readLine();
                    }
                }
                if (line.length() < 60 && Character.isLowerCase(line.codePointAt(0)) && !line.contains(":")) {
                    genre = line;
                    line = reader.readLine();
                    while (line.isEmpty()) {
                        line = reader.readLine();
                    }
                }
                if (line.contains("rating:")) {
                    rating = line.substring(line.indexOf(":") + 1);
                    line = reader.readLine();
                    while (line.isEmpty()) {
                        line = reader.readLine();
                    }
                }
                if (line.contains("price:")) {
                    price = line.substring(line.indexOf(":") + 1);
                }
                Poster posterToSave = null;
                for (Poster poster : posterList) {
                    if (poster.getMovieName().equals(rus)) {
                        posterToSave = poster;
                        break;
                    }
                }
                if (rus != null && eng != null && year != null && genre != null && rating != null && price != null) {

                    Movie movie = Movie.builder()
                            .nameRussian(rus)
                            .nameNative(eng)
                            .yearOfRelease(Integer.parseInt(year))
                            .genre(genre)
                            .rating(Double.valueOf(rating))
                            .price(Double.valueOf(price))
                            .poster(posterToSave)
                            .build();

                    movieList.add(movie);

                    rus = null;
                    eng = null;
                    year = null;
                    genre = null;
                    rating = null;
                    price = null;
                }
            }
            reader.close();
            return movieList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Poster> parsePosters(String path) {

        List<Poster> posterList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            String name;
            String poster_path;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                name = line.substring(0, line.indexOf(" h"));
                poster_path = line.substring(line.indexOf("h"));

                Poster poster = Poster.builder()
                        .movieName(name)
                        .picturePath(poster_path)
                        .build();

                posterList.add(poster);
            }
            reader.close();
            return posterList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Genre> parseGenres(String path) {
        List<Genre> genreList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                while (line.isEmpty()) {
                    line = reader.readLine();
                }
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                Genre genre = Genre.builder()
                        .name(line)
                        .build();

                genreList.add(genre);
            }
            reader.close();
            return genreList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
