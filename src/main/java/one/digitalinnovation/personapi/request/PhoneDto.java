package one.digitalinnovation.personapi.request;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovation.personapi.enumerate.PhoneType;


@Data
@Builder
@AllArgsConstructor // cria construtores com todos os argumentos
@NoArgsConstructor  // cria construtores sem os argumentos

public class PhoneDto {
	
	private Long id;
	@Enumerated(EnumType.STRING)
	private PhoneType type;
	
	@NotEmpty
	@Size(min =13, max =14)
	private String number;
	

}
