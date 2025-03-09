package ca.ulaval.glo2003.api.resources;

import ca.ulaval.glo2003.api.requests.ExpenseRequest;
import ca.ulaval.glo2003.api.requests.GroupRequest;
import ca.ulaval.glo2003.api.requests.MemberRequest;
import ca.ulaval.glo2003.api.responses.GroupResponse;
import ca.ulaval.glo2003.api.responses.MemberResponse;
import ca.ulaval.glo2003.application.assemblers.ExpenseAssembler;
import ca.ulaval.glo2003.application.assemblers.GroupAssembler;
import ca.ulaval.glo2003.application.assemblers.MemberAssembler;
import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import ca.ulaval.glo2003.application.dtos.GroupDto;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.application.services.GroupService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/groups")
public class GroupResource {
	private final GroupService groupService = new GroupService();

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGroup(GroupRequest groupRequest) {
		GroupDto groupDto = GroupAssembler.fromRequest(groupRequest);
		groupService.CreateGroup(groupDto);
		URI location = UriBuilder.fromPath("/groups/{name}").resolveTemplate("name", groupDto.getNameGroupDto())
				.build();
		return Response.created(location).build();
	}

	@DELETE
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGroup(@PathParam("name") String name) {
		GroupDto groupDto = new GroupDto(name);
		groupService.deleteGroup(groupDto);
		return Response.noContent().build();
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllGroups() {
		List<GroupDto> groupsDto = groupService.getAllGroups();
		return Response.ok(new GroupResponse().groupsResponses(groupsDto)).build();
	}

	@GET
	@Path("/{groupName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroup(@PathParam("groupName") String groupName) {
		GroupDto groupDto = groupService.getGroup(groupName);
		return Response.ok(new GroupResponse(groupDto)).build();
	}

	@Path("/{groupName}/members")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMember(MemberRequest memberRequest, @PathParam("groupName") String groupName) {
		MemberDto memberDto = MemberAssembler.fromRequest(memberRequest);
		groupService.addMember(groupName, memberDto);
		URI location = UriBuilder.fromPath("/groups/{groupName}/members/{memberName}")
				.resolveTemplate("groupName", groupName).resolveTemplate("memberName", memberDto.getMemberName())
				.build();
		return Response.created(location).build();
	}

	@GET
	@Path("/{groupName}/members")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMembers(@PathParam("groupName") String groupName,
			@HeaderParam("Member") String requestingMember) {
		List<MemberDto> memberDtos = groupService.getAllMembersOfGroup(groupName, requestingMember);
		return Response.ok(new MemberResponse().memberResponses(memberDtos)).build();
	}

	@POST
	@Path("/{groupName}/expenses")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createExpense(@PathParam("groupName") String groupName, ExpenseRequest expenseRequest) {
		GroupDto groupDto = new GroupDto(groupName);
		ExpenseDto expenseDto = ExpenseAssembler.fromRequest(expenseRequest);
		UUID id = groupService.addExpense(groupDto, expenseDto);
		URI location = UriBuilder.fromPath("/groups/{groupName}/expenses/{id}").resolveTemplate("id", id)
				.resolveTemplate("groupName", groupName).build();
		return Response.created(location).build();
	}
}
