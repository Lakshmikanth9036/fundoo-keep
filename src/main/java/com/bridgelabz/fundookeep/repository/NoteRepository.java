package com.bridgelabz.fundookeep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundookeep.dao.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{	

}
