package hpclab.ksatengmaker_spring.admin.service;

import hpclab.ksatengmaker_spring.admin.domain.UserRequest;
import hpclab.ksatengmaker_spring.admin.dto.UserRequestRequestForm;
import hpclab.ksatengmaker_spring.admin.dto.UserRequestResponseForm;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;

import java.util.List;

public interface UserRequestService {

    UserRequestResponseForm getQuestionErrorForm(Long qId);

    UserRequestResponseForm getGoodSayingForm(UserRequestRequestForm form);

    UserRequestResponseForm getImprovingForm(UserRequestRequestForm form);

    UserRequestResponseForm getETCForm(UserRequestRequestForm form);

    void saveUserRequest(UserRequestResponseForm form);

    List<UserRequestResponseForm> getUserRequests();
}
