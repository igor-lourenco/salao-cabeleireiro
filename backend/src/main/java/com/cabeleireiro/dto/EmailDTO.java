package com.cabeleireiro.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String fromEmail;
	private String fromName;
	private String replyTo;
	private String to;
	private String subject;
	private String body;
	private String contentType;
		
}
