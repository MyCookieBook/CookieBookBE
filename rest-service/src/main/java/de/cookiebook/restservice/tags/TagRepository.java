package de.cookiebook.restservice.tags;

import de.cookiebook.restservice.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {

}
