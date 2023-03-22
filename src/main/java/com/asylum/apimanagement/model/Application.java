package com.asylum.apimanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.*;

@Entity
@Table(name = "applications", uniqueConstraints = {
		@UniqueConstraint(name = " uniques", columnNames = { "first_name", "last_name", "birth_date" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	@NotBlank(message = " First name is mandatory")
	@Size(max = 20, message = " First name must be less than 20 characters")
	private String firstName;

	@Column(name = "middle_name")
	@Size(max = 20, message = " Middle name must be less than 20 characters")
	private String middleName;

	@NotBlank(message = " Last name is mandatory")
	@Size(max = 20, message = " Last name must be less than 20 characters")
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "birth_date")
	@Past(message = "Birth date must be in the past")
	private Date birthDate;

	@Column(name = "birth_country")
	private String birthCountry;

	@Column(name = "citizen_country")
	private String citizenCountry;

	@Column(name = "phone")
	@Size(min = 10, message = " Phone number must be at least 10 digits")
	@Digits(fraction = 0, integer = 12, message = " Phone number must be a number with less than 12 digits")
	@NotBlank(message = " Phone number is mandatory")
	private String phone;

	@Email(message = " Email must be an email ID")
	@Column(name = "email")
	private String email;

	@Column(name = "current_address")
	private String address;
	@Column(name = "race")
	private String race;

	@Column(name = "height")
	@Min(value = 0, message = "Height must be a positive integer in cm")
	@NotNull(message = " Height is mandatory and must be an integer")
	private Integer height;

	@Column(name = "eye_color")
	private String eyeColor;
	@Column(name = "hair_color")
	private String hairColor;
	@Column(name = "degree")
	private String degree;
	@Column(name = "degree_name")
	private String degreeName;
	@Column(name = "adv_overthrow")
	private boolean advOverthrow;
	@Column(name = "reli_persecuted")
	private boolean reliPersecuted;
	@Column(name = "nazi")
	private boolean nazi;
	@Column(name = "crimes")
	private boolean crimes;

	public Application(String firstName, String middleName, String lastName, String gender, Date birthDate,
			String birthCountry, String citizenCountry, String phone, String email, String address, String race,
			int height, String eyeColor, String hairColor, String degree, String degreeName, boolean advOverthrow,
			boolean reliPersecuted, boolean nazi, boolean crimes) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.birthCountry = birthCountry;
		this.citizenCountry = citizenCountry;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.race = race;
		this.height = height;
		this.eyeColor = eyeColor;
		this.hairColor = hairColor;
		this.degree = degree;
		this.degreeName = degreeName;
		this.advOverthrow = advOverthrow;
		this.reliPersecuted = reliPersecuted;
		this.nazi = nazi;
		this.crimes = crimes;
	}

}
