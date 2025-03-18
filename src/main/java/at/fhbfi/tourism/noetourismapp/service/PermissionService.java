package at.fhbfi.tourism.noetourismapp.service;

import at.fhbfi.tourism.noetourismapp.model.Permission;
import at.fhbfi.tourism.noetourismapp.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }


    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }


    public Optional<Permission> findPermissionById(Long id) {
        return permissionRepository.findById(id);
    }


    public Optional<Permission> findPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }


    public void deletePermissionById(Long id) {

        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
        }
    }


    public boolean existsPermissionById(Long id) {
      return permissionRepository.existsById(id);
    }
}