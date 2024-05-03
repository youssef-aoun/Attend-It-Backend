package com.attendit.Attend.It.service.user;

import com.attendit.Attend.It.dao.RoleRepository;
import com.attendit.Attend.It.dao.UserRepository;
import com.attendit.Attend.It.entities.roles.Role;
import com.attendit.Attend.It.entities.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(int id, String roleName) {
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName(roleName);
        roles.add(role);
        Optional<User> result = Optional.ofNullable(userRepository.findUserByIdAndRoles(id, roles));
        return result.orElse(null);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        // Delete entries from attended_by table
        String nativeQuery = "DELETE FROM attended_by WHERE attendee_id = ?";
        Query query = entityManager.createNativeQuery(nativeQuery);
        query.setParameter(1, id);
        query.executeUpdate();

        // Delete user
        userRepository.deleteById(id);
    }



    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<User> page = userRepository.findAll(pageable);
        return page.getContent();
    }

    @Override
    public List<User> findUsersByRole(String roleName, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("firstName").ascending());
        Role role = roleRepository.findRoleByName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Page<User> page = userRepository.findUsersByRoles(roles, pageable);
        return page.getContent();
    }

}