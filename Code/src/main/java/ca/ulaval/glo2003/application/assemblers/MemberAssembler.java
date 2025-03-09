package ca.ulaval.glo2003.application.assemblers;

import ca.ulaval.glo2003.api.requests.MemberRequest;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.entities.Member;

public class MemberAssembler {
	public MemberAssembler() {
	}

	public static MemberDto toMemberDto(Member member) {
		return new MemberDto(member);
	}

	public static MemberDto fromRequest(MemberRequest memberRequest) {
		return new MemberDto(memberRequest.getMemberName());
	}
}
