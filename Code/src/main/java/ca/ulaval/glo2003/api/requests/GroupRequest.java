package ca.ulaval.glo2003.api.requests;

public class GroupRequest {
	private String name;

	public GroupRequest() {
	}

	public GroupRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
