package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.application.dtos.DebtDto;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonPropertyOrder({"memberName", "debts"})
public class MemberResponse {

	private String memberName;
	private Map<String, Double> debts;

	public MemberResponse() {
	}
	public MemberResponse(MemberDto memberdto) {
		this.memberName = memberdto.getMemberName();
		this.debts = memberdto.getDebts().stream()
				.collect(Collectors.toMap(DebtDto::getMemberNameDto, DebtDto::getAmountDto));
	}

	public String getMemberName() {
		return memberName;
	}

	public Map<String, Double> getDebts() {
		return debts;
	}

	public List<MemberResponse> memberResponses(List<MemberDto> memberDtos) {
		List<MemberResponse> response = new ArrayList<>();
		response.addAll(memberDtos.stream().map(MemberResponse::new).toList());
		return response;
	}

}
