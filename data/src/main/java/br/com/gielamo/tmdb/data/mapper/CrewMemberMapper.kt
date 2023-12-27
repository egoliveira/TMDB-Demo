package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteCrewMember
import br.com.gielamo.tmdb.domain.vo.CrewMember
import javax.inject.Inject

class CrewMemberMapper @Inject constructor(
    private val imageMapper: ImageMapper
) {
    fun fromRemoteCrewMember(remoteCrewMember: RemoteCrewMember): CrewMember {
        return CrewMember(
            adult = remoteCrewMember.adult,
            creditId = remoteCrewMember.credit_id,
            department = remoteCrewMember.department,
            gender = remoteCrewMember.gender,
            id = remoteCrewMember.id,
            job = remoteCrewMember.job,
            knownForDepartment = remoteCrewMember.known_for_department,
            name = remoteCrewMember.name,
            originalName = remoteCrewMember.original_name,
            popularity = remoteCrewMember.popularity,
            profileUrl = imageMapper.fromImagePath(
                remoteCrewMember.profile_path,
                ImageMapper.ImageSize.W300
            )
        )
    }

    fun fromRemoteCrewMemberList(remoteCrewMemberList: List<RemoteCrewMember>): List<CrewMember> {
        return remoteCrewMemberList.map { fromRemoteCrewMember(it) }
    }
}