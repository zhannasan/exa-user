package dev.domains;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author janka
 *
 */
@Entity
public class Userexa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty
	@Size(min = 2)
	private String username;
	@NotEmpty
	@Size(min = 5)
	private String password;
	@NotEmpty
	@Size(min = 5)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role roles;
	/**
	 * 
	 */
	public Userexa() {
	}
	public Userexa(@NotEmpty @Size(min = 2) String username, @NotEmpty @Size(min = 5) String password,
			@NotEmpty @Size(min = 5) String email, Role roles) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRoles() {
		return roles;
	}
	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
}
