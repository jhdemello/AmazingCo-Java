package org.springframework.samples.petclinic.amazingco;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Repository class for <code>Employee</code> domain objects All method names are
 * compliant with Spring Data naming conventions so this interface can easily be extended
 * for Spring Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Jason deMello
 */
public interface EmployeeRepository extends Repository<Employee, Integer> {

	/**
	 * Retrieve all <code>Employee</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Employee</code>s
	 */
	@Transactional(readOnly = true)
	@Cacheable("amazingco")
	Collection<Employee> findAll() throws DataAccessException;

	/**
	 * Retrieve all <code>Employee</code>s from data store in Pages
	 * @param pageable
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	@Cacheable("amazingco")
	Page<Employee> findAll(Pageable pageable) throws DataAccessException;

	;

}
