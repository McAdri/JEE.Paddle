package data.daos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import data.entities.Token;
import data.entities.User;

public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByUser(User user);
    
    @Transactional
    Long deleteByUser(User user);
}
