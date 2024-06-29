package com.anurag.repository;

import com.anurag.model.Invitation;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {

    Invitation findByToken(String token);

    Invitation findByEmail(String email);

}
