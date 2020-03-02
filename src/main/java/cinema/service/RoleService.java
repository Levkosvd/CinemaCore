package cinema.service;

import cinema.model.Role;

public interface RoleService {

    Role add(Role entity);

    Role findById(Long id);

    Role findByRoleName(String roleName);
}
