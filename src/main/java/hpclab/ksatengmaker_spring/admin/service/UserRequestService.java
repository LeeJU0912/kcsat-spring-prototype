package hpclab.ksatengmaker_spring.admin.service;

import hpclab.ksatengmaker_spring.admin.dto.UserRequestRequestForm;
import hpclab.ksatengmaker_spring.admin.dto.UserRequestResponseForm;

import java.util.List;

public interface UserRequestService {

    UserRequestResponseForm getQuestionErrorForm(Long qId);

    UserRequestResponseForm getImprovingForm(UserRequestRequestForm form);

    UserRequestResponseForm getETCForm(UserRequestRequestForm form);

    void saveUserRequest(UserRequestResponseForm form);

    List<UserRequestResponseForm> getUserRequests();
}
