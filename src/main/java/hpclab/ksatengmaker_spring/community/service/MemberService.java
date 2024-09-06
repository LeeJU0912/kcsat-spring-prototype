package hpclab.ksatengmaker_spring.community.service;

import hpclab.ksatengmaker_spring.community.dto.MemberForm;
import hpclab.ksatengmaker_spring.community.dto.MemberResponseForm;
import hpclab.ksatengmaker_spring.myBook.dto.BookResponseForm;

import java.util.List;

public interface MemberService {

    Long join(MemberForm memberForm);

    List<MemberResponseForm> findMembers();
}