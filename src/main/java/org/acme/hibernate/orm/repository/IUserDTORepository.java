package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.UserDTO;

import java.util.List;

public interface IUserDTORepository {

    List<UserDTO> findAll();

    void createUserDTO(UserDTO userDTO);

    UserDTO findByUserName(String userName);

}
