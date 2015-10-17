package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.user.User;
import stersectas.domain.user.UserId;
import stersectas.domain.user.UserRepository;

public interface HibernateUserRepository extends CrudRepository<User, UserId>, UserRepository {

}