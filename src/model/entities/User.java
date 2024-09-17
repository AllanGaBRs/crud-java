package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String email;
	private String password;
	private String passcode;
	
	public User() {
	}

	public User(Integer id, String name, String email, String password, String passcode) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.passcode = passcode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}
