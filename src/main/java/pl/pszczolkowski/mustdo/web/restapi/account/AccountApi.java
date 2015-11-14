package pl.pszczolkowski.mustdo.web.restapi.account;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.pszczolkowski.mustdo.domain.user.bo.UserBO;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;

@RestController
@RequestMapping("/account")
public class AccountApi {

	private final UserSnapshotFinder userSnapshotFinder;
	private final UserBO userBO;
	private final Validator userRegisterValidator;

	@Autowired
	public AccountApi(UserSnapshotFinder userSnapshotFinder, UserBO userBO, @Qualifier("accountRegisterValidator") Validator userRegisterValidator) {
		this.userSnapshotFinder = userSnapshotFinder;
		this.userBO = userBO;
		this.userRegisterValidator = userRegisterValidator;
	}
	
	@InitBinder("accountRegister")
	protected void initNewBinder(WebDataBinder binder) {
		binder.setValidator(userRegisterValidator);
	}

	private UserSnapshot getLoggedUserSnapshot() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return userSnapshotFinder.findByLogin(username);
	}

	@ApiOperation(value = "Create new account", notes = "Returns login and id of created account")
	@ApiResponses({ @ApiResponse(code = 200, message = "Account created") })
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
	public HttpEntity<Account> register(@Valid @RequestBody AccountRegister accountNew) {
		UserSnapshot userSnapshot = userBO.register(accountNew.getLogin(), accountNew.getPassword());

		return new ResponseEntity<>(new Account(userSnapshot), HttpStatus.OK);
	}

	@ApiOperation(value = "Get account of logged user", notes = "Returns account of logged user")
	@ApiResponses({ @ApiResponse(code = 200, message = "Found account of logged user") })
	@RequestMapping(method = GET)
	public HttpEntity<Account> get() {
		UserSnapshot userSnapshot = getLoggedUserSnapshot();
		return new ResponseEntity<>(new Account(userSnapshot), HttpStatus.OK);
	}

	@ApiOperation(value = "Change password for account with id", notes = "Empty body")
	@ApiResponses({ @ApiResponse(code = 200, message = "Changed password for account") })
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public HttpEntity<Account> changePassword(@RequestBody String password) {
		UserSnapshot userSnapshot = getLoggedUserSnapshot();

		userBO.changePassword(userSnapshot.getId(), password);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
