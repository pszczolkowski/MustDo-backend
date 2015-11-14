package pl.pszczolkowski.mustdo.domain.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.task.entity.Task;

public interface TaskRepository
   extends JpaRepository<Task, Long> {

   List<Task> findAllByTasksListId(Long tasksListId);

   List<Task> findAllByBoardId(Long boardId);

   int removeByTasksListId(Long tasksListId);

   int removeByBoardId(Long boardId);
}
