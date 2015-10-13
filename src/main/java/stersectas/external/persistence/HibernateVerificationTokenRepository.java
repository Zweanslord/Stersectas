package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.token.VerificationToken;
import stersectas.domain.token.VerificationTokenRepository;

public interface HibernateVerificationTokenRepository extends CrudRepository<VerificationToken, Long>, VerificationTokenRepository {

}