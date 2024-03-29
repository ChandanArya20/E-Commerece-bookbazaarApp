package in.ineuron.dto;

import java.util.List;

import in.ineuron.models.Address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserResponse {
	
	Long id;

	String name;
	
	String phone;
	
	String email;
	
	List<Address> address;
	
}
