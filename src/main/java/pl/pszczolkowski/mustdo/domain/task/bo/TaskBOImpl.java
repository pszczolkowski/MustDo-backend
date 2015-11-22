package pl.pszczolkowski.mustdo.domain.task.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.Task;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.domain.task.repository.TaskRepository;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class TaskBOImpl
   implements TaskBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(TaskBOImpl.class);
   private final TaskRepository taskRepository;
   private final TasksListRepository tasksListRepository;

   @Autowired
   public TaskBOImpl(TaskRepository taskRepository, TasksListRepository tasksListRepository) {
      this.taskRepository = taskRepository;
      this.tasksListRepository = tasksListRepository;
   }

   @Override
   public TaskSnapshot add(Long tasksListId, String title, String description, Long createdBy) {
      TasksList tasksList = tasksListRepository.findOne(tasksListId);

      Task task = new Task(tasksList, tasksList.toSnapshot().getBoardId(), title, description,createdBy);
      task = taskRepository.save(task);

      tasksList.addTask(task);
      tasksListRepository.save(tasksList);

      TaskSnapshot taskSnapshot = task.toSnapshot();

      LOGGER.info(
         "Created new Task with id <{}>, title <{}> and description <{}> and placed it on TasksList with id <{}>",
         taskSnapshot.getId(), title, description, tasksListId);

      return taskSnapshot;
   }

   @Override
   public void edit(Long id, String title, String description, Long updatedBy) {
      Task task = taskRepository.findOne(id);
      task.edit(title, description, updatedBy);
      taskRepository.save(task);

      LOGGER.info("Changed title to <{}> and description to <{}> for Task with id <{}>", title, description, id);

   }

   @Override
   public void delete(Long id, Long updatedBy) {
	  Task task = taskRepository.findOne(id);
	  task.markAsDeleted(updatedBy);
	  
      taskRepository.save(task);
      LOGGER.info("Deleted Task with id <{}>", id);
   }

   @Override
   public void moveToAntoherTasksList(Long id, Long tasksListId, Long updatedBy) {
      TasksList tasksList = tasksListRepository.findOne(tasksListId);
      Task task = taskRepository.findOne(id);
      
      task.moveToAnotherList(tasksList, updatedBy);
      taskRepository.save(task);

      tasksList.addTask(task);
      tasksListRepository.save(tasksList);

      LOGGER.info("Moved Task with id <{}> to TasksList with id <{}>", id, tasksListId);

   }

}
