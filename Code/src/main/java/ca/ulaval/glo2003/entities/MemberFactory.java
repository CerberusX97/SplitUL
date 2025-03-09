package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.application.dtos.MemberDto;

public class MemberFactory {
	public Member createMember(MemberDto memberdto) {
		String memberName = memberdto.getMemberName();
		return new Member(memberName);
	}
}
