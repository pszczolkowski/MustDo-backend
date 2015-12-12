package pl.pszczolkowski.mustdo.web.restapi.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;

@RestController
@RequestMapping("/user")
public class UserApi {
	private UserSnapshotFinder userSnapshotFinder;
	
	@Autowired
	public UserApi(UserSnapshotFinder userSnapshotFinder){
		this.userSnapshotFinder = userSnapshotFinder;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<User>> list(){
		List<UserSnapshot> userSnapshots = userSnapshotFinder.findAll();
		List<User> users = userSnapshots
				.stream()
				.map(User::new)
				.collect(Collectors.toList());
		
		return ResponseEntity
				.ok()
				.body(users);
	}
}
