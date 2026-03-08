package com.diego.iatrainig.service.impl;

import com.diego.iatrainig.domain.User;
import com.diego.iatrainig.dto.UserRequest;
import com.diego.iatrainig.dto.UserResponse;
import com.diego.iatrainig.exception.ResourceNotFoundException;
import com.diego.iatrainig.repository.UserRepository;
import com.diego.iatrainig.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest request) {
        // (1) Reject duplicate emails before touching the database
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered: " + request.email());
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        // (2) Plain text for now — will be replaced with BCrypt when security is added
        user.setPassword(request.password());
        user.setLevel(request.level());
        user.setRole(request.role());
        user.setWeightKg(request.weightKg());
        user.setHeightCm(request.heightCm() != null ? request.heightCm().intValue() : null);
        user.setAge(request.age());
        user.setRegisteredAt(LocalDateTime.now());

        return toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(String id) {
        return toResponse(findById(id));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(String id, UserRequest request) {
        User user = findById(id);

        // (3) Allow email change only if the new email is not taken by another user
        if (!user.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered: " + request.email());
        }

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setLevel(request.level());
        user.setRole(request.role());
        user.setWeightKg(request.weightKg());
        user.setHeightCm(request.heightCm() != null ? request.heightCm().intValue() : null);
        user.setAge(request.age());

        return toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        // (4) Check existence first so we get a 404 instead of a silent no-op
        findById(id);
        userRepository.deleteById(id);
    }

    // --- private helpers ---

    private User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    // (5) Never exposes the password field
    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLevel(),
                user.getRole(),
                user.getWeightKg(),
                user.getHeightCm() != null ? user.getHeightCm().doubleValue() : null,
                user.getAge(),
                user.getRegisteredAt()
        );
    }
}
