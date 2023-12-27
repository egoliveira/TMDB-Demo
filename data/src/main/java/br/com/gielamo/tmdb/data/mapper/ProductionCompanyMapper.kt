package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteProductionCompany
import br.com.gielamo.tmdb.domain.vo.ProductionCompany
import javax.inject.Inject

class ProductionCompanyMapper @Inject constructor(private val imageMapper: ImageMapper) {
    fun fromRemoteProductionCompany(
        remoteProductionCompany: RemoteProductionCompany
    ): ProductionCompany {
        return ProductionCompany(
            id = remoteProductionCompany.id,
            logoUrl = imageMapper.fromImagePath(remoteProductionCompany.logo_path),
            name = remoteProductionCompany.name,
            originCountry = remoteProductionCompany.origin_country
        )
    }

    fun fromRemoteProductionCompanyList(
        remoteProductionCompanies: List<RemoteProductionCompany>
    ): List<ProductionCompany> {
        return remoteProductionCompanies.map { fromRemoteProductionCompany(it) }
    }
}