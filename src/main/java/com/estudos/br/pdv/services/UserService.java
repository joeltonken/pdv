package com.estudos.br.pdv.services;

import com.estudos.br.pdv.dtos.UserDTO;
import com.estudos.br.pdv.entities.User;
import com.estudos.br.pdv.exceptions.NoItemFoundException;
import com.estudos.br.pdv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user) {
        User userToSave = new User();
        userToSave.setEnabled(user.isEnabled());
        userToSave.setName(user.getName());
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
    }

    public UserDTO findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent())
            throw new NoItemFoundException("Usuário não encontrado.");

        User user = optionalUser.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO update(UserDTO user) {
        User userToSave = new User();
        userToSave.setEnabled(user.isEnabled());
        userToSave.setName(user.getName());
        userToSave.setId(user.getId());

        Optional<User> userToEdit = userRepository.findById(userToSave.getId());

        if (!userToEdit.isPresent())
            throw new NoItemFoundException("Usuário não encontrado.");

        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
    }

    public void deleteById(long id) {
        if (!userRepository.existsById(id))
            throw new EmptyResultDataAccessException(1);

        userRepository.deleteById(id);
    }

}
