package at.fhbfi.tourism.noetourismapp.service;

import at.fhbfi.tourism.noetourismapp.model.Role;
import at.fhbfi.tourism.noetourismapp.model.Permission;
import at.fhbfi.tourism.noetourismapp.repository.RoleRepository;
import at.fhbfi.tourism.noetourismapp.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public void deleteRoleById(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        }
    }

    public Role addPermissionToRole(Long roleId, Long permissionId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);

        if (roleOpt.isPresent() && permissionOpt.isPresent()) {
            Role role = roleOpt.get();
            Permission permission = permissionOpt.get();
            role.getPermissions().add(permission);
            return roleRepository.save(role);
        }
        return null;
    }

    public Role removePermissionFromRole(Long roleId, Long permissionId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);

        if (roleOpt.isPresent() && permissionOpt.isPresent()) {
            Role role = roleOpt.get();
            Permission permission = permissionOpt.get();
            role.getPermissions().remove(permission);
            return roleRepository.save(role);
        }
        return null;
    }

    public boolean existsRoleById(Long id) {
        return roleRepository.existsById(id);
    }
}
