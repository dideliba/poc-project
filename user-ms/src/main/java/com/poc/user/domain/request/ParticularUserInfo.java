package com.poc.user.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Class that represents a user request body for particular user. Same fields as  {@link UserInfo UserInfo.class}
 * but additionally holds the primary key (id)
 *
 * @author didel
 */
@Data
public class ParticularUserInfo extends UserInfo {
	@NotBlank
	private String id;
}
