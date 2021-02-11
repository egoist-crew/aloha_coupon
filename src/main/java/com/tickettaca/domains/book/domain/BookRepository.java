package com.tickettaca.domains.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

  Optional<BookEntity> findByIdAndUserToken(Long bookIndex,String userToken);
}
