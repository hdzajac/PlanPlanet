package pl.edu.agh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.agh.forms.UserCreateForm;
import pl.edu.agh.model.auth.CreateUserResponse;
import pl.edu.agh.model.user.User;
import pl.edu.agh.services.UserService;
import pl.edu.agh.validators.UserCreateFormValidator;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;
    private final UserCreateFormValidator validator;

    @Autowired
    UserController(UserService service, UserCreateFormValidator validator) {
        this.service = service;
        this.validator = validator;
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.POST)
//    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.getDetails() != null) {
//            try {
//                request.getSession(false).invalidate();
//                System.out.println("INVALIDATED");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        response.setStatus(HttpServletResponse.SC_OK);
//    }

//        @RequestMapping("/user")
//        public User getUsersPage (@RequestParam("login") String username) {
//            Optional<User> user = service.findByLogin(username);
//            System.out.println(user.get());
//            if (user.isPresent()){
//                return user.get();
//            } else
//                throw new UsernameNotFoundException(username);
//        }

//    @RequestMapping(value = "{id}", method = RequestMethod.GET)
//    @ResponseBody
//    User getById(@PathVariable("id") String id) {
//        return service.findById(id).orElseThrow(() -> new NoSuchElementException("No such user: " + id));
//    }


    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user_create", "form", new UserCreateForm());
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<CreateUserResponse> handleUserCreateForm(@RequestBody UserCreateForm form, BindingResult
            bindingResult) {
        validator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(CreateUserResponse.failed(bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.OK);
        }
        try {
            User user = service.create(form);
            return new ResponseEntity<>(CreateUserResponse.success(user), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("login.exists", "Login already exists");
            return new ResponseEntity<>(CreateUserResponse.failed("Login already exists"), HttpStatus.OK);
        }
    }
}