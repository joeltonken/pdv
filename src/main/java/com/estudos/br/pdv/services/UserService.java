package com.estudos.br.pdv.services;

import com.estudos.br.pdv.dtos.UserDTO;
import com.estudos.br.pdv.dtos.UserResponseDTO;
import com.estudos.br.pdv.entities.User;
import com.estudos.br.pdv.exceptions.NoItemFoundException;
import com.estudos.br.pdv.repositories.UserRepository;
import com.estudos.br.pdv.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserResponseDTO(user.getId(), user.getName(), user.getUsername(),
                        user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user) {
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = modelMapper.map(user, User.class);
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.getUsername(),
                userToSave.getPassword(), userToSave.isEnabled());
    }

    public UserDTO findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent())
            throw new NoItemFoundException("Usuário não encontrado.");

        User user = optionalUser.get();
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword(),
                user.isEnabled());
    }

    public UserDTO update(UserDTO user) {
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = modelMapper.map(user, User.class);

        Optional<User> userToEdit = userRepository.findById(userToSave.getId());

        if (!userToEdit.isPresent())
            throw new NoItemFoundException("Usuário não encontrado.");

        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.getUsername(), userToSave.getPassword(),
                userToSave.isEnabled());
    }

    public void deleteById(long id) {
        if (!userRepository.existsById(id))
            throw new EmptyResultDataAccessException(1);

        userRepository.deleteById(id);
    }

    public User getByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

}
