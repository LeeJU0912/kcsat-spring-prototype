package hpclab.ksatengmaker_spring.admin.repository;

import hpclab.ksatengmaker_spring.admin.domain.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

}
