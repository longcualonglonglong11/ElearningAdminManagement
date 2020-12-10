package com.myclass.socket.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.myclass.dto.RoleMessage;
import com.myclass.entity.Role;
import com.myclass.service.RoleService;
import com.myclass.service.UserInformationService;

@CrossOrigin("*")
@Controller
public class SocketRoleController {
	@Autowired
	private RoleService roleService;
	private String suceedStatus = "success";
	private String errorStatus = "error";
	private String errorAddRole = "ADD ROLE ERROR";
	private String errorDeleteRole = "DELETE ROLE ERROR";
	private String errorEditRole = "DELETE ROLE ERROR";

	private String actionAdd = "role/add";
	private String actionDelete = "role/del";
	private String actionEdit = "role/edit";

// Send to flexible gates
//	@Autowired
//	 private SimpMessagingTemplate simpMessagingTemplate;
	// Final received break point
	@MessageMapping("/add-role")
	// Requesting to this break point again
	@SendTo("/topic/role-topic/add")
	public RoleMessage addNewRole(@Payload RoleMessage roleMsg) {
		roleMsg.setType(actionAdd);
		if (roleService.add(roleMsg.getRole())) {
			roleMsg.setStatus(suceedStatus);
			return roleMsg;

		}
		System.out.println(roleMsg.getSenderId());
		roleMsg.setStatus(errorStatus);
		roleMsg.setError(errorAddRole);
		return roleMsg;

	}
	
	@MessageMapping("/edit-role")
	// Requesting to this break point again
	@SendTo("/topic/role-topic/edit")
	public RoleMessage editARole(@Payload RoleMessage roleMsg) {
		roleMsg.setType(actionEdit);
		if (roleService.update(roleMsg.getRole())) {
			roleMsg.setStatus(suceedStatus);
			return roleMsg;

		}
		System.out.println(roleMsg.getSenderId());
		roleMsg.setStatus(errorStatus);
		roleMsg.setError(errorEditRole);
		return roleMsg;

	}
	@Autowired
	private UserInformationService userInformationService;
	@MessageMapping("/delete-role/{id}")
	@SendTo("/topic/role-topic/delete")
	public RoleMessage deleteARole(@DestinationVariable int id) {
		RoleMessage roleMsg = new RoleMessage();
		roleMsg.setType(actionDelete);
		if (roleService.deleteById(id)) {
			roleMsg.setStatus(suceedStatus);
			Role role = new Role();
			role.setId(id);
			roleMsg.setRole(role);
			Object principal = SecurityContextHolder.getContext().getAuthentication();

			System.out.println("Name: " + principal.toString());
			System.out.println(roleMsg.getSenderId());
		
			return roleMsg;

		}
		roleMsg.setStatus(errorStatus);
		roleMsg.setError(errorDeleteRole);
		return roleMsg;
	}

}
