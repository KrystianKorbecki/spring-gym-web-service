package com.api.gym.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse extends RepresentationModel<MessageResponse>
{
	private String message;

}
