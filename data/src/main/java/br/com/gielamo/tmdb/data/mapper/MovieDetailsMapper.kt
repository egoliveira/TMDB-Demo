package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteMovieDetails
import br.com.gielamo.tmdb.domain.vo.MovieDetails
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(
    private val imageMapper: ImageMapper,
    private val movieCollectionMapper: MovieCollectionMapper,
    private val genreMapper: GenreMapper,
    private val productionCompanyMapper: ProductionCompanyMapper,
    private val countryMapper: CountryMapper,
    private val languageMapper: LanguageMapper
) {
    fun fromRemoteMovieDetail(remoteMovieDetails: RemoteMovieDetails): MovieDetails {
        return MovieDetails(
            adult = remoteMovieDetails.adult,
            backdropUrl = imageMapper.fromImagePath(remoteMovieDetails.backdrop_path),
            belongsToCollection = movieCollectionMapper.fromRemoteMovieCollection(
                remoteMovieDetails.belongs_to_collection
            ),
            budget = remoteMovieDetails.budget,
            genres = genreMapper.fromRemoteGenreList(remoteMovieDetails.genres),
            homepage = remoteMovieDetails.homepage,
            id = remoteMovieDetails.id,
            imdbId = remoteMovieDetails.imdb_id,
            originalLanguage = remoteMovieDetails.original_language,
            originalTitle = remoteMovieDetails.original_title,
            overview = remoteMovieDetails.overview,
            popularity = remoteMovieDetails.popularity,
            posterUrl = imageMapper.fromImagePath(remoteMovieDetails.poster_path),
            productionCompanies = productionCompanyMapper.fromRemoteProductionCompanyList(
                remoteMovieDetails.production_companies
            ),
            productionCountries = countryMapper.fromRemoteCountryList(
                remoteMovieDetails.production_countries
            ),
            releaseDate = remoteMovieDetails.release_date,
            revenue = remoteMovieDetails.revenue,
            runtime = remoteMovieDetails.runtime,
            spokenLanguages = languageMapper.fromRemoteLanguageList(remoteMovieDetails.spoken_languages),
            status = remoteMovieDetails.status,
            tagline = remoteMovieDetails.tagline,
            title = remoteMovieDetails.title,
            video = remoteMovieDetails.video,
            voteAverage = remoteMovieDetails.vote_average,
            voteCount = remoteMovieDetails.vote_count
        )
    }
}