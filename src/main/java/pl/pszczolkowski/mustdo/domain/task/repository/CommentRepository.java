package pl.pszczolkowski.mustdo.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.task.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
