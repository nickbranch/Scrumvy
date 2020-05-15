
package com.wescrum.scrumvy.repos;


import com.wescrum.scrumvy.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatusRepository extends JpaRepository <Status, Integer> {
    
    
}
