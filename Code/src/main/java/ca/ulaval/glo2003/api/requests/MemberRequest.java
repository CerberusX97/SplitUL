package ca.ulaval.glo2003.api.requests;

public class MemberRequest {
	private String memberName;

	public MemberRequest() {
	}

	public MemberRequest(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberName() {
		return memberName;
	}
}
