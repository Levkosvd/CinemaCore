package cinema.dao;

import cinema.model.Role;

public interface RoleDao extends GenericDao<Role> {
    Role findByRoleName(String roleName);
}
