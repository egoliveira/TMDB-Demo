package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteLanguage
import br.com.gielamo.tmdb.domain.vo.Language
import javax.inject.Inject

class LanguageMapper @Inject constructor() {
    fun fromRemoteLanguage(remoteLanguage: RemoteLanguage): Language {
        return Language(
            englishName = remoteLanguage.english_name,
            code = remoteLanguage.iso_639_1,
            name = remoteLanguage.name
        )
    }

    fun fromRemoteLanguageList(remoteLanguages: List<RemoteLanguage>): List<Language> {
        return remoteLanguages.map { fromRemoteLanguage(it) }
    }
}