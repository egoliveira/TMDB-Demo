package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteCastMember
import br.com.gielamo.tmdb.domain.vo.CastMember
import javax.inject.Inject

class CastMemberMapper @Inject constructor(
    private val imageMapper: ImageMapper
) {
    fun fromRemoteCastMember(remoteCastMember: RemoteCastMember): CastMember {
        return CastMember(
            adult = remoteCastMember.adult,
            castId = remoteCastMember.cast_id,
            character = remoteCastMember.character,
            creditId = remoteCastMember.credit_id,
            gender = remoteCastMember.gender,
            id = remoteCastMember.id,
            knownForDepartment = remoteCastMember.known_for_department,
            name = remoteCastMember.name,
            order = remoteCastMember.order,
            originalName = remoteCastMember.original_name,
            popularity = remoteCastMember.popularity,
            profileUrl = imageMapper.fromImagePath(remoteCastMember.profile_path,
                ImageMapper.ImageSize.W300)
        )
    }

    fun fromRemoteCastMemberList(remoteCastMemberList: List<RemoteCastMember>): List<CastMember> {
        return remoteCastMemberList.map { fromRemoteCastMember(it) }
    }
}