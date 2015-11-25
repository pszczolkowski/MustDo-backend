package pl.pszczolkowski.mustdo.web.restapi.file;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.pszczolkowski.mustdo.domain.file.bo.FileBO;
import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;
import pl.pszczolkowski.mustdo.domain.file.finder.FileSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TaskSnapshotFinder;

@Controller
public class FileApi {

   private static final Logger LOGGER = LoggerFactory.getLogger(FileApi.class);
   private static final String[] ACCEPTED_FILES_VALUES = new String[]{
      "image/gif",
      "image/jpg",
      "image/jpeg",
      "image/png",
      ".pdf",
      "application/pdf",
      "text/plain",
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      "application/vnd.ms-excel",
      "application/msword",
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      "application/vnd.ms-powerpoint",
      "application/vnd.openxmlformats-officedocument.presentationml.presentation"

   };
   private static final Set<String> ACCEPTED_FILES = new HashSet<>(Arrays.asList(ACCEPTED_FILES_VALUES));
   private final FileBO fileBO;
   private final FileSnapshotFinder fileSnapshotFinder;
   private final TaskSnapshotFinder taskSnapshotFinder;

   @Autowired
   public FileApi(FileBO fileBO,
      FileSnapshotFinder fileSnapshotFinder, 
      TaskSnapshotFinder taskSnapshotFinder) {
      this.fileBO = fileBO;
      this.fileSnapshotFinder = fileSnapshotFinder;
      this.taskSnapshotFinder = taskSnapshotFinder;
   }

   @ApiOperation(value = "Find File by ID",
      notes = "Returns File based on ID",
      position = 1)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Found File by specified ID"),
      @ApiResponse(code = 400,
         message = "Specified ID is invalid"),
      @ApiResponse(code = 404,
         message = "File not found")})
   @RequestMapping(value = "/file/{fileId}",
      method = RequestMethod.GET)
   public void get(HttpServletResponse response, @PathVariable("fileId") long fileId) {
      FileSnapshot getFile = fileSnapshotFinder.findById(fileId);
      try {
         response.setContentType(getFile.getFileExtension());
         response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
         FileCopyUtils.copy(getFile.getFileByte(), response.getOutputStream());
      } catch (IOException e) {
         LOGGER.error("Unable to return file <{}> because of IOException <{}>",
            fileId, e.getMessage(), e);
      }
   }

   @ApiOperation(value = "Delete existing File [HR, MANAGER]",
      notes = "Returns empty body",
      position = 6)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Remove specified File")})
   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
   @RequestMapping(value = "/file/{fileId}",
      method = RequestMethod.DELETE)
   @ResponseBody
   public HttpEntity<File> remove(@PathVariable("fileId") long fileId) {
      FileSnapshot fileSnapshot = fileSnapshotFinder.findById(fileId);
      if (fileSnapshot == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
         fileBO.delete(fileId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
   }

	@ApiOperation(value = "Upload file",
				  notes = "Returns empty body")
	@ApiResponses(value = { 
			@ApiResponse(code = 200,
					message = "File uploaded"),
			@ApiResponse(code = 400,
					message = "Invalid input") })
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<Void> upload(@Valid @RequestBody FileNew fileNew) throws IOException {
		String[] temp = fileNew.getFile().getOriginalFilename().split(".");
		fileBO.add(fileNew.getFileName(),temp[temp.length-1], fileNew.getFile().getBytes(), fileNew.getTaskId());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Return list of files by TaskId",
			  notes = "Returns list of files")
	@ApiResponses(value = { 
		@ApiResponse(code = 200,
				message = "List of files returned"),
		@ApiResponse(code = 400,
				message = "Invalid input") })
	@RequestMapping(value = "task/{taskId}/file", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(View.Summary.class)
	public HttpEntity<List<File>> list(@PathVariable("taskId") Long taskId) throws IOException {
		TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(taskId);
		if(taskSnapshot == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<FileSnapshot> fileSnapshots = fileSnapshotFinder.findByTaskId(taskId);
		List<File> files = fileSnapshots.stream()
				.map(File::new)
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(files, HttpStatus.OK);
	}
}
