package one.digitalinnovation.personapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovation.personapi.enumerate.PhoneType;

@Data
@Builder
@AllArgsConstructor // cria construtores com todos os argumentos
@NoArgsConstructor  // cria construtores sem os argumentos
@Entity(name="phone")
public class Phone {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(length=250, nullable=false)
	private PhoneType type;
	@Column(length=250, nullable=false)
	private String number;
	

}
