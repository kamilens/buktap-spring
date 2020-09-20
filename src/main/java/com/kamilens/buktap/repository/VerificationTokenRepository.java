package com.kamilens.buktap.repository;

import com.kamilens.buktap.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    List<VerificationToken> findAllByCreationDateBefore(Date date);

    void deleteAllByCreationDateBefore(Date date);

}
