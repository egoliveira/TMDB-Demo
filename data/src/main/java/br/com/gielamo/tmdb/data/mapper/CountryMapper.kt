package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteCountry
import br.com.gielamo.tmdb.domain.vo.Country
import javax.inject.Inject

class CountryMapper @Inject constructor() {
    fun fromRemoteCountry(remoteCountry: RemoteCountry): Country {
        return Country(
            code = remoteCountry.iso_3166_1,
            name = remoteCountry.name
        )
    }

    fun fromRemoteCountryList(remoteCountries: List<RemoteCountry>): List<Country> {
        return remoteCountries.map { fromRemoteCountry(it) }
    }
}