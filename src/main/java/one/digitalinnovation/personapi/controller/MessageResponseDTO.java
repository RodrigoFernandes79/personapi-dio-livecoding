package one.digitalinnovation.personapi.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDTO { // CLasse para gerar tratamento de mensagens com o @Build
	
	private String message;
	

}
