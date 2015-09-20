package stersectas.application;

import stersectas.domain.User;

public interface IUserService {
	User registerNewUser(UserDto userDto);
}
