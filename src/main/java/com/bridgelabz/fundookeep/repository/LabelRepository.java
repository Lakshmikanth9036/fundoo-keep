package com.bridgelabz.fundookeep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundookeep.dao.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>{

}
