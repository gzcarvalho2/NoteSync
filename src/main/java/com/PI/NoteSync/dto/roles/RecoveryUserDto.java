package com.PI.NoteSync.dto.roles;

import com.PI.NoteSync.entity.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
) {
}