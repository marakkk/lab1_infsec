package com.infosec.lab1.repo;

import com.infosec.lab1.model.DataItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataItem, Long> {
}
