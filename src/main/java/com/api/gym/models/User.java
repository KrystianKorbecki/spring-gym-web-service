package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "user", schema = "public",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "user_name"),
			@UniqueConstraint(columnNames = "email_address")
		})
public class User extends RepresentationModel<User>
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "user_name")
	private String userName;

	@NotBlank
	@Size(max = 20)
	@Column(name = "last_name")
	private String lastName;

	@NotBlank
	@Size(max = 15)
	@Column(name = "phone_number")
	private String phoneNumber;

	@NotBlank
	@Size(max = 50)
	@Column(name = "profile_name")
	private String profileName;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email_address")
	private String email;

	@Column(name = "email_confirm")
	private Boolean confirmEmail;

	@Column(name = "confirmation_code")
	private String confirmationCode;

	@Column(name = "chat_code")
	private String chatCode;

	@NotBlank
	@Size(max = 240)
	private String password;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "id_trainer")
	private Long idTrainer;

	@Column(name = "id_admin")
	private Long idAdmin;

	@Column(name = "active")
	private Boolean active;

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday_date")
	private Date birthdayDate;

	@Column(name = "gender")
	private String gender;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "role_users",
				joinColumns = @JoinColumn(name = "id_user"),
				inverseJoinColumns = @JoinColumn(name = "id_role"))
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_ticket",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_ticket"))
	private List<Ticket> ticket = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<TrainingPlan> trainingPlans = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Schedule> schedules = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Message> messages = new ArrayList<>();
}
