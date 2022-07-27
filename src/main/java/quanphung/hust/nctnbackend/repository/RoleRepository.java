package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findRoleByName(String name);
}