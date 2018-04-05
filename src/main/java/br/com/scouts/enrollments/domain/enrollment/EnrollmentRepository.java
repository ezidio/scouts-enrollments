package br.com.scouts.enrollments.domain.enrollment;

import org.springframework.data.repository.CrudRepository;


public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {

}
